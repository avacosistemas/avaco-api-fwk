package ar.com.avaco.fwk.core.component.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.component.dto.PageDTO;

public abstract class BaseController {

	protected ResponseEntity<JSONResponse> OKDATA(Object data) {
		JSONResponse response = new JSONResponse();
		response.setData(data);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	protected ResponseEntity<JSONResponse> CONFLICT(Object data) {
		JSONResponse response = new JSONResponse();
		response.setData(data);
		response.setStatus(JSONResponse.ERROR);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.CONFLICT);
	}
	
	protected ResponseEntity<JSONResponse> OKPAGE(PageDTO<?> p) {
		JSONResponse response = new JSONResponse(p);
		response.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(response, HttpStatus.OK);
	}

	
}
