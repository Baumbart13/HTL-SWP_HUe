package date2020_01_23;

public class Chess {
    public static void main(String[] args) {

        int sizeX = 8, sizeY = 8;
        try {
            if (args.length > 1) {
                sizeX = Integer.parseInt(args[0]);
                sizeY = Integer.parseInt(args[1]);
            } else if (args.length == 1) {
                sizeX = sizeY = Integer.parseInt(args[0]);
            }
        }catch (NumberFormatException e){

        }

        Checkerboard board = new Checkerboard(sizeX, sizeY);
        board.printBoard();
    }
}
