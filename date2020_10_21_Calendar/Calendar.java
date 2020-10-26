package date2020_10_21_Calendar;

import date2020_10_21_Calendar.res.FeiertagAPIParser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.LinkedList;

public class Calendar {
    static FeiertagAPIParser parser = new FeiertagAPIParser();

    public static void main(String[] args) {
        try{
            LinkedList<LocalDate> dates = parser.getDates(LocalDate.now().getYear(), LocalDate.now().getYear()+20);
            Days.countDays(dates);
            Days.orderByHighestCount();

            System.err.println(Days.highestDay());
            for(int i = 0; i < Days.orderedByHighestCount().length; ++i){
                if(Days.orderedByHighestCount()[i].getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                   Days.orderedByHighestCount()[i].getDayOfWeek().equals(DayOfWeek.SUNDAY)){
                    continue;
                }
                System.out.println(Days.orderedByHighestCount()[i].toString());
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
