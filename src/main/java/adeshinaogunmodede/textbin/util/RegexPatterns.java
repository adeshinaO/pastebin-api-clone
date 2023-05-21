package adeshinaogunmodede.textbin.util;

public class RegexPatterns {
    /**
     * Matches Date and Time values with time zone offsets. E.g., `2023-02-12T01:01:35:43+01:00`
     */
    public static final String VALID_DATE_TIME_AND_ZONE = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}((\\+|-)[0-1][0-9]:[0-9][0-9])?$";
}
