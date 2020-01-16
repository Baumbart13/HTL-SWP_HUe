package date_2020_01_09_Ticketshop.res;

import date_2020_01_09_Ticketshop.Address;

import java.util.GregorianCalendar;

public abstract class Person {
    protected String name;
    protected GregorianCalendar birthDate;
    protected Sex sex;
    protected Address residence;

    public Person(String name, GregorianCalendar birthDate, Address residence, Sex sex){
        this.name = name;
        this.birthDate = birthDate;
        this.residence = residence;
        this.sex = sex;
    }

    public String getName(){
        return this.name;
    }

    public String getBirthDate(){
        return this.birthDate.getTime().toInstant().toString();
    }

    public Sex getSex(){
        return this.sex;
    }

    public enum Sex{
        MALE,
        FEMALE,
        TRANS,
        OTHER
    }
}
