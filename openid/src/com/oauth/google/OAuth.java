package com.oauth.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

@WebServlet("/oauth")
public class OAuth extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private static final String NETWORK_NAME = "G+";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
		final String cliendId = "299469286864-ecjotn9dteehv4ieoi9c9srhiaaf63bf.apps.googleusercontent.com";
		final String clientSecret = "IMWiaXSrFxhJDGwX2YPGXlbQ";
		final String callback = "http://localhost:8080/openid/callback";
		final String scope = "https://www.googleapis.com/auth/plus.login";
		final String secretState = "secret" + new Random().nextInt(999_999);

		OAuth20Service service = new ServiceBuilder().apiKey(cliendId)
				.apiSecret(clientSecret).scope(scope)
				.state(secretState).callback(callback)
				.build(GoogleApi20.instance());

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();

		// Adding additional params
		final Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("access_type", "offline");
		additionalParams.put("prompt", "consent");
		
		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = service.getAuthorizationUrl(additionalParams);

		System.out.println(authorizationUrl+"\n");
		
		HttpSession session=request.getSession();
		session.setAttribute("service", service);
		
		
		response.sendRedirect(authorizationUrl);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
	}

}
