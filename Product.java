import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product implements ProductHandling, Comparable<Product>{

    protected String size, color, name;
    protected int quantity, price;

    public String getSize(){ return size; }
    public String getName(){ return name; }
    public String getColor(){ return color; }
    public int getQuantity(){ return quantity; }
    public int getPrice(){ return price; }
    public void setPrice(int price){ this.price = price; }
    public void increaseQuantity(int quantity){ this.quantity += quantity; }
    public void decreaseQuantity(int quantity){ this.quantity -= quantity; }

    @Override
    public int compareTo(Product o) {
        if(this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if(this.size.compareTo(o.size) != 0) return this.size.compareTo(o.size);
        if(this.color.compareTo(o.color) != 0) return this.color.compareTo(o.color);
        if (this.quantity != o.quantity) return this.quantity - o.quantity;
        if (this.price != o.price) return this.price - o.price;
        return 0;
    }

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
