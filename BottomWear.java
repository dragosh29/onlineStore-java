public class BottomWear implements Product {


    enum BottomWearType{ JEANS, SHORTS, PANTS, SKIRT; }
    String size, color, name;
    int quantity, price;

    public BottomWear(String name, String size, String color, int quantity){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public BottomWear(String name, String size, String color){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = 0;
    }

    @Override
    public String toString(){
        return this.name + ":\n- size " + this.size + "\n- color " + this.color + "\n- quantity " + this.quantity + "\n";
    }

    public String getSize(){
        return size;
    }

    public String getName(){
        return name;
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    public void decreaseQuantity(int quantity){
        this.quantity -= quantity;
    }

    public int getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }

}
