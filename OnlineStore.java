import java.util.ArrayList;
import java.util.Arrays;

public class OnlineStore { // class B

    ArrayList<Product> productList = new ArrayList<>();
    Owner owner;

    OutputDevice outputDevice = new OutputDevice();
    public OnlineStore(Owner owner){
        this.owner = owner;
    }
    public  OnlineStore(Owner owner, Product[] productList){
        this.owner = owner;
        for(Product product:productList){
            this.addProduct(product);
        }
    }


    public void printProducts(){
        if(productList == null || productList.isEmpty()){
            outputDevice.writeMessageNl("Store content is:\nNo products");
            return;
        }
        outputDevice.writeMessageNl("Store content is:");
        for(Product product:productList){
            outputDevice.writeMessageNl(product);
        }
    }
    public void printStore(){
        outputDevice.writeMessageNl(owner);
        printProducts();
    }

    public void resetProducts(){
        productList.clear();
    }

    public void addProduct(Product product){
        for(Product p : productList){
            if(p.name.equals(product.name)){
                p.quantity += product.quantity;
                p.price = product.price;
                return;
            }
        }
        productList.add(product);
    }

    public void removeProduct(String name){
        for(Product p : productList){
            if(p.name.equals(name)){
                productList.remove(p);
                return;
            }
        }
    }

    public void removeSoldOutProducts(){
        for(Product p : productList){
            if(p.quantity == 0){
                removeProduct(p.name);
            }
        }
    }
}
