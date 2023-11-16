import java.util.ArrayList;
import java.util.Comparator;

public class OnlineStore { // class B

    private ArrayList<Product> productList = new ArrayList<>();
    Owner owner;

    public OnlineStore(Owner owner){
        this.owner = owner;
    }
    public  OnlineStore(Owner owner, Product[] productList){
        this.owner = owner;
        for(Product product:productList){
            this.addProduct(product);
        }
    }

    private void sortProducts(){ productList.sort(Comparator.comparing(p -> p.name)); }
    public void resetProducts(){ productList.clear(); }
    public void removeProductIdx(int idx){ productList.remove(idx); }
    public void removeProductType(String name){
        if(productList.isEmpty()) return;
        productList.removeIf(p -> p.name.equals(name));
    }
    public void removeSoldOutProducts(){ productList.removeIf(p -> p.getQuantity() == 0); }

    public void addProduct(Product product){
        for(Product p : productList){
            if(p.equals(product)){
                p.increaseQuantity(product.getQuantity());
                return;
            }
        }
        productList.add(product);
        sortProducts();
    }

    public void setProductList(Product[] productList){
        resetProducts();
        for(Product product:productList){
            this.addProduct(product);
        }
    }

    public Product[] getProductList(){ return productList.toArray(new Product[0]); }

}
