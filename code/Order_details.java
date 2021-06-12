package sample;

public class Order_details {
    private int id;
    private int order_id;
    private int product_id;

    public Order_details(int id, int order_id, int product_id) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
