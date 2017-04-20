/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * The {@code QRCodeGenerator} class uses Google's {@code zxing} library to
 * generate a QR Code
 * 
 * @author Victor A. Lora
 * @version 1.0
 * @since 2017-04-12
 *
 */
public class QRCodeGenerator
{
	/**
	 * Generates a QR Code from the given content
	 * 
	 * @param content
	 *            a {@code String} with the content desired in the QR Code
	 * @return a byte array containing the QR Code image
	 */
	public static byte[] generateQRCode(final String content)
	{
		byte[] data = null;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			final String qrCodeData = content;
			final String fileType = "png";
			final int size = 150;

			final Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			// Change border size (white border size to just 1)
			hintMap.put(EncodeHintType.MARGIN, 3); /* default = 4 */
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

			// Create QR Code Image
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);
			graphics.setFont(graphics.getFont().deriveFont(8f));

			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}

			// Write the image to the byte array
			ImageIO.write(image, fileType, out);

			// Set the byte array to the data variable
			data = out.toByteArray();

		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
}
