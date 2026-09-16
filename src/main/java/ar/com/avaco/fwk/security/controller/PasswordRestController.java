package ar.com.avaco.fwk.security.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ar.com.avaco.fwk.core.component.dto.JSONResponse;
import ar.com.avaco.fwk.core.exception.BusinessException;
import ar.com.avaco.fwk.core.utils.FunctionBusiness;
import ar.com.avaco.fwk.security.domain.Usuario;
import ar.com.avaco.fwk.security.dto.PassworResetDTO;
import ar.com.avaco.fwk.security.dto.UpdatePasswordDTO;
import ar.com.avaco.fwk.security.epservice.UsuarioEPService;
import ar.com.avaco.fwk.security.service.UsuarioService;

@RestController
public class PasswordRestController {

	private UsuarioService usuarioService;

	@RequestMapping(value = "/password/reset", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> reset(@RequestBody PassworResetDTO dto) throws BusinessException {
		usuarioService.sendMissingPassword(dto.getEmail());
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setData(null);
		jsonResponse.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/password/reset/{id}", method = RequestMethod.PUT)
	public ResponseEntity<JSONResponse> resetById(@PathVariable("id") Long id) throws BusinessException {
		usuarioService.sendMissingPasswordById(id);
		JSONResponse jsonResponse = new JSONResponse();
		jsonResponse.setData(null);
		jsonResponse.setStatus(JSONResponse.OK);
		return new ResponseEntity<JSONResponse>(jsonResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "/password/update", method = RequestMethod.POST)
	public ResponseEntity<JSONResponse> resetPassword(HttpServletRequest request,
			@RequestBody UpdatePasswordDTO updatePassword) throws BusinessException {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		Usuario usuario = (Usuario) usuarioService.loadUserByUsername(name);

		return executeProcess("update-password", Void -> {
			usuarioService.updatePassword(usuario, updatePassword.getCurrentPassword(),
					updatePassword.getNewPassword());
			return null;
		});
	}

	public <R> ResponseEntity<JSONResponse> executeProcess(String processName, FunctionBusiness<Void, R> function)
			throws BusinessException {
		HttpStatus httpStatus = HttpStatus.OK;
		JSONResponse response = getResponseOK(function.apply(null));
		return new ResponseEntity<JSONResponse>(response, httpStatus);
	}

	protected <T> JSONResponse getResponseOK(T data) {
		JSONResponse response = new JSONResponse();
		response.setData(data);
		response.setStatus(JSONResponse.OK);
		return response;
	}

	@Resource(name = "usuarioService")
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

}
