package date2020_01_23;

public class Coordinate {
    public int X;
    public int Y;

    public Coordinate(){
        this(0,0);
    }

    public Coordinate(Coordinate coord){
        this.X = coord.X;
        this.Y = coord.Y;
    }

    public Coordinate(int x, int y){
        this.X = x;
        this.Y = y;
    }
}
