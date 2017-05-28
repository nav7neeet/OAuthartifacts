package com.oauth.google;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuthService;

@WebServlet("/oauth")
public class OAuth extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private final String CLIENT_ID = "299469286864-ecjotn9dteehv4ieoi9c9srhiaaf63bf.apps.googleusercontent.com";
	private final String CLIENT_SECRET = "IMWiaXSrFxhJDGwX2YPGXlbQ";
	private final String CALL_BACK = "http://localhost:8080/OAuth/OAuthCallback";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
		System.out.println("oauth");
		
		ServiceBuilder builder = new ServiceBuilder();
		OAuthService service=builder
				.apiKey(CLIENT_ID)
				.apiSecret(CLIENT_SECRET)
				.scope("https://www.googleapis.com/auth/plus.login")
				.callback(CALL_BACK)
				.build(GoogleApi20.instance());

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
	}

}
