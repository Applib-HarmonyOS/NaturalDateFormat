package tk.zielony.naturaldateformat;

import ohos.app.Context;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import java.util.TimeZone;

/**
 * Created by Marcin on 29.01.2016.
 */
public abstract class NaturalDateFormat {

    public static final int DAYS = 1;
    public static final int MONTHS = 2;
    public static final int HOURS = 4;
    public static final int MINUTES = 8;
    public static final int YEARS = 16;
    public static final int SECONDS = 32;
    public static final int DATE = DAYS | MONTHS | YEARS;
    public static final int TIME = SECONDS | MINUTES | HOURS;
    public static final int WEEKDAY = 64;

    protected Context context;

    protected int format;

    protected TimeZone zone;

    protected Chronology chronology;

    public NaturalDateFormat(Context context, int format) {
        this.context = context;
        this.format = format;
    }

    /**
     * NaturalDateFormat constructor.
     *
     * @param context context
     * @param chronology chronology
     * @param format format
     */
    public NaturalDateFormat(Context context, Chronology chronology, int format) {
        this.context = context;
        this.chronology = chronology;
        this.format = format;
    }

    /**
     * NaturalDateFormat constructor.
     *
     * @param context context
     * @param zone TimeZone
     * @param format format
     */
    public NaturalDateFormat(Context context, TimeZone zone, int format) {
        this.context = context;
        this.zone = zone;
        this.format = format;
    }

    protected boolean hasFormat(int f) {
        return (format & f) != 0;
    }

    /**
     * format.
     *
     * @param dateTime dateTime as input.
     * @return  formatTime.
     */
    public String format(long dateTime) {
        DateTime now;
        if (chronology != null) {
            now = DateTime.now(chronology);
        } else {
            now = DateTime.now(DateTimeZone.forTimeZone(zone));
        }
        DateTime then = new DateTime(dateTime);
        if ((format & DATE) != 0) {
            return formatDate(now, then);
        }
        return formatTime(now, then);
    }

    /**
     * format.
     *
     * @param nowDateTime nowDateTime
     * @param dateTime dateTime
     * @return formatDate
     */
    public String format(long nowDateTime, long dateTime) {
        DateTime now = new DateTime(nowDateTime);
        DateTime then = new DateTime(dateTime);
        if ((format & DATE) != 0) {
            return formatDate(now, then);
        }
        return formatTime(now, then);
    }

    protected abstract String formatTime(DateTime now, DateTime then);

    protected abstract String formatDate(DateTime now, DateTime then);
}
