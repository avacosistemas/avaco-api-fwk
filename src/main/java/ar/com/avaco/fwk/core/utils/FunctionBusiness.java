package ar.com.avaco.fwk.core.utils;
import ar.com.avaco.fwk.core.exception.BusinessException;

/**
 * 
 */

/**
 * @author avaco
 *
 */
public interface FunctionBusiness<T, R>{
	R apply(T t) throws BusinessException;
}
