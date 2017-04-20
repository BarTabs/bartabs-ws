/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * The {@code NotificationService} class handles all the heavy lifting
 * associated with sending a notification to a user's phone.
 * <p>
 * Notifications are sent using the Google's Firebase API, through HTTP requests
 * from our webapp to their server.
 * 
 * @author Victor A. Lora
 * @version 1.0
 * @see com.bartabs.ws.Notification
 * @since 2017-04-16
 *
 */
@Service("Notification.NotificationService")
public class NotificationService
{
	private final static String URL = "https://fcm.googleapis.com/fcm/send";
	private final static String SERVER_KEY = "AAAAXbWu6JU:APA91bG4n8uXyWZMhVsxn5qOXQ7vaqooSXA73FYAN8JokVptOvTFoK1GbAI5A"
			+ "sOCDBT6t1IXxIoKxXBzK9q5p22bi8DpnLOUIw8Bi90OONBUPE4H4B8u-UHv5qYOxObziS7BiCip1LA3";
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * Takes a {@code Notification} class and use it to send a notification to
	 * the Firebase server
	 * 
	 * @param notification
	 *            a {@code Notification} instance containing the required
	 *            information to send a message to the user
	 * @throws Exception
	 *             thrown when an error occurs during the notification process
	 */
	public void sendNotification(Notification notification) throws Exception
	{
		if (notification.getTargetDeviceID() == null) {
			throw new Exception("No target ID was provided.");
		} else if (notification.getBody() == null) {
			throw new Exception("A notification must be given a body.");
		}

		// TODO: Abstract the HTTP Post & add a HTTP Get
		System.setProperty("https.protocols", "SSLv3");

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);

		httppost.setHeader("Authorization", "key=" + SERVER_KEY);
		httppost.setHeader("Content-Type", "application/json");

		// Request body
		ObjectMapper mapper = new ObjectMapper();

		// Create the different parts of the JSON object (each node represents a
		// layer in JSON)
		ObjectNode notificationNode = mapper.createObjectNode();
		notificationNode.put("title", notification.getTitle());
		notificationNode.put("body", notification.getBody());
		notificationNode.put("sound", notification.getSound());

		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put("to", notification.getTargetDeviceID());
		mainNode.put("priority", notification.getPriority());
		mainNode.putPOJO("notification", notificationNode);

		// Set the HTTP request body
		httppost.setEntity(new StringEntity(mainNode.toString()));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				log.info("Notification sent");
			} finally {
				instream.close();
			}
		}
	}
}
