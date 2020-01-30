package date2020_01_09_Ticketshop.Exceptions;

public class CantCreateException extends Exception{

    public CantCreateException(){
        this("Unable to create an instance of this class!");
    }

    public CantCreateException(String msg){
        super(msg);
        System.gc();
        System.exit(-1);
    }
}
