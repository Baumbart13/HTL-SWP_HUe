package date2020_01_09_Ticketshop;

public enum Priceclass {

    CHEAP(5.005, 10.004),
    MEDIUM(10.005,20.004),
    EXPENSIVE(20.005,40.004),
    VIP(50.005,100.004);

    private double min;
    private double max;

    Priceclass(double min, double max){
        this.min = min;
        this.max = max;
    }
}
