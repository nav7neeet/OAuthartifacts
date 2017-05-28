package com.oauth.google;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.github.scribejava.core.oauth.OAuthService;

@WebServlet(urlPatterns = { "/callback" }, asyncSupported = true)
public class OAuthCallback extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/plus/v1/people/me";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
		System.out.println("in the call back servlet");
		String code = request.getParameter("code");
		HttpSession session = request.getSession();
		OAuth20Service service = (OAuth20Service) session
				.getAttribute("service");
		OAuth2AccessToken accessToken = null;
		try
		{
			accessToken = service.getAccessToken(code);
			final OAuthRequest authRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
			service.signRequest(accessToken, authRequest);

			final Response response2 = service.execute(authRequest);
			
			System.out.println();
	            System.out.println(response2.getCode());
	            System.out.println(response2.getBody());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			IOException
	{
		System.out.println("post call back");
	}

}
