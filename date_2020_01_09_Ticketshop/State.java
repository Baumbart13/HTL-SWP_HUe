package date_2020_01_09_Ticketshop;

public enum State {
    W,
    N,
    O,
    Sa,
    T,
    V,
    B,
    St,
    K;

    public String toString() {
        switch(this.name().toUpperCase()){
            case "W":
                return "Vienna";
            case "N":
                return "Lower Austria";
            case "O":
                return "Upper Austria";
            case "SA":
                return "Salzburg";
            case "T":
                return "Tyrol";
            case "V":
                return "Vorarlberg";
            case "B":
                return "Burgenland";
            case "ST":
                return "Styria";
            case "K":
                return "Carinthia";
            default:
                return "You geographical feral pig!! This doesn't exists";
        }
    }
}
