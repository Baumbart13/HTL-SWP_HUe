package date2020_01_23.Units;

import date2020_01_23.Color;
import date2020_01_23.Coordinate;

public abstract class Figure {

    protected Coordinate coord;
    private boolean alive = true;
    private Color color;

    public Figure(Coordinate coord, Color color){
        this.coord = coord;
        this.color = color;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public Color getColor(){
        return this.color;
    }

    public abstract void moveTo(Coordinate coord);
    public abstract void print();
}
