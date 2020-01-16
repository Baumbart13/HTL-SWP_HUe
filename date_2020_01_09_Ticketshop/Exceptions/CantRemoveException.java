package date_2020_01_09_Ticketshop.Exceptions;

public class CantRemoveException extends Exception {
    public CantRemoveException (){
        this("Unable to remove an element!");
    }

    public CantRemoveException (String msg){
        super(msg);
        System.gc();
        System.exit(-1);
    }
}
