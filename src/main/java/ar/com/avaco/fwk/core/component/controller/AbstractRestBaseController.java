package ar.com.avaco.fwk.core.component.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.utils.FunctionBusiness;

public abstract class AbstractRestBaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRestBaseController.class);

	protected static final String OK = "OK";

	public <R> ResponseEntity<JSONResponse> executeProcess(String processName, FunctionBusiness<Void, R> function) throws Exception {
		LOGGER.info("Run process " + processName);
		HttpStatus httpStatus = HttpStatus.OK;
		JSONResponse response = getResponseOK(function.apply(null));
		return new ResponseEntity<JSONResponse>(response, httpStatus);
	}

	protected <T> JSONResponse getResponseOK(T data) {
		JSONResponse response = new JSONResponse();
		response.setData(data);
		response.setStatus(OK);
		return response;
	}

}