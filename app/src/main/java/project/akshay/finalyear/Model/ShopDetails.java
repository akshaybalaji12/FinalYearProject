package project.akshay.finalyear.Model;

public class ShopDetails {

    private String id;
    private String name;
    private String receivedDate;

    public ShopDetails(String id, String name, String receivedDate) {
        this.name = name;
        this.id = id;
        this.receivedDate = receivedDate;
    }

    public ShopDetails() {
    }

    public String getName() {
        return name;
    }

    public String getReceivedDate() {
        return receivedDate;
    }
}
