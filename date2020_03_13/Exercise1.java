package date2020_03_13;

import java.util.LinkedList;

import static java.lang.Math.random;

public class Exercise1 {

    public static String foo(){
        int temperatures[][] = new int[14][10];

        for(int i  = 0; i < temperatures.length; ++i){
            for(int j = 0; j < temperatures[i].length; ++j){
                temperatures[i][j] = (int)(random()*15 + 20);
            }
        }

        StringBuilder returnedText = new StringBuilder("Temperaturen in Grad Celsius" + System.lineSeparator() + "----------------------------" + System.lineSeparator());
        //text.add("Temperaturen in Grad Celsius" + System.lineSeparator() + "----------------------------" + System.lineSeparator());
        int averageTemperature = 0;

        LinkedList<Integer> averageTemps = new LinkedList<Integer>();
        for(int day = 0; day < temperatures.length; ++day){

            int averageOnDay = 0;
            StringBuilder textOnDay = new StringBuilder();
            for(int measurement = 0; measurement < temperatures[day].length; ++measurement){
                averageOnDay += temperatures[day][measurement];
                textOnDay.append(temperatures[day][measurement] + " ");
            }

            averageOnDay /= temperatures[day].length;
            averageTemperature += averageOnDay;
            returnedText.append(textOnDay + "\t- Durchschnitss-Temperatur: " + averageOnDay + System.lineSeparator());
            //text.add(textOnDay.toString() + "\t- Durchschnitss-Temperatur: " + averageOnDay + System.lineSeparator());
        }
        averageTemperature /= temperatures.length;
        returnedText.append("Gesamt-Durchschnitss-Temperatur: " + averageTemperature + System.lineSeparator() + System.lineSeparator());

        return returnedText.toString();
    }
}
