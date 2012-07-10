package org.frantictools.franticmod.util;

/** This class is a collection of random shit that I don't want to repeat elsewhere
 * @author improv32 */
public class StringUtils {
	public static String repeat(String str, int n)
	{
		return new String(new char[n]).replace("\0", str);
	}
}
