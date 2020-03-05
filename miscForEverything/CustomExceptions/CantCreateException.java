package miscForEverything.CustomExceptions;

public class CantCreateException extends Exception{

    public CantCreateException(){
        super("Unable to create an instance of this class!");
        System.err.println("Unable to create an instance of this class!" + System.lineSeparator() + "Program will now close");
        System.gc();
        System.exit(-1);
    }

    public CantCreateException(String msg){
        super(msg);
        System.err.println("Cant create instance of " + msg + System.lineSeparator() + "Program will now close");
        System.gc();
        System.exit(-1);
    }
}
