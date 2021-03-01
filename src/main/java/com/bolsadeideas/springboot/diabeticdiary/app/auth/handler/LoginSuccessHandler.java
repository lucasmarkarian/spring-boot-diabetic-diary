package com.bolsadeideas.springboot.diabeticdiary.app.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.bolsadeideas.springboot.diabeticdiary.app.models.entity.Usuario;
import com.bolsadeideas.springboot.diabeticdiary.app.models.service.IUsuarioService;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		Usuario usuario = this.usuarioService.findByUsername(authentication.getName());
		
		String mensaje = String.format("Hola %s, has iniciado sesión con éxito!", usuario.getNombre());
		
		flashMap.put("success", mensaje);
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		super.onAuthenticationSuccess(request, response, authentication);
		
	}
	
}
