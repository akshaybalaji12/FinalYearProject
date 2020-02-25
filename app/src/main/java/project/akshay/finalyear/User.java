package project.akshay.finalyear;

public class User {

    private String userName;
    private String userMobile;
    private String userEmail;
    private int userType;

    public User() {
    }

    public User(String userName, String userMobile, String userEmail, int userType) {
        this.userName = userName;
        this.userMobile = userMobile;
        this.userEmail = userEmail;
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
