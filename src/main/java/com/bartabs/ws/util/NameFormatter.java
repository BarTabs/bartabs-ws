package com.bartabs.ws.util;

public class NameFormatter
{
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

	public static String format(String firstName, String lastName, boolean reverseCommaSeparated)
	{
		return format(firstName, lastName, null, reverseCommaSeparated);
	}

	public static String format(String firstName, String lastName)
	{
		return format(firstName, lastName, false);
	}
}
