package miscForEverything;

public abstract class MenuTemplate {
    protected StringBuilder input;
    public MenuTemplate(){
        input = new StringBuilder();
    }

    public abstract boolean menu();
    public abstract MenuOptionTemplate read();
    public abstract void printOptions();
}
