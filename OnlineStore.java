import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class OnlineStore { // class B

    private ArrayList<Product> productList = new ArrayList<>();
    Owner owner;

    public OnlineStore(Owner owner){
        this.owner = owner;
    }
    public  OnlineStore(Owner owner, ArrayList<Product> productList){
        this.owner = owner;
        this.productList = productList;
    }

    private void sortProducts(){ productList.sort(Comparator.naturalOrder()); }
    private void sortProductsPrice(){ productList.sort(Comparator.comparing(Product::getPrice)); }
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
                p.setPrice(product.getPrice());
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

    public Map<String, ArrayList<Product>> groupProductsByType(){
        Map<String, ArrayList<Product>> productMap = new HashMap<>();
        for(Product product : productList){
            if(productMap.containsKey(product.name)){
                productMap.get(product.name).add(product);
            }else{
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                productMap.put(product.name, products);
            }
        }
        return productMap;
    }

    public Map<Product, Integer> countProducts(ArrayList<Product> productList){
        Map<Product, Integer> productMap = new HashMap<>();
        for(Product product : productList){
            if(productMap.containsKey(product)){
                productMap.put(product, productMap.get(product) + 1);
            }else{
                productMap.put(product, 1);
            }
        }
        return productMap;
    }

    public Product[] getProductList(){ return productList.toArray(new Product[0]); }

}
