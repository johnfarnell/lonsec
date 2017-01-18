package au.com.lonsec.unit;

import java.util.Calendar;

/**
 * Created by Countrywide Austral on 18-Jan-17.
 */
public class CalendarHelper {
    public static Calendar getCalendar(int year, int month, int day) {
        Calendar calFRS1 = Calendar.getInstance();
        calFRS1.set(Calendar.YEAR, year);
        calFRS1.set(Calendar.MONTH,month);
        calFRS1.set(Calendar.DAY_OF_MONTH, day);
        calFRS1.set(Calendar.HOUR_OF_DAY, 0);
        calFRS1.set(Calendar.MINUTE, 0);
        calFRS1.set(Calendar.SECOND, 0);
        calFRS1.set(Calendar.MILLISECOND, 0);
        return calFRS1;
    }}
