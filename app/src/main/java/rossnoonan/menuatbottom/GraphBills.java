package rossnoonan.menuatbottom;

public class GraphBills {
    private String titlebillg, price, date;

    public GraphBills() {
    }

    public GraphBills(String titlebillg, String price, String date) {
        this.titlebillg = titlebillg;
        this.price = price;
        this.date = date;
    }

    public String getTitlebillg() {
        return titlebillg;
    }

    public void setTitlebillg(String titlebillg) {
        this.titlebillg = titlebillg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
