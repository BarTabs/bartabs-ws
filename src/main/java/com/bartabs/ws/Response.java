package com.bartabs.ws;

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

	public Response buildResponse(final String message)
	{
		Response response = new Response();
		response.setStatus(0);
		response.setMessage(message);

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
