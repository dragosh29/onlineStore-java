import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/// OnlineStore class that represents the store. It has a list of products and an owner. It has methods to add, remove, and sort products.
/// It also has methods to group products by type and count products.
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
        try{
            product.checkAttributes();
        }catch(InvalidProductAttribute e){
            switch (e.getMessage()){
                case "Invalid size" -> System.out.println("Invalid size: " + product.getSize());
                case "Invalid color" -> System.out.println("Invalid color: " + product.getColor());
                case "Invalid price" -> System.out.println("Invalid price: " + product.getPrice());
            }
        }

        if(product instanceof TopWear topWear){
            try{
                product.checkType();
            }catch(InvalidProductTypeException e){
                System.out.println("Invalid type: " + topWear.getName());
            }
        }

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

    public void setProductList(ArrayList<Product> productList){
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

    public Map<String, Integer> countProducts(ArrayList<Product> productList){
        Map<String, Integer> productMap = new HashMap<>();
        for(Product product : productList){
            if(productMap.containsKey(product.getName())){
                productMap.put(product.getName(), productMap.get(product.getName()) + 1);
            }else{
                productMap.put(product.getName(), 1);
            }
        }
        return productMap;
    }

    public Product[] getProductList(){ return productList.toArray(new Product[0]); }

}
