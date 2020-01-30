package date2020_01_23.Units;

import date2020_01_23.UI;
import date2020_01_23.Color;
import date2020_01_23.Coordinate;

public class King extends Figure{

    public King(Coordinate coord, Color color){
        super(coord, color);
    }

    public void moveTo(Coordinate coord) {
        if(coord.X < 0 || coord.Y < 0){
            return;
        }
        int dx = (int)java.lang.Math.sqrt(coord.X * coord.X - this.coord.X * this.coord.X);
        int dy = (int)java.lang.Math.sqrt(coord.Y * coord.Y - this.coord.Y * this.coord.Y);

        if(!(dx > 1) || !(dy > 1)){
            this.coord = coord;
        }
    }

    public void print(){
        if(this.getColor() == Color.BLACK){
            System.out.print(UI.ANSI_BLACK + UI.ANSI_WHITE_BACKGROUND);
        }else if(this.getColor() == Color.WHITE){
            System.out.print(UI.ANSI_WHITE + UI.ANSI_BLACK_BACKGROUND);
        }

        System.out.print("K" + UI.ANSI_RESET);
    }
}
