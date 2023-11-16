public class TopWear extends Product {

    public enum TopWearType{ TSHIRT, SHIRT, BLOUSE, SWEATER, HOODIE, CARDIGAN, JACKET, COAT }

    public TopWear(String name, String size, String color, int quantity, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
    }

    public TopWear(String name, String size, String color, int price){
        this.name = name;
        this.size = size;
        this.color = color;
        this.price = price;
        this.quantity = 0;
    }

}
