package date2020_01_23.Units;

import date2020_01_23.UI;
import date2020_01_23.Color;
import date2020_01_23.Coordinate;

public class Pawn extends Figure{

    public Pawn(Coordinate coord, Color color){
        super(coord, color);
    }

    public void moveTo(Coordinate coord){
        if(this.getColor() == Color.WHITE){

        }else if(this.getColor() == Color.BLACK){

        }
    }

    public void print(){
        if(this.getColor() == Color.BLACK){
            System.out.print(UI.ANSI_BLACK + UI.ANSI_WHITE_BACKGROUND);
        }else if(this.getColor() == Color.WHITE){
            System.out.print(UI.ANSI_WHITE + UI.ANSI_BLACK_BACKGROUND);
        }

        System.out.print("P" + UI.ANSI_RESET);
    }
}
