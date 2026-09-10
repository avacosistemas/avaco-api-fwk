package ar.com.avaco.fwk.core.utils;

import java.math.BigDecimal;

public class NumberUtils {


	public static BigDecimal parseMonto(String valor) {
	    if (valor == null || valor.trim().isEmpty()) {
	        return BigDecimal.ZERO;
	    }

	    valor = valor.trim().replace(" ", "");

	    int ultimoPunto = valor.lastIndexOf('.');
	    int ultimaComa = valor.lastIndexOf(',');

	    if (ultimoPunto >= 0 && ultimaComa >= 0) {
	        // Tiene ambos separadores
	        if (ultimoPunto > ultimaComa) {
	            // 2,341,141.65
	            valor = valor.replace(",", "");
	        } else {
	            // 2.341.141,65
	            valor = valor.replace(".", "");
	            valor = valor.replace(",", ".");
	        }
	    } else if (ultimoPunto >= 0) {
	        int decimales = valor.length() - ultimoPunto - 1;

	        if (decimales <= 2) {
	            // 1234.56
	        } else {
	            // 1.234.567
	            valor = valor.replace(".", "");
	        }
	    } else if (ultimaComa >= 0) {
	        int decimales = valor.length() - ultimaComa - 1;

	        if (decimales <= 2) {
	            // 1234,56
	            valor = valor.replace(",", ".");
	        } else {
	            // 1,234,567
	            valor = valor.replace(",", "");
	        }
	    }

	    return new BigDecimal(valor);
	}

}
