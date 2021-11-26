package tk.zielony.naturaldateformat;

import ohos.app.Context;
import ohos.global.resource.NotExistException;
import ohos.global.resource.WrongTypeException;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;
import java.io.IOException;
import java.util.TimeZone;

/**
 * Created by Marcin on 2016-04-02.
 */
public class RelativeDateFormat extends NaturalDateFormat {
    private static final HiLogLabel HI_LOG_LABEL = new HiLogLabel(0, 0, "RelativeDateFormat");

    public RelativeDateFormat(Context context, Chronology chronology, int format) {
        super(context, chronology, format);
    }

    public RelativeDateFormat(Context context, int format) {
        super(context, format);
    }

    public RelativeDateFormat(Context context, TimeZone zone, int format) {
        super(context, zone, format);
    }

    @Override
    protected String formatTime(DateTime now, DateTime then) {
        StringBuilder text = new StringBuilder();
        if (hasFormat(HOURS)) {
            formatHours(now, then, text);
        } else if (hasFormat(MINUTES)) {
            formatMinutes(now, then, text);
        } else if (hasFormat(SECONDS)) {
            formatSeconds(now, then, text);
        }
        return text.toString();
    }

    private void formatHours(DateTime now, DateTime then, StringBuilder text) {
        try {
            int hoursBetween = Hours.hoursBetween(now.toLocalTime(), then.toLocalTime()).getHours();
            if (hoursBetween == 0) {
                if (hasFormat(MINUTES)) {
                    formatMinutes(now, then, text);
                } else {
                    text.append(context.getString(ResourceTable.String_now));
                }
            } else if (hoursBetween > 0) {
                // in N hours
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inHours)
                        .getPluralString(hoursBetween, hoursBetween));
            } else {
                // N hours ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_hoursAgo)
                        .getPluralString(-hoursBetween, -hoursBetween));
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatHours");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatHours");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatHours");
        }
    }

    private void formatMinutes(DateTime now, DateTime then, StringBuilder text) {
        try {
            int minutesBetween = Minutes.minutesBetween(now.toLocalTime(), then.toLocalTime()).getMinutes();
            if (minutesBetween == 0) {
                if (hasFormat(SECONDS)) {
                    formatSeconds(now, then, text);
                } else {
                    text.append(context.getString(ResourceTable.String_now));
                }
            } else if (minutesBetween > 0) {
                // in N hours
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inMinutes)
                        .getPluralString(minutesBetween, minutesBetween));

            } else {
                // N hours ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_minutesAgo)
                        .getPluralString(minutesBetween, minutesBetween));
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatMinutes");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatMinutes");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatMinutes");
        }
    }

    private void formatSeconds(DateTime now, DateTime then, StringBuilder text) {
        try {
            int secondsBetween = Seconds.secondsBetween(now.toLocalTime(), then.toLocalTime()).getSeconds();
            if (secondsBetween == 0) {
                text.append(context.getString(ResourceTable.String_now));
            } else if (secondsBetween > 0) {
                // in N seconds
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inSeconds)
                        .getPluralString(secondsBetween, secondsBetween));

            } else {
                // N seconds ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_secondsAgo)
                        .getPluralString(-secondsBetween, -secondsBetween));
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatSeconds");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatSeconds");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatSeconds");
        }
    }

    @Override
    protected String formatDate(DateTime now, DateTime then) {
        StringBuilder text = new StringBuilder();
        if (hasFormat(YEARS)) {
            formatYears(now, then, text);
        } else if (hasFormat(MONTHS)) {
            formatMonths(now, then, text);
        } else if (hasFormat(DAYS)) {
            formatDays(now, then, text);
        }
        if (hasFormat(TIME)) {
            formateDateSb(text, then);
        }
        return text.toString();
    }

    private void formateDateSb(StringBuilder text, DateTime then) {
        StringBuilder pattern = new StringBuilder();
        if ((format & HOURS) != 0) {
            pattern.append("hh");
        }
        if ((format & MINUTES) != 0) {
            pattern.append(pattern.length() == 0 ? "mm" : ":mm");
        }
        if ((format & SECONDS) != 0) {
            pattern.append(pattern.length() == 0 ? "ss" : ":ss");
        }
        text.append(", " + DateTimeFormat.forPattern(pattern.toString()).print(then.toInstant()));
    }

    private void formatYears(DateTime now, DateTime then, StringBuilder text) {
        try {
            int yearsBetween = Years.yearsBetween(now.toLocalDate(), then.toLocalDate()).getYears();
            if (yearsBetween == 0) {
                if ((format & MONTHS) != 0) {
                    formatMonths(now, then, text);
                } else {
                    text.append(context.getString(ResourceTable.String_thisYear));
                }
            } else if (yearsBetween > 0) {
                // in N years
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inYears)
                        .getPluralString(yearsBetween, yearsBetween));

            } else {
                // N years ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_yearsAgo)
                        .getPluralString(-yearsBetween, -yearsBetween));
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatYears");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatYears");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatYears");
        }
    }

    private void formatMonths(DateTime now, DateTime then, StringBuilder text) {
        try {
            int monthsBetween = Months.monthsBetween(now.toLocalDate(), then.toLocalDate()).getMonths();
            if (monthsBetween == 0) {
                if ((format & DAYS) != 0) {
                    formatDays(now, then, text);
                } else {
                    text.append(context.getString(ResourceTable.String_thisMonth));
                }
            } else if (monthsBetween > 0) {
                // in N months
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inMonths)
                        .getPluralString(monthsBetween, monthsBetween));

            } else {
                // N months ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_monthsAgo)
                        .getPluralString(-monthsBetween, -monthsBetween));
            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatMonths");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatMonths");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatMonths");
        }
    }

    private void formatDays(DateTime now, DateTime then, StringBuilder text) {
        try {
            int daysBetween = Days.daysBetween(now.toLocalDate(), then.toLocalDate()).getDays();
            if (daysBetween == 0) {
                text.append(context.getString(ResourceTable.String_today));
            } else if (daysBetween > 0) {
                // in N days
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_inDays)
                        .getPluralString(daysBetween, daysBetween));

            } else {
                // N days ago
                text.append(context.getResourceManager().getElement(ResourceTable.Plural_carbon_daysAgo)
                        .getPluralString(-daysBetween, -daysBetween));

            }
        } catch (IOException e) {
            HiLog.error(HI_LOG_LABEL, "getElement IOException in formatDays");
        } catch (NotExistException e) {
            HiLog.error(HI_LOG_LABEL, "getElement NotExistException in formatDays");
        } catch (WrongTypeException e) {
            HiLog.error(HI_LOG_LABEL, "getElement WrongTypeException in formatDays");
        }
    }
}
