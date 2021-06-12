package sample;

enum Payment {
    CREDIT_CARD,
    CASH
}

public class Order_info {
    private int id;
    private int customer_id;
    private float price;
    private Payment payment;

    public Order_info(int id, int customer_id, float price, Payment payment) {
        this.id = id;
        this.customer_id = customer_id;
        this.price = price;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public float getPrice() {
        return price;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
