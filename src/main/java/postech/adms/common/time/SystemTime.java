package postech.adms.common.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class SystemTime {
	public static Calendar asCalendar() {
        return asCalendar(true);
    }

    public static Calendar asCalendar(boolean includeTime) {
        return asCalendar(Locale.getDefault(), TimeZone.getDefault(), includeTime);
    }

    public static Calendar asCalendar(Locale locale) {
        return asCalendar(locale, TimeZone.getDefault(), true);
    }

    public static Calendar asCalendar(TimeZone timeZone) {
        return asCalendar(Locale.getDefault(), timeZone, true);
    }
    
    public static Calendar asCalendar(Locale locale, TimeZone timeZone, boolean includeTime) {
        Calendar calendar = Calendar.getInstance(timeZone, locale);
        //calendar.setTimeInMillis(asMillis());
        if (!includeTime) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return calendar;
    }
    
    public static long asMillis() {
        return asMillis(true);
    }

    public static long asMillis(boolean includeTime) {
    	 return asCalendar(includeTime).getTimeInMillis();
    }

    // =========================================================================
    // 날짜 설정 (yyyyMMdd) interval 값 만큼 전날의 날짜
    // =========================================================================
    public static String getPlainDate(int interval) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, interval);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        strDate = sdf.format(cal.getTime());

        return strDate;
    }
}
