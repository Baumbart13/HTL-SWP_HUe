package date2019_11_07;

public abstract class Cars {
    private double maxVelocity;

    public Cars(double maxVelocity){
        this.maxVelocity = maxVelocity;
    }

    public double getMaxVelocity(){
        return this.maxVelocity;
    }

    public abstract void fahren();

    public abstract String toString();
}
