package com.bartabs.ws.configuration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@Configuration("Configuration.AuthenticationConfiguration")
public class AuthenticationConfiguration
{
	private String tokenSecretPath;
	private int tokenExpiresMinutes;
	private String tokenIssuer;
	private String tokenAudience;
	private boolean enableTokenAuthentication;

	public String getTokenSecretPath()
	{
		return tokenSecretPath;
	}

	public void setTokenSecretPath(String tokenSecretPath)
	{
		this.tokenSecretPath = tokenSecretPath;
	}

	public int getTokenExpiresMinutes()
	{
		return tokenExpiresMinutes;
	}

	public void setTokenExpiresMinutes(int tokenExpiresMinutes)
	{
		this.tokenExpiresMinutes = tokenExpiresMinutes;
	}

	public String getTokenIssuer()
	{
		return tokenIssuer;
	}

	public void setTokenIssuer(String tokenIssuer)
	{
		this.tokenIssuer = tokenIssuer;
	}

	public String getTokenAudience()
	{
		return tokenAudience;
	}

	public void setTokenAudience(String tokenAudience)
	{
		this.tokenAudience = tokenAudience;
	}

	public boolean getEnableTokenAuthentication()
	{
		return enableTokenAuthentication;
	}

	public void setEnableTokenAuthentication(boolean enableTokenAuthentication)
	{
		this.enableTokenAuthentication = enableTokenAuthentication;
	}

	// @Bean(name = "cobl.authTokenParams")
	// public TokenParams tokenParams() throws Exception
	// {
	// final TokenParams tokenParams = new TokenParams();
	// tokenParams.setIssuer(getTokenIssuer());
	// tokenParams.setAudience(getTokenAudience());
	// tokenParams.setExpiresMinutes(getTokenExpiresMinutes());
	//
	// byte[] key;
	//
	// // TODO - clean this up. don't want unclosed files.
	// try {
	// final FileInputStream fis = new FileInputStream(getTokenSecretPath());
	//
	// key = new byte[fis.available()];
	// fis.read(key);
	// tokenParams.setUsingGeneratedSecretKey(false);
	// fis.close();
	// } catch (final FileNotFoundException fnfe) {
	// try {
	// key = createSecretFile();
	// } catch (final IOException ioe) {
	// key = MacProvider.generateKey().getEncoded();
	// tokenParams.setUsingGeneratedSecretKey(true);
	// }
	// }
	//
	// tokenParams.setSecretKey(key);
	//
	// return tokenParams;
	// }

	private byte[] createSecretFile() throws FileNotFoundException, IOException
	{
		final FileOutputStream fos = new FileOutputStream(getTokenSecretPath());
		final byte[] key = MacProvider.generateKey(SignatureAlgorithm.HS512).getEncoded();
		fos.write(key);
		fos.close();
		return key;
	}

}
