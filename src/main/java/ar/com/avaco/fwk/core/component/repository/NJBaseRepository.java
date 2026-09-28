package ar.com.avaco.fwk.core.component.repository;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.Subcriteria;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import ar.com.avaco.fwk.core.component.dto.entity.DTOEntity;
import ar.com.avaco.fwk.core.domain.filter.AbstractFilter;
import ar.com.avaco.fwk.core.domain.filter.FilterData;

public class NJBaseRepository<ID extends Serializable, E extends ar.com.avaco.fwk.core.domain.Entity<ID>>
		extends SimpleJpaRepository<E, ID> implements NJRepository<ID, E> {

	@Autowired
	private SessionFactory sessionFactory;
	private Class<E> javaType;

	protected EntityManager entityManager;

	public NJBaseRepository(Class<E> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.javaType = domainClass;
		this.entityManager = entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> listFilter(AbstractFilter abstractFilter) {
		Criteria criteria = getCurrentSession().createCriteria(getHandledClass());
		applyFilters(criteria, abstractFilter);
		applyPagination(criteria, abstractFilter);
		applyOrdering(criteria, abstractFilter);
		if (Boolean.TRUE.equals(abstractFilter.getDistinctRootEntity())) {
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <ID extends Serializable, D extends DTOEntity<ID>> List<D> listFilter(AbstractFilter filter,
			Class<D> dtoClass) {

		Criteria criteria = getCurrentSession().createCriteria(getHandledClass());

		applyFilters(criteria, filter);
		applyPagination(criteria, filter);
		applyOrdering(criteria, filter);

		ProjectionList projections = resolveProjection(dtoClass, criteria);

		if (projections != null) {
			criteria.setProjection(projections);
			criteria.setResultTransformer(Transformers.aliasToBean(dtoClass));
		} else if (Boolean.TRUE.equals(filter.getDistinctRootEntity())) {
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}

		return criteria.list();
	}

	private ProjectionList resolveProjection(Class<?> dtoClass, Criteria criteria) {

		try {
			Object dtoInstance = dtoClass.getDeclaredConstructor().newInstance();
			Method method = dtoClass.getMethod("getProjections");
			ProjectionList pl = (ProjectionList) method.invoke(dtoInstance);

			Field field = ProjectionList.class.getDeclaredField("elements");
			field.setAccessible(true);

			List<?> projections = (List<?>) field.get(pl);

			for (Object projection : projections) {

				try {

					Field projField = projection.getClass().getDeclaredField("projection");
					projField.setAccessible(true);

					Object innerProjection = projField.get(projection);

					if (innerProjection instanceof PropertyProjection) {

						Field propertyNameField = PropertyProjection.class.getDeclaredField("propertyName");

						propertyNameField.setAccessible(true);

						String propertyName = (String) propertyNameField.get(innerProjection);

						// Crear aliases necesarios
						containsAlias(criteria, propertyName);

						// Reemplazar el path original por el aliasado
						String aliasedProperty = getAliasedProperty(propertyName);

						propertyNameField.set(innerProjection, aliasedProperty);

					}

				} catch (NoSuchFieldException e) {
					// SQLProjection, AggregateProjection,
					// CountProjection, etc.
					continue;
				}
			}

			return pl;

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error resolving DTO projection: " + dtoClass.getName(), e);
		}
	}

	protected Class<?> getHandledClass() {
		return javaType;
	}

	@Override
	public int listCount(AbstractFilter abstractFilter) {
		Criteria criteria = getCurrentSession().createCriteria(getHandledClass());
		applyFilters(criteria, abstractFilter);
		Long uniqueResult = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return uniqueResult.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> listPattern(String field, Object pattern) {
		Criteria criteria = getCurrentSession().createCriteria(getHandledClass());
		if (pattern instanceof String) {
			criteria.add(Restrictions.like(field, pattern.toString(), MatchMode.ANYWHERE).ignoreCase());
		} else {
			criteria.add(Restrictions.eq(field, pattern));
		}
		return criteria.list();
	}

	protected void applyPagination(Criteria criteria, AbstractFilter abstractFilter) {
		if (abstractFilter.getFirst() != null && abstractFilter.getRows() != null) {
			criteria.setFirstResult(abstractFilter.getFirst());
			criteria.setMaxResults(abstractFilter.getRows());
		}
	}

	protected void applyOrdering(Criteria criteria, AbstractFilter abstractFilter) {
		if (abstractFilter.getAsc() != null && !StringUtils.isEmpty(abstractFilter.getSidx())) {
			criteria = containsAlias(criteria, abstractFilter.getIdx());

			String sortField = abstractFilter.getIdx();
			if (!isPropertyEmbeddable(sortField)) {
				sortField = getAliasedProperty(sortField);
			}

			if (abstractFilter.isAsc()) {
				criteria.addOrder(Order.asc(sortField).nulls(NullPrecedence.NONE));
			} else {
				criteria.addOrder(Order.desc(sortField).nulls(NullPrecedence.NONE));
			}
		}
	}

	private String getAliasedProperty(String property) {

		if (!property.contains(".")) {
			return property;
		}

		String[] prop = property.split("\\.");

		if (prop.length == 2) {
			return property;
		}

		StringBuilder alias = new StringBuilder();

		for (int i = 0; i < prop.length - 1; i++) {
			alias.append(prop[i]);
		}

		return alias.toString() + "." + prop[prop.length - 1];
	}

	protected void applyFilters(Criteria criteria, AbstractFilter abstractFilter) {

		for (List<FilterData> fdl : abstractFilter.getOrFilterDatas()) {
			Disjunction or = Restrictions.disjunction();
			for (FilterData ofd : fdl) {
				criteria = containsAlias(criteria, ofd.getProperty());
				or.add(createCriterion(ofd));
			}
			criteria.add(or);
		}

		List<FilterData> filters = abstractFilter.getFilterDatas();

		for (FilterData data : filters) {
			criteria = containsAlias(criteria, data.getProperty());
			Criterion criterion = createCriterion(data);
			criteria.add(criterion);
		}

	}

	private Criterion createCriterion(FilterData data) {
		String property = data.getProperty();
		if (!isPropertyEmbeddable(property)) {
			property = getAliasedProperty(property);
		}

		switch (data.getFilterDataType()) {
		case LESS_THAN:
			return Restrictions.lt(property, data.getObject());
		case MORE_THAN:
			return Restrictions.gt(property, data.getObject());
		case EQUALS:
			return Restrictions.eq(property, data.getObject());
		case LIKE:
			return Restrictions.ilike(property, data.getObject().toString(), MatchMode.ANYWHERE);
		case EQUALS_LESS_THAN:
			return Restrictions.le(property, data.getObject());
		case EQUALS_MORE_THAN:
			return Restrictions.ge(property, data.getObject());
		case NOT_EQUALS:
			return Restrictions.not(Restrictions.eq(property, data.getObject()));
		case IS_NULL:
			return Restrictions.isNull(property);
		case IS_NOT_NULL:
			return Restrictions.isNotNull(property);
		case IN:
			return Restrictions.in(property, (Object[]) data.getObject());
		case NOT_IN:
			return Restrictions.not(Restrictions.in(property, (Object[]) data.getObject()));
		default:
			return null;
		}
	}

	private boolean isPropertyEmbeddable(String property) {
		String theProperty = property;
		if (property.contains(".")) {
			theProperty = property.substring(0, property.indexOf("."));
		}

		Class<?> clazz = getHandledClass();

		Annotation[] lt = null;
		while (clazz != null) {
			try {
				lt = clazz.getDeclaredField(theProperty).getType().getAnnotations();
				clazz = null;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
				if (clazz.equals(Object.class)) {
					throw new RuntimeException(
							"Property " + property + " not present in class " + getHandledClass().getName());
				}
			}
		}
		if (lt != null) {
			for (Annotation ann : lt) {
				if (ann.annotationType().getCanonicalName().equals(Embeddable.class.getCanonicalName())) {
					return true;
				}
			}
		}
		return false;
	}

	private Criteria containsAlias(Criteria criteria, String property) {

		if (!property.contains(".")) {
			return criteria;
		}

		CriteriaImpl ci = (CriteriaImpl) criteria;

		String[] prop = property.split("\\.");

		Class<?> currentClass = getHandledClass();

		String currentPath = "";

		for (int i = 0; i < prop.length - 1; i++) {

			String fieldName = prop[i];

			Field field = findField(currentClass, fieldName);

			if (field == null) {
				return criteria;
			}

			boolean association = field.isAnnotationPresent(ManyToOne.class)
					|| field.isAnnotationPresent(OneToOne.class) || field.isAnnotationPresent(OneToMany.class)
					|| field.isAnnotationPresent(ManyToMany.class);

			boolean embedded = field.isAnnotationPresent(Embedded.class);

			if (currentPath.isEmpty()) {
				currentPath = fieldName;
			} else {
				currentPath += "." + fieldName;
			}

			if (association) {

				String alias = currentPath.replace(".", "");

				Iterator<Subcriteria> it = ci.iterateSubcriteria();

				boolean found = false;

				while (it.hasNext() && !found) {
					Subcriteria next = it.next();
					found = alias.equals(next.getAlias());
				}

				if (!found) {
					criteria.createAlias(currentPath, alias, JoinType.LEFT_OUTER_JOIN);
				}
			}

			currentClass = field.getType();

			if (embedded) {
				continue;
			}
		}

		return criteria;
	}

	private Field findField(Class<?> clazz, String fieldName) {

		Class<?> current = clazz;

		while (current != null && current != Object.class) {

			try {
				Field field = current.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				current = current.getSuperclass();
			}
		}

		return null;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * Gets the {@link SessionFactory} that will handle the Hibernate
	 * {@link org.hibernate.Session}s.
	 * 
	 * @return {@link SessionFactory} The factory.
	 */
	protected SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Sets the {@link SessionFactory} that will handle the Hibernate
	 * {@link org.hibernate.Session}s.
	 * 
	 * @param sessionFactory The factory.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> listEqField(String field, Object value) {
		Criteria criteria = getCurrentSession().createCriteria(getHandledClass());
		if (value == null) {
			criteria.add(Restrictions.isNull(field));
		} else {
			criteria.add(Restrictions.eq(field, value));
		}
		return criteria.list();
	}

	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void remove(ID id) {
		E entity = findById(id).orElse(null);

		System.out.println("entity = " + entity);
		System.out.println("TX READ ONLY = " +
		        TransactionSynchronizationManager.isCurrentTransactionReadOnly());
		
		Connection connection = DataSourceUtils.getConnection(dataSource);

		System.out.println("Spring TX READ ONLY = "
		        + TransactionSynchronizationManager.isCurrentTransactionReadOnly());

		try {
			System.out.println("JDBC READ ONLY = "
			        + connection.isReadOnly());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			System.out.println("JDBC URL = "
			        + connection.getMetaData().getURL());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("METHOD = " +
		        this.getClass().getName());

		System.out.println("TX ACTIVE = " +
		        TransactionSynchronizationManager.isActualTransactionActive());

		System.out.println("TX READ ONLY = " +
		        TransactionSynchronizationManager.isCurrentTransactionReadOnly());

		System.out.println("TX NAME = " +
		        TransactionSynchronizationManager.getCurrentTransactionName());
		
		if (entity != null) {
			delete(entity);
			entityManager.flush();
		}
	}

}