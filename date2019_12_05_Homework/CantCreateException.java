package date2019_12_05_Homework;

public class CantCreateException extends Exception{
    public CantCreateException(){
        super("Unable to create an instance of this class!");
        System.gc();
        System.exit(-1);
    }
    public CantCreateException(String msg){
        super(msg);
        System.gc();
        System.exit(-1);
    }
}
