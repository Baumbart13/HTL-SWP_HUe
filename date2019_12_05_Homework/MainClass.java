package date2019_12_05_Homework;

public class MainClass {

    public static boolean checkInput(String input){
        boolean ok = true;
        try{
            int temp = Integer.parseInt(input);
        }catch(NumberFormatException nfE){
            nfE.printStackTrace();
            ok = false;
        }

        return ok;
    }

    public static int menu(){
        System.out.println("What do you want to do?\n1.: Convert\n0.: Exit");

        String str = UI.readString();

        switch(str.toLowerCase()){
            case "0":
                return -1;
            case "1":
                return 0;
            default:
                System.out.println("Bruh what happened - switch");
                return -1;
        }
    }

    public static void converting(){
        String input = "";
        String output = "";
        String baseIn = "";
        String baseOut = "";

        try {
            System.out.println("Gimme Input base (2-10):");
            baseIn = UI.readString();

            System.out.println("Gimme Output base (2-10)");
            do{
                baseOut = UI.readString();
            }while(baseOut.equals(baseIn));

            System.out.println("gimme number");
            input = UI.readString();
            checkInput(input);

            output = Integer.toString(BaseConverter.convert(Integer.parseInt(input), Integer.parseInt(baseIn), Integer.parseInt(baseOut)));
            System.out.println("\nDone\t" + output + "\n\n");
        }
        catch(BaseException bE){
            bE.printStackTrace();
        }
    }

    public static void main(String[] args) {
        boolean stay = true;
        while(stay){

            switch(menu()){
                case -1:
                    stay = false;
                    break;
                case 0:
                    converting();
                    break;
                default:
                    System.err.println("Bruh, what happened - " + new Throwable().getStackTrace()[0].getLineNumber());
            }
        }
        System.gc();
        System.exit(0);
    }
}
