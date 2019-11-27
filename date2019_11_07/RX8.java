package date2019_11_07;

public class RX8 extends Cars {

    public RX8(double maxVelocity){
        super(maxVelocity);
    }

    public void fahren() {
        System.out.println("Ich fahre " + this.getMaxVelocity() + "km/h");
    }

    public String toString(){
        return "Mazda RX-8";
    }
}
