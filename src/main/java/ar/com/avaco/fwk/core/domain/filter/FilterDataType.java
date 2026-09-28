package ar.com.avaco.fwk.core.domain.filter;

/**
 * @author avaco
 *
 */
public enum FilterDataType {
	LESS_THAN, 
	MORE_THAN, 
	EQUALS, 
	LIKE, 
	EQUALS_LESS_THAN, 
	EQUALS_MORE_THAN,
	NOT_EQUALS,
	IS_NULL,
	IS_NOT_NULL,
	IN,
	NOT_IN;
}