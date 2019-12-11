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

    private static Integer toDecimal(Integer input, Integer baseIn){
        Integer sum = 0;

        int power = input.toString().length()-1;
        for(int i = 0; i < input.toString().length(); ++i, --power){
            sum += Character.getNumericValue(input.toString().charAt(i)) * (int)Math.pow(baseIn, power);
        }

        return sum;
    }

    private static Integer toBase(Integer input, Integer baseOut){
        StringBuilder sum = new StringBuilder("");
        for( ; input > 1; input /= baseOut){
            sum.append(input%baseOut);
        }

        return Integer.parseInt(sum.toString());
    }

    public int convert(int input) throws BaseException{

        if(baseIn == baseOut){
            throw new BaseException("Same Bases!");
        }

        return toBase(toDecimal(input, this.baseIn), baseOut);
    }

    public static int convert(int input, int baseIn, int baseOut) throws BaseException{

        if(baseIn == baseOut){
            throw new BaseException("Same Bases!");
        }

        return (baseIn > baseOut) ? toDecimal(toBase(input, baseIn), baseOut) : toBase(toDecimal(input, baseIn), baseOut);

        /*if(baseIn > baseOut){
            return toDecimal(toBase(input, baseIn), baseOut);
        }
        return toBase(toDecimal(input, baseIn), baseOut);*/
    }

    public String toString(){
        return ("BaseIn = " + this.baseIn + "\nBaseOut = " + this.baseOut);
    }
}
