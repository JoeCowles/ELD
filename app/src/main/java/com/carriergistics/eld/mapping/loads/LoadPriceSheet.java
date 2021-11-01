package main.java.com.carriergistics.eld.mapping.loads;

public class LoadPriceSheet {
    
    int id;
    String type;
    boolean isSelected;
    double accessorialTotal;
    double subtotal;
    double total;
    double normalizedTotal;

    public LoadPriceSheet(int id, String type, boolean isSelected, double accessorialTotal, double subtotal, double total, double normalizedTotal) {
        this.id = id;
        this.type = type;
        this.isSelected = isSelected;
        this.accessorialTotal = accessorialTotal;
        this.subtotal = subtotal;
        this.total = total;
        this.normalizedTotal = normalizedTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        String str = "<id>%d</id> <type>%s</type>"
                + "<isSelected>%b</isSelected>"
                + "<accessorialTotal>%f</accessorialTotal>"
                + "<subtotal>%f</subtotal>"
                + "<total>%f</total>"
                + "<normalizedTotal>%f</normalizedTotal>"; 
        return String.format(str, id, type, isSelected, accessorialTotal, subtotal, total, normalizedTotal);
    }

}
