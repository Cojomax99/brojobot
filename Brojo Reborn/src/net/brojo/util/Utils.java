package net.brojo.util;

import java.util.regex.Pattern;

/**
 * Utility class with assorted helper methods.
 */
public class Utils {
	
	/**
	 * Performs a hostmask-level check on a user to see if said user matches a given hostmask
	 * @param mask Hostmask to check against
	 * @param line Should be in the form 'line.getUsername() + "@" + line.getHost()' of an IRCLine
	 * @return Whether the user in line is the same as mask
	 */
	public static boolean matchesMask(String mask, String line) {
        line = toIRCLowercase(line);
        mask = toIRCLowercase(mask);
        mask = mask.replaceAll("\\.", "\\\\.");
        mask = mask.replaceAll("\\$", "\\\\\\$");
        mask = mask.replaceAll("\\(", "\\\\(");
        mask = mask.replaceAll("\\)", "\\\\)");
        mask = mask.replaceAll("\\{", "\\\\{");
        mask = mask.replaceAll("\\}", "\\\\}");
        mask = mask.replaceAll("\\+", "\\\\+");
        mask = mask.replaceAll("\\|", "\\\\|");
        mask = mask.replaceAll("\\?", ".");
        mask = mask.replaceAll("\\*", ".*");
        return Pattern.matches(mask, line);
    }
    public static String toIRCLowercase(String s) {
        s = s.toLowerCase();
        s = s.replaceAll("\\[", "{");
        s = s.replaceAll("\\]", "}");
        s = s.replaceAll("\\\\", "|");
        s = s.replaceAll("\\^", "~");
        return s;
    }
}
