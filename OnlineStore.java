import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/// OnlineStore class that represents the store. It has a list of products and an owner. It has methods to add, remove, and sort products.
/// It also has methods to group products by type and count products.
public class OnlineStore { // class B

    private ArrayList<Product> productList = new ArrayList<>();
    private ArrayList<Client> clientList = new ArrayList<>(); //not used yet

    private DatabaseHandler dbHandler;

    public void setDatabaseHandler(DatabaseHandler dbHandler){
        this.dbHandler = dbHandler;
    }
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
    public void resetProducts(){
        productList.clear();
        dbHandler.deleteAllProducts();
    }
    public void removeProductIdx(int idx){
        Product aux = productList.get(idx);
        productList.remove(idx);
        dbHandler.deleteProduct(aux);
    }
    public void removeProductType(String name){
        if(productList.isEmpty()) return;
        for(Product p : productList){
            if(p.getName().equals(name)){
                productList.remove(p);
                dbHandler.deleteProduct(p);
                return;
            }
        }
    }
    public void removeSoldOutProducts(){
        for(Product p : productList){
            if(p.getQuantity() == 0){
                productList.remove(p);
                dbHandler.deleteProduct(p);
                return;
            }
        }
    }

    public void addProduct(Product product){
        try{
            product.checkAttributes();
        }catch(InvalidProductAttribute e){
            switch (e.getMessage()){
                case "Invalid size" -> System.out.println("Invalid size: " + product.getSize());
                case "Invalid color" -> System.out.println("Invalid color: " + product.getColor());
                case "Invalid price" -> System.out.println("Invalid price: " + product.getPrice());
                case "Invalid quantity" -> System.out.println("Invalid quantity: " + product.getQuantity());
            }
        }

       try{
           Product.checkType(product.getName());
       }catch (InvalidProductTypeException e){
           System.out.println("Invalid product type: " + product.getName());
         }

        for(Product p : productList){
            if(p.equals(product)){
                p.increaseQuantity(product.getQuantity());
                p.setPrice(product.getPrice());
                return;
            }
        }
        productList.add(product);
        dbHandler.insertProduct(product);
        sortProducts();
    }

    public void addClient(Client client){
        try{
            client.checkAttributes();
        }catch(InvalidPersonAttribute e){
            switch (e.getMessage()){
                case "Invalid name" -> System.out.println("Invalid name: " + client.name);
                case "Invalid phone number" -> System.out.println("Invalid phone number: " + client.phoneNumber);
                case "Invalid email" -> System.out.println("Invalid email: " + client.email);
            }
        }
        clientList.add(client);
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

    public Map<String, Integer> countProducts(){
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

    public ArrayList<String> getProductTypes(){
        ArrayList<String> productTypes = new ArrayList<>();
        for(Product product : productList){
            if(!productTypes.contains(product.getName())){
                productTypes.add(product.getName());
            }
        }
        return productTypes;
    }

    public void increaseProductQuantity(int index, int quantity){
        productList.get(index).increaseQuantity(quantity);
        dbHandler.updateProductQuantity(productList.get(index));
    }

    public void decreaseProductQuantity(int index, int quantity){
        productList.get(index).decreaseQuantity(quantity);
        dbHandler.updateProductQuantity(productList.get(index));
    }

    public Product[] getProductList(){ return productList.toArray(new Product[0]); }

}
