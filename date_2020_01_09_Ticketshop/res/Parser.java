package date_2020_01_09_Ticketshop.res;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class Parser {
    protected File file;
    protected FileInputStream fileStream;

    public abstract void setFile(String filePath) throws FileNotFoundException;

    public File File(){
        return this.file;
    }

    public void closeFile(){
        try{
            this.fileStream.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally{
            System.gc();
        }
    }
}
