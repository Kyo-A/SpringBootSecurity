package com.example.springbootapp.security.jwt;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

	// Cette méthode sera déclenchée chaque fois qu'un utilisateur non authentifié demande une ressource HTTP
    // sécurisée et qu'une AuthenticationException est levée
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		
		logger.error("Unauthorized error. Message - {}", e.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
		
	}

}
