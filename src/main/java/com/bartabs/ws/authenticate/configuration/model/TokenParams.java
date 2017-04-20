/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.authenticate.configuration.model;

import java.util.Calendar;
import java.util.Date;

public class TokenParams
{
	private String issuer;
	private String audience;
	private String subject;
	private Date expiration;
	private String secretKey;

	public String getIssuer()
	{
		return issuer;
	}

	public void setIssuer(String issuer)
	{
		this.issuer = issuer;
	}

	public String getAudience()
	{
		return audience;
	}

	public void setAudience(String audience)
	{
		this.audience = audience;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * Calculates a day one week in the future from the given {@code Date}
	 * 
	 * @param date
	 *            a start date
	 * @return a date one week in the future from the start date
	 */
	public Date calculateExpiration(Date date)
	{
		final Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DAY_OF_YEAR, 7);

		return now.getTime();
	}

	public Date getExpiration()
	{
		return expiration;
	}

	public void setExpiration(Date expiration)
	{
		this.expiration = expiration;
	}

	public String getSecretKey()
	{
		return secretKey;
	}

	public void setSecretKey(String secretKey)
	{
		this.secretKey = secretKey;
	}

}
