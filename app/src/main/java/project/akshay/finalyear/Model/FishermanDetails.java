package project.akshay.finalyear.Model;

public class FishermanDetails {

    private String id;
    private String name;
    private String fishType;
    private String date;
    private String location;

    public FishermanDetails(String id, String name, String fishType, String date, String location) {
        this.id = id;
        this.name = name;
        this.fishType = fishType;
        this.date = date;
        this.location = location;
    }

    public FishermanDetails() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFishType() {
        return fishType;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
