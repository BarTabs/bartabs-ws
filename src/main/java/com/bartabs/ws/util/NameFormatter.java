/*
 * Copyright (c) 2017, Ron Gerschel, Jon Goldberg and Victor Lora. All rights reserved.
 * Ron Gerschel, Jon Goldberg and Victor Lora PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.bartabs.ws.util;

/**
 * The {@code NameFormatter} class implements a name formatter that returns
 * names in one of the following two formats (middle initial optional):
 * <ol>
 * <li>John A Doe</li>
 * <li>Doe, John A</li>
 * </ol>
 *
 * @author Victor A. Lora
 * @version 1.0
 * @since 2017-04-16
 */
public class NameFormatter
{
	/**
	 * Returns a formatted name.
	 * 
	 * @param firstName
	 *            a first name
	 * @param lastName
	 *            a last name
	 * @param middleInitial
	 *            a middle name
	 * @param reverseCommaSeparated
	 *            if true, the name is formatted as Doe, John A; if false, the
	 *            name is formatted as John A Doe
	 * @return a formatted name
	 */
	public static String format(String firstName, String lastName, String middleInitial, boolean reverseCommaSeparated)
	{
		String formattedName = null;

		if (firstName != null && lastName != null && middleInitial != null && !reverseCommaSeparated) {
			formattedName = firstName + " " + middleInitial + " " + lastName;
		} else if (firstName != null && lastName != null && middleInitial != null && reverseCommaSeparated) {
			formattedName = lastName + ", " + firstName + " " + middleInitial;
		} else if (firstName != null && lastName != null && middleInitial == null && !reverseCommaSeparated) {
			formattedName = firstName + " " + lastName;
		} else if (firstName != null && lastName != null && middleInitial == null && reverseCommaSeparated) {
			formattedName = lastName + ", " + firstName;
		} else if (firstName != null && lastName == null) {
			formattedName = firstName;
		} else if (firstName == null && lastName != null) {
			formattedName = firstName;
		}

		return formattedName;
	}

	/**
	 * Returns a formatted name.
	 * 
	 * @param firstName
	 *            a first name
	 * @param lastName
	 *            a last name
	 * @param reverseCommaSeparated
	 *            if true, the name is formatted as Doe, John; if false, the
	 *            name is formatted as John Doe
	 * @return a formatted name
	 */
	public static String format(String firstName, String lastName, boolean reverseCommaSeparated)
	{
		return format(firstName, lastName, null, reverseCommaSeparated);
	}

	/**
	 * Returns a formatted name as <first name> <last name>.
	 * 
	 * @param firstName
	 *            a first name
	 * @param lastName
	 *            a last name
	 * @return a formatted name as first name last name (John Doe)
	 */
	public static String format(String firstName, String lastName)
	{
		return format(firstName, lastName, false);
	}

	/**
	 * Returns a formatted name.
	 * 
	 * @param firstName
	 *            a first name
	 * @param lastName
	 *            a last name
	 * @return a formatted name as first name last name (John Doe)
	 */
	public static String format(String firstName, String lastName, String middleInitial)
	{
		return format(firstName, lastName, middleInitial, false);
	}
}
