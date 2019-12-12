package date2019_12_05_Homework;

public class BaseConverter {

    protected int baseIn = 0;
    protected int baseOut = 0;

    public BaseConverter(int baseIn, int baseOut){
        this.baseIn = baseIn;
        this.baseOut = baseOut;
    }

    public BaseConverter(){
        this.baseIn = 0;
        this.baseOut = 0;
    }

    private static Integer baseToDecimal(Integer input, Integer baseIn){
        Integer sum = 0;

        int power = input.toString().length()-1;
        for(int i = 0; i < input.toString().length(); ++i, --power){
            sum += Character.getNumericValue(input.toString().charAt(i)) * (int)Math.pow(baseIn, power);
        }

        return sum;
    }

    /*private static Integer decimalToBase(Integer input, Integer baseOut){
        StringBuilder sum = new StringBuilder("");
        for( ; input > 0; input /= baseOut){
            sum.append( input % baseOut );
        }
    }*/


    /*public int calculationFromDezToN(int valueNumbersystem, int value) {	//	from dez in other
        int[] arrResult = new int[proofLength(valueNumbersystem, value)];
        int temp = value;
        for (int i = 0; i < arrResult.length; i++) {
            arrResult[arrResult.length - i - 1] = temp % valueNumbersystem;
            temp = temp / valueNumbersystem;
        }
        return (arrayIntToInt(arrResult));
    }*/

    private static Integer decimalToBase(Integer input, Integer baseOut){
        StringBuilder sum = new StringBuilder("");
        for( ; input > 0; input /= baseOut){
            sum.append(input%baseOut);
        }

        return Integer.parseInt(sum.reverse().toString());
    }

    public int convert(int input) throws BaseException{

        if(baseIn == baseOut){
            throw new BaseException("Same Bases!");
        }

        return decimalToBase(baseToDecimal(input, this.baseIn), baseOut);
    }

    public static int convert(int input, int baseIn, int baseOut) throws BaseException{

        if(baseIn == baseOut){
            throw new BaseException("Same Bases!");
        }

        if(baseIn == 10){
            return decimalToBase(input, baseOut);
        }
        return decimalToBase(baseToDecimal(input, baseIn), baseOut);

    }

    public String toString(){
        return ("BaseIn = " + this.baseIn + "\nBaseOut = " + this.baseOut);
    }
}
