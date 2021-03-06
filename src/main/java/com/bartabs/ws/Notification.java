/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws;

public class Notification
{
	private String targetDeviceID;
	private String title = "Notification";
	private String body;
	private String priority = "high";
	private String sound = "default";

	public String getTargetDeviceID()
	{
		return targetDeviceID;
	}

	public void setTargetDeviceID(String targetDeviceID)
	{
		this.targetDeviceID = targetDeviceID;
	}

	/**
	 * Returns the notification title
	 * 
	 * @return the notification title. Default is "Notification"
	 */
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getBody()
	{
		return body;
	}

	public void setBody(String body)
	{
		this.body = body;
	}

	public String getPriority()
	{
		return priority;
	}

	public void setPriority(String priority)
	{
		this.priority = priority;
	}

	public String getSound()
	{
		return sound;
	}

	public void setSound(String sound)
	{
		this.sound = sound;
	}

}
