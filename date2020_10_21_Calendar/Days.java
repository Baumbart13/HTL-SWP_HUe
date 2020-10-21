package date2020_10_21_Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public enum Days {
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY);

    private int count = 0;
    private static Days[] orderedValues = values().clone();
    private DayOfWeek dayOfWeek = null;

    Days(DayOfWeek day){
        this.count = 0;
        this.dayOfWeek = day;
    }

    /**
     * @return The day as java.time.DayOfWeek.
     */
    public DayOfWeek getDayOfWeek(){
        return this.dayOfWeek;
    }

    /**
     * @return The count of this object.
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Increases count by given positive number n.
     * @param n The added value to this objects count.
     */
    public void increaseCount(int n){
        if(n < 1){
            return;
        }
        this.count += n;
    }

    /**
     * Increases count by 1.
     */
    public void increaseCount(){
        ++this.count;
    }

    /**
     * Decreases count by given positive number n.
     * @param n The subtracted value to this objects count.
     */
    public void decreaseCount(int n){
        if(n < 1){
            return;
        }
        this.count -= n;
    }

    /**
     * Decreases count by 1.
     */
    public void decreaseCount(){
        --this.count;
    }

    public String toString(boolean hexAddress){
        if(hexAddress){
            return getClass().getName() + '@' + Integer.toHexString(hashCode());
        }
        return this.toString();
    }

    @Override
    public String toString(){
        return this.name().charAt(0) + this.name().substring(1).toLowerCase() + ":" + this.getCount();
    }

    public static String[] allToString(){
        String[] out = new String[values().length];

        for(int i = 0; i < values().length; ++i){
            out[i] = values()[i].toString();
        }

        return out;
    }

    /**
     * Counts the occurrences of the days in given List and adds them to the previous value.
     * @param days The days which should be
     */
    public static void countDays(List<LocalDate> days){
        for(LocalDate day : days){

            if(day.getDayOfWeek() == DayOfWeek.MONDAY){
                Days.MONDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.TUESDAY){
                Days.TUESDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.WEDNESDAY){
                Days.WEDNESDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.THURSDAY){
                Days.THURSDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.FRIDAY){
                Days.FRIDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.SATURDAY){
                Days.SATURDAY.increaseCount();
            }else if(day.getDayOfWeek() == DayOfWeek.SUNDAY){
                Days.SUNDAY.increaseCount();
            }

        }
        return;
    }

    /**
     * Erases all counts of the days.
     */
    public static void eraseCounts(){
        for(Days day : Days.values()){
            day.count = 0;
        }
    }

    /**
     * Sorts all days based on the count. After any change and on start, this method must be used for achieving the
     * correct results.
     */
    public static void orderByHighestCount(){

        boolean unsorted = true;
        boolean changedAnything_old = false;

        while(unsorted){

            boolean changedAnything_new = false;
            for(int i = 0; i < orderedValues.length-1; ++i){

                if(orderedValues[i].count < orderedValues[i+1].count){
                    Days temp = orderedValues[i];
                    orderedValues[i] = orderedValues[i+1];
                    orderedValues[i+1] = temp;
                    changedAnything_new = true;
                }
            }
            if(!changedAnything_new && !changedAnything_old){
                unsorted = false;
            }
            changedAnything_old = changedAnything_new;
        }

        return;
    }

    /**
     * The count-ordered array of days. After any change and on start, "orderByHighestCount" must be used for achieving
     * the correct results of this method.
     * @return The array ordered by the count of the days.
     */
    public static Days[] orderedByHighestCount(){
        return orderedValues;
    }

    /**
     * The day with the highest count. After any change and on start, "orderByHighestCount" must be used for achieving
     * the correct results of this method.
     * @return The day with the highest count.
     */
    public static Days highestDay(){
        return orderedValues[0];
    }
}
