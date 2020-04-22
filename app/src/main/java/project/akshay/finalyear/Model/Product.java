package project.akshay.finalyear.Model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Product {

    private String productID;
    private ArrayList<String> supplyChainArray;

    public Map<String, Boolean> stars = new HashMap<>();

    public Product() {
    }

    public Product(String productID) {
        this.productID = productID;
        this.supplyChainArray = new ArrayList<>();
    }

    public String getProductID() {
        return productID;
    }

    public ArrayList<String> getSupplyChainArray() {
        return supplyChainArray;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productID", productID);
        result.put("supplyChainArray", supplyChainArray);

        return result;
    }

}
