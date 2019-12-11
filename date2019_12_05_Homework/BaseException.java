package date2019_12_05_Homework;

public class BaseException extends Exception {

    public BaseException(String msg){
        super(msg);
    }

    public BaseException(){
        super("Wrong Base");
    }
}
