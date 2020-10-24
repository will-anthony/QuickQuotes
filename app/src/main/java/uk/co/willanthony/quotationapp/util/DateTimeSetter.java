package uk.co.willanthony.quotationapp.util;

import java.util.Calendar;

public class DateTimeSetter {

    private Calendar calendar;

    public DateTimeSetter() {
        this.calendar = Calendar.getInstance();
    }

    public String getDate() {
        return calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);
    }

    public String getTime() {
        return formatTime(calendar.get(Calendar.HOUR)) + ":" + formatTime(calendar.get(Calendar.MINUTE));
    }

    private String formatTime(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }
}
