public class TopWear {


    String name, color, size;
    int quantity;

    enum TopWearType{ TSHIRT, SHIRT, BLOUSE, SWEATER, HOODIE, CARDIGAN, JACKET, COAT; }

    public TopWear(String name, String size, String color, int quantity){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    public TopWear(String name, String size, String color){
        this.name = name;
        this.size = size;
        this.color = color;
        this.quantity = 0;
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

    @Override
    public String toString(){
        return this.name + ":\n- size " + this.size + "\n- color " + this.color + "\n- quantity " + this.quantity + "\n";
    }
}
