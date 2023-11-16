public class Product implements ProductHandling{

    protected String size, color, name;
    protected int quantity, price;

    public String getSize(){ return size; }
    public String getName(){ return name; }
    public String getColor(){ return color; }
    public int getQuantity(){ return quantity; }
    public void increaseQuantity(int quantity){ this.quantity += quantity; }
    public void decreaseQuantity(int quantity){ this.quantity -= quantity; }

    @Override
    public String toString(){
        return this.name + ":\n- size " + this.size + "\n- color " + this.color + "\n- quantity " + this.quantity + "\n" + "- price " + this.price + "\n";
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Product product){
            return this.name.equals(product.name) && this.size.equals(product.size) && this.color.equals(product.color);
        }
        return false;
    }
}
