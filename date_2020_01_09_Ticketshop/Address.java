package date_2020_01_09_Ticketshop;

import date_2020_01_09_Ticketshop.res.ExcelTableParser;

import java.math.BigInteger;

public class Address {
    private static ExcelTableParser parser = new ExcelTableParser("C:\\Users\\Baumbart13\\IdeaProjects\\Programmieren - 2019-11-07\\src\\date_2020_01_09_Ticketshop\\res\\PostalCode_AT.xlsx");

    public final Places place = Places.AT;
    protected int postalCode;
    protected String village;

    protected InternExtern internExtern;
    protected boolean addressable;
    protected boolean mailbox;

    public Address(String village){
        if(parser.getData("PLZ", village, int.class) == null){
            this.postalCode = -1;
        }else{
            this.postalCode = (int) parser.getData("PLZ", village, int.class);
        }

        this.village = (String) parser.getData("Ort", village, String.class);
        this.internExtern = ((String)parser.getData("intern_extern", village, String.class) == "intern") ? InternExtern.intern : InternExtern.extern;
        if(parser.getData("adressierbar", village, String.class) == null){
            this.addressable = false;
        }else{
            this.addressable = (boolean)parser.getData("adressierbar", village, boolean.class);
        }

        if(parser.getData("Postfach", village, String.class) == null){
            this.mailbox = false;
        }else {
            this.mailbox = (boolean) parser.getData("Postfach", village, boolean.class);
        }
    }

    public Address(int postalCode){
        System.out.println(parser.getData("intern_extern", String.valueOf(postalCode), String.class));

        if(parser.getData("PLZ", String.valueOf(postalCode), int.class) == null){
            this.postalCode = -1;
        }else {
            this.postalCode = (int) parser.getData("PLZ", String.valueOf(postalCode), int.class);
        }

        this.village = (String) parser.getData("Ort", String.valueOf(postalCode), String.class);
        if(parser.getData("intern_extern", String.valueOf(postalCode), String.class) == null){
            this.internExtern = null;
        }else{
            if(parser.getData("intern_extern", String.valueOf(postalCode), String.class) == "intern"){
                this.internExtern = InternExtern.intern;
            }else if(parser.getData("intern_extern", String.valueOf(postalCode), String.class) == "extern"){
                this.internExtern = InternExtern.extern;
            }
        }
        if(parser.getData("adressierbar", String.valueOf(postalCode), String.class) == null){
            this.addressable = false;
        }else {
            this.addressable = (boolean) parser.getData("adressierbar", String.valueOf(postalCode), boolean.class);
        }

        if(parser.getData("Postfach", String.valueOf(postalCode), String.class) == null){
            this.mailbox = false;
        }else {
            this.mailbox = (boolean) parser.getData("Postfach", String.valueOf(postalCode), boolean.class);
        }
    }

    public int getPostalCode() {
        return postalCode;
    }

    public String getVillage() {
        return village;
    }

    public InternExtern getInternExtern() {
        return internExtern;
    }

    public boolean isAddressable() {
        return addressable;
    }

    public boolean isMailbox() {
        return mailbox;
    }

    private enum InternExtern{
        intern,
        extern;
    }
}
