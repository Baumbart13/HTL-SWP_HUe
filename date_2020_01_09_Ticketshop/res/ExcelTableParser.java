package date_2020_01_09_Ticketshop.res;

import date_2020_01_09_Ticketshop.UI;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;

public class ExcelTableParser extends date_2020_01_09_Ticketshop.res.Parser{

    private XSSFWorkbook wb;

    public ExcelTableParser(String pathWithFile){
        try {
            setFile(pathWithFile);
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
    }

    public void setFile(String filePath) throws FileNotFoundException{/*
        if(!this.file.getName().isEmpty()) {
            this.closeFile();
        }*/

        if(!new File(filePath).isFile() || !filePath.endsWith(".xlsx")){
            throw new FileNotFoundException();
        }

        this.file = new File(filePath);
        this.fileStream = new FileInputStream(this.file);

        try{
            this.wb = new XSSFWorkbook(this.fileStream);
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private int chooseRightCol(String in){
        for (int i = 0; i < this.wb.getSheetAt(0).getRow(0).getLastCellNum(); ++i) {
            if (in.toLowerCase().equals(this.wb.getSheetAt(0).getRow(0).getCell(i).getStringCellValue().toLowerCase())) {
                return i;
            }
        }

        return -1;
    }

    private BigInteger chooseRightRow(String in){

        for(int i = 0; i < this.wb.getSheetAt(0).getLastRowNum(); ++i){
            if(in.toLowerCase().equals(this.wb.getSheetAt(0).getRow(i).getCell(1).getStringCellValue().toLowerCase())){
                return BigInteger.valueOf(i);
            }
        }

        String Term = "";

        for(int i = 0; i < this.wb.getSheetAt(0).getLastRowNum(); ++i){

            Term = this.wb.getSheetAt(0).getRow(i).getCell(0).getStringCellValue();

            if(Term.equals(in)){
                return BigInteger.valueOf(i);
            }
        }
        return BigInteger.valueOf(-1);
    }

    public Object getData(String Col, String Row, Class<?> type){
        int column = chooseRightCol(Col);
        BigInteger row = chooseRightRow(Row);

        if(column < 0 || row.intValue() < 0){
            return null;
        }

        XSSFSheet sheet = this.wb.getSheetAt(0);
        Cell out = sheet.getRow(row.intValue()).getCell(column);

        Object name = type.getSimpleName();
        // Integer
        if(name.equals(byte.class.getSimpleName())){
            return Integer.valueOf(out.getStringCellValue()).byteValue();
        }
        if(name.equals(short.class.getSimpleName())){
            return Integer.valueOf(out.getStringCellValue()).shortValue();
        }
        if(name.equals(int.class.getSimpleName())){
            return Integer.valueOf(out.getStringCellValue()).intValue();
        }
        if(name.equals(long.class.getSimpleName())){
            return Integer.valueOf(out.getStringCellValue()).longValue();
        }
        // Floating Points
        if(name.equals(float.class.getSimpleName())){
            return Double.valueOf(out.getStringCellValue()).floatValue();
        }
        if(name.equals(double.class.getSimpleName())){
            return Double.valueOf(out.getStringCellValue()).doubleValue();
        }
        // Boolean
        if(name.equals(boolean.class.getSimpleName())){
            final String[] yeh = {"yes", "true", "ja"};
            final String[] nah = {"no", "false", "nein"};

            for(String str : yeh){
                if(str.equalsIgnoreCase(out.getStringCellValue())) return true;
            }
            for(String str : nah){
                if(str.equalsIgnoreCase(out.getStringCellValue())) return false;
            }
        }

        // default
        return out.getStringCellValue();
    }
}
