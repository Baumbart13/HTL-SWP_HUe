package date2020_02_27;

import java.time.*;

import miscForEverything.CustomExceptions.CantCreateException;

public class DateSearches {
    private DateSearches() throws CantCreateException{
        throw new CantCreateException(this.toString());
    }

    public static int howManyMondays(LocalDate until){
        LocalDate today = LocalDate.now();

        if(today.isBefore(until)){
            return -1;
        }

        int out = 0;
        while(today.isAfter(until)){
            if(today.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                ++out;
                today = today.minusWeeks(1);
            }else {
                today = today.minusDays(1);
            }
        }

        return out;
    }

    public static int howManyMondaysInYear(LocalDate year){
        int startYear = year.getYear()+1;
        int mondays = 0;
        while(!(year.getYear() == startYear)){
            if(year.getDayOfWeek() == DayOfWeek.MONDAY){
                ++mondays;
                year = year.plusWeeks(1);
            }else{
                year = year.plusDays(1);
            }
        }

        return mondays;
    }

    public static int howManyMondaysInYear(int year){
        if(year < LocalDate.MIN.getYear() || year > LocalDate.MAX.getYear()){
            return -1;
        }
        return howManyMondaysInYear(LocalDate.of(year, Month.JANUARY, 1));
    }

    public static int howManyMondays(LocalDate since, LocalDate until){
        if(since.isAfter(until)){
            LocalDate temp = since;
            since = until;
            until = temp;
        }

        int out = 0;
        while(!since.until(until).isZero()){
            if(since.getDayOfWeek().equals(DayOfWeek.MONDAY)){
                ++out;
            }
            since = since.plusDays(1);
        }

        return out;
    }

    public static int howManyMondaysSince1900(){
        return howManyMondays(LocalDate.EPOCH);
    }

    public static int howManyMondaysSince1900(LocalDate since){
        return howManyMondays(LocalDate.EPOCH, since);
    }

    /**
     * @param americanFormat true formats to american way: MM-DD-YY
     *                       false formats to european way: YYYY-MM-DD
     */
    public static String lastMondayOfYear(int year, boolean americanFormat){
        LocalDate date = LocalDate.of(year, Month.DECEMBER, Month.DECEMBER.maxLength());

        while(!date.getDayOfWeek().equals(DayOfWeek.MONDAY)){
            date = date.minusDays(1);
        }

        StringBuilder monthOut = new StringBuilder(Integer.toString(date.getMonthValue()));
        StringBuilder dayOut = new StringBuilder(Integer.toString(date.getDayOfMonth()));
        StringBuilder yearOut = new StringBuilder(Integer.toString(date.getYear()));

        if(monthOut.length() == 1){
            monthOut.insert(0,'0');
        }
        if(dayOut.length() == 1){
            yearOut.insert(0, '0');
        }

        String out;
        if(americanFormat){
            yearOut.delete(0, 2);
            out = new String(monthOut.toString() + "-" + dayOut.toString() + "-" + yearOut.toString());
        }else{
            out = new String(yearOut.toString() + "-" + monthOut.toString() + "-" + dayOut.toString());
        }
        return out;
    }

    public static int daysUntil(LocalDate start, LocalDate until){
        int out = 0;
        while(!start.until(until).isZero()){
            ++out;
            start = start.plusDays(1);
        }
        return out;
    }

    public static DayOfWeek getWeekdayOfDate(LocalDate date){
        return date.getDayOfWeek();
    }

    /**
     * Used format is DD-MM-YYYY
     */
    public static LocalDate[] palindromeDates(LocalDate from, LocalDate to){
        if(from.isAfter(to)){
            LocalDate temp = from;
            from = to;
            to = temp;
        }

        java.util.ArrayList<LocalDate> dates = new java.util.ArrayList<LocalDate>(1);
        while(!from.isEqual(to)){
            StringBuilder palindrome = new StringBuilder();
            // add the correct format of date
            palindrome.append(from.getDayOfWeek().getValue());
            if(palindrome.length() == 1){
                palindrome.insert(0,'0');
            }

            palindrome.append(from.getMonthValue());
            if(palindrome.length() == 3){
                palindrome.insert(2,'0');
            }

            palindrome.append(from.getYear());

            // check if palindrome
            if(palindrome.equals(palindrome.reverse())){
                dates.add(from);
            }

            // prepare for next iteration
            from = from.plusDays(1);
        }

        return dates.toArray(new LocalDate[dates.size()]);
    }
}
