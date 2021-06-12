package sample;

enum Size {
    XS,
    S,
    M,
    L,
    XL
}

public class Product {
    private int id;
    private float price;
    private String name;
    private Size size;

    public Product(int id, String name, float price, Size size) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public Size getSize() {
        return size;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
