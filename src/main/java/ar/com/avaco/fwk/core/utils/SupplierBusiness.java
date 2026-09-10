package ar.com.avaco.fwk.core.utils;
import ar.com.avaco.fwk.core.exception.BusinessException;

/**
 * 
 */

/**
 * @author avaco
 *
 */
public interface SupplierBusiness<T>{
	T get() throws BusinessException;
}
