package com.bartabs.ws;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service("Notification.NotificationService")
public class NotificationService
{
	private final static String URL = "https://fcm.googleapis.com/fcm/send";
	private final static String SERVER_KEY = "AAAAXbWu6JU:APA91bG4n8uXyWZMhVsxn5qOXQ7vaqooSXA73FYAN8JokVptOvTFoK1GbAI5A"
			+ "sOCDBT6t1IXxIoKxXBzK9q5p22bi8DpnLOUIw8Bi90OONBUPE4H4B8u-UHv5qYOxObziS7BiCip1LA3";

	public void sendNotification(Notification notification) throws Exception
	{
		System.setProperty("https.protocols", "SSLv3");

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(URL);

		httppost.setHeader("Authorization", "key=" + SERVER_KEY);
		httppost.setHeader("Content-Type", "application/json");

		// Request body
		ObjectMapper mapper = new ObjectMapper();

		ObjectNode notificationNode = mapper.createObjectNode();
		notificationNode.put("title", notification.getTitle());
		notificationNode.put("body", notification.getBody());
		notificationNode.put("sound", notification.getSound());

		ObjectNode mainNode = mapper.createObjectNode();
		mainNode.put("to", notification.getTargetDeviceID());
		mainNode.put("priority", notification.getPriority());
		mainNode.putPOJO("notification", notificationNode);

		httppost.setEntity(new StringEntity(mainNode.toString()));

		// Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instream = entity.getContent();
			try {
				// do something useful
			} finally {
				instream.close();
			}
		}
	}
}
