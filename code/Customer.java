package sample;

public class Customer {
    private int id;
    private String first_name;
    private String last_name;
    private int discount_card;
    private int mobile_phone;
    private String email;

    public Customer (int id, String first_name, String last_name, int discount_card, int mobile_phone, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.discount_card = discount_card;
        this.mobile_phone = mobile_phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getDiscount_card() {
        return discount_card;
    }

    public int getMobile_phone() {
        return mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setDiscount_card(int discount_card) {
        this.discount_card = discount_card;
    }

    public void setMobile_phone(int mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
