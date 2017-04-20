/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws;

/**
 * The {@code Response} class produces a model for the JSON formatted response.
 * <p>
 * JSON Model:
 * <p>
 * {<br>
 * &emsp;status: 0/-1<br>
 * &emsp;message: 'message'<br>
 * &emsp;data: 'data'<br>
 * }
 * 
 * 
 * @author Victor A. Lora
 * @version 1.0
 * @since 2017-04-12
 *
 */
public class Response
{
	private int status;
	private String message;
	private Object data;

	public Response buildResponse(final Object data, final String message)
	{
		Response response = new Response();
		response.setStatus(0);
		response.setMessage(message);
		response.setData(data);

		return response;
	}

	public Response buildResponse(final Object data)
	{
		Response response = new Response();
		response.setStatus(0);
		response.setMessage("Ok");
		response.setData(data);

		return response;
	}

	public Response buildErrorResponse(final String message)
	{
		Response response = new Response();
		response.setStatus(-1);
		response.setMessage(message);

		return response;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

}
