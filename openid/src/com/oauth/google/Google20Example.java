package com.oauth.google;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;

public class Google20Example
{
	private static final String NETWORK_NAME = "G+";
	private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/plus/v1/people/me";

	public static void main(String[] args) throws IOException,
			InterruptedException, ExecutionException
	{
		final String cliendId = "299469286864-ecjotn9dteehv4ieoi9c9srhiaaf63bf.apps.googleusercontent.com";
		final String clientSecret = "IMWiaXSrFxhJDGwX2YPGXlbQ";
		final String callback = "http://localhost:8080/OAuth/OAuthCallback";
		final String scope = "https://www.googleapis.com/auth/plus.me";
		final String secretState = "secret" + new Random().nextInt(999_999);

		OAuth20Service service = new ServiceBuilder().apiKey(cliendId)
				.apiSecret(clientSecret).scope(scope)
				.state(secretState).callback(callback)
				.build(GoogleApi20.instance());

		final Scanner in = new Scanner(System.in, "UTF-8");

		System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
		System.out.println();

		// Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");

		final Map<String, String> additionalParams = new HashMap<>();
		additionalParams.put("access_type", "offline");
		additionalParams.put("prompt", "consent");

		final String authorizationUrl = service
				.getAuthorizationUrl(additionalParams);

		System.out.println("Got the Authorization URL!");
		System.out.println("Now go and authorize ScribeJava here:");
		System.out.println(authorizationUrl);
		System.out.println("And paste the authorization code here");
		System.out.print(">>");

		final String code = in.nextLine();
		System.out.println();

		System.out.println("And paste the state from server here. We have set 'secretState'='"
				+ secretState + "'.");
		System.out.println(">>");

		final String value = in.nextLine();

		if (secretState.equals(value))
		{
			System.out.println("State value does match!");
		}
		else
		{
			System.out.println("Ooops, state value does not match!");
			System.out.println("Expected = " + secretState);
			System.out.println("Got      = " + value);
			System.out.println();
		}

		// Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");

		OAuth2AccessToken accessToken = service.getAccessToken(clientSecret);

		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: "
				+ accessToken + ", 'rawResponse'='"
				+ accessToken.getRawResponse() + "')");

	}

}
