public class BottomWear extends Product {

    public enum BottomWearType{ JEANS, SHORTS, PANTS, SKIRT }

    public BottomWear(String name, String size, String color, int quantity, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = quantity;
    }

    public BottomWear(String name, String size, String color, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }


}
