package date2019_11_07;

public class Skyline extends Cars {

    public Skyline(double maxVelocity){
        super(maxVelocity);
    }

    public void fahren() {
        System.out.println("Ich fahre " + this.getMaxVelocity() + "km/h");
    }

    public String toString(){
        return "Nissan date2019_11_07.Skyline";
    }
}
