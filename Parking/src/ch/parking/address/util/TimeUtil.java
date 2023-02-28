package ch.parking.address.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Helper functions for handling dates.
 *
 * @author Mecit Zira
 */
public class TimeUtil {

    /** The date pattern that is used for conversion. Change as you wish. */
    private static final String TIME_PATTERN = "HH:mm";

    /** The date formatter. */
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param time the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalTime time) {
        if (time == null) {
            return null;
        }
        return TIME_FORMATTER.format(time);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalTime parse(String dateString) {
        try {
            return TIME_FORMATTER.parse(dateString, LocalTime::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks the String whether it is a valid date.
     *
     * @param timeString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String timeString) {
        // Try to parse the String.
        return TimeUtil.parse(timeString) != null;
    }
    
    
}