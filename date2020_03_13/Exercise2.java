package date2020_03_13;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Exercise2 {
    private static final int DAYS_PER_WEEK = 7;

    private static int getWeekOfYear(LocalDate date){
        return date.plusDays(1).get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
    }

    public static String foo(int year, int monthOfYear){
        return foo(year, Month.of(monthOfYear));
    }

    public static String foo(int year, Month MonthOfYear){
        LocalDate date = LocalDate.of(year, MonthOfYear.getValue(), 1);

        StringBuilder textOut = new StringBuilder("Kalender für " + MonthOfYear.name() + " " + year + System.lineSeparator());
        textOut.append("Wo\tMo\tDi\tMi\tDo\tFr\tSa\tSo" + System.lineSeparator());

        int temp = getWeekOfYear(date);
        textOut.append(temp + "\t");

        // fill empty space caused by month not starting with monday
        for(DayOfWeek day = DayOfWeek.MONDAY; !day.equals(date.getDayOfWeek()); day = day.plus(1)){
            textOut.append("\t");
        }

        // fill space with numbers til the end of the month
        for( ; date.getMonth().equals(MonthOfYear); date = date.plusDays(1)){
            if(date.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                textOut.append(date.getDayOfMonth() + System.lineSeparator());
                textOut.append(getWeekOfYear(date) + "\t");
            }else{
                textOut.append(date.getDayOfMonth() + "\t");
            }
        }

        return (textOut.toString() + System.lineSeparator());
    }
}
