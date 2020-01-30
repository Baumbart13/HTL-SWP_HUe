package date2020_01_23;

import date2020_01_23.Units.*;

import static date2020_01_23.Color.WHITE;

public class Checkerboard {
    Figure field[][];
    Figure units[];

    public Checkerboard(int size){
        this(size, size);
    }

    public Checkerboard(int sizeX, int sizeY){
        this.field = new Figure[sizeY][sizeX];
        this.units = new Figure[sizeY * 4];

        foo();
    }

    public void foo(){
        if(field[0].length < 8){
            System.err.println("Default initializing not possible");
            return;
        }

        System.err.println("X: " + field.length + "\tY: " + field[0].length);
        field[0][field[0].length/2-4] = new Rook(new Coordinate(field[0].length/2-4, 0), Color.BLACK);
        field[0][field[0].length/2-3] = new Jumper(new Coordinate(field[0].length/2-3, 0), Color.BLACK);
        field[0][field[0].length/2-2] = new Bishop(new Coordinate(field[0].length/2-2, 0), Color.BLACK);
        field[0][field[0].length/2-1] = new King(new Coordinate(field[0].length/2-1, 0), Color.BLACK);
        field[0][field[0].length/2] = new Queen(new Coordinate(field[0].length/2, 0), Color.BLACK);
        field[0][field[0].length/2+1] = new Bishop(new Coordinate(field[0].length/2+1, 0), Color.BLACK);
        field[0][field[0].length/2+2] = new Jumper(new Coordinate(field[0].length/2+2, 0), Color.BLACK);
        field[0][field[0].length/2+3] = new Rook(new Coordinate(field[0].length/2+3, 0), Color.BLACK);
        for(int i = 0; i < field[1].length; ++i){
            field[1][i] = new Pawn(new Coordinate(1, i), Color.BLACK);
        }

        for(int i = 0; i < field[field.length-2].length; ++i){
            field[field.length-2][i] = new Pawn(new Coordinate(field[field.length-2].length, i), WHITE);
        }
        field[field.length-1][field[field.length-1].length/2-4] = new Rook(new Coordinate(field[field.length-1].length/2-4, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2-3] = new Jumper(new Coordinate(field[field.length-1].length/2-3, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2-2] = new Bishop(new Coordinate(field[field.length-1].length/2-2, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2-1] = new King(new Coordinate(field[field.length-1].length/2-1, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2] = new Queen(new Coordinate(field[field.length-1].length/2, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2+1] = new Bishop(new Coordinate(field[field.length-1].length/2+1, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2+2] = new Jumper(new Coordinate(field[field.length-1].length/2+2, field.length-1), WHITE);
        field[field.length-1][field[field.length-1].length/2+3] = new Rook(new Coordinate(field[field.length-1].length/2+3, field.length-1), WHITE);
    }

    private static void printRow(int size){
        for(int i = 0; i < size; ++i){
            System.out.print(UI.ANSI_RED_BACKGROUND + "-");
        }
        System.out.println("-" + UI.ANSI_RESET);
    }

    public void printBoard(){

        printRow(this.field[0].length*2);
        for(int i = 0; i < this.field.length; ++i){
            System.out.print(UI.ANSI_RED_BACKGROUND + "|");
            for(int j = 0; j < this.field[i].length; ++j){

                if(this.field[i][j] == null){
                    System.out.print(" ");
                }else{
                    this.field[i][j].print();
                }
                System.out.print(UI.ANSI_RED_BACKGROUND + "|");
            }
            System.out.println(UI.ANSI_RESET);
            printRow(this.field[i].length*2);
        }
    }

    public int whiteLeft(){
        int out = 0;
        for(int i = 0; i < this.field.length; ++i){
            for(int j = 0; j < this.field[i].length; ++j){
                if(field[i][j] != null){
                    if(field[i][j].isAlive() && field[i][j].getColor() == WHITE){
                        ++out;
                    }
                }
            }
        }
        return out;
    }

    public int blackLeft(){
        int out = 0;
        for(int i = 0; i < this.field.length; ++i){
            for(int j = 0; j < this.field[i].length; ++j){
                if(field[i][j] != null){
                    if(field[i][j].isAlive() && field[i][j].getColor() == Color.BLACK){
                        ++out;
                    }
                }
            }
        }
        return out;
    }

    public int unitsLeft(){
        int out = 0;
        for(int i = 0; i < this.field.length; ++i){
            for(int j = 0; j < this.field[i].length; ++j){
                if(field[i][j] != null){
                    if(field[i][j].isAlive()){
                        ++out;
                    }
                }
            }
        }
        return out;
    }
}
