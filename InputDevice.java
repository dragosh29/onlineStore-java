import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;


/// InputDevice class is used to read input from the user and from the file and to generate random products
public class InputDevice {


    InputStream consoleInputStream;
    FileInputStream fileInputStream;
    Scanner scanner;

    InputDevice(){
        this.consoleInputStream = System.in;
        this.scanner = new Scanner(System.in);
    }

    public void setFileInputStream(String fileName) {
        try {
            this.fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.scanner = new Scanner(fileInputStream);
    }

    public void closeFileInputStream() {
        if (this.fileInputStream == null) return;
        try {
            this.fileInputStream.close();
            this.scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int nextInt(int minBound, int maxBound) { return ThreadLocalRandom.current().nextInt(minBound, maxBound); }

    private BottomWear[] getRandomBottomWear(){
        Product.sizes[] sizes = Product.sizes.values();
        Product.colors[] colors = Product.colors.values();
        BottomWear.BottomWearType[] names = BottomWear.BottomWearType.values();
        BottomWear[] products = new BottomWear[5];
        for(int i = 0; i < products.length; i++){
            products[i] = new BottomWear(names[nextInt(0, names.length)].toString(), sizes[nextInt(0, sizes.length)].toString(),
                    colors[nextInt(0, colors.length)].toString(), nextInt(0, 101), nextInt(0, 501));
        }
        return products;
    }

    private TopWear[] getRandomTopWear(){
        Product.sizes[] sizes = Product.sizes.values();
        Product.colors[] colors = Product.colors.values();
        TopWear.TopWearType[] names = TopWear.TopWearType.values();
        TopWear[] products = new TopWear[5];
        for(int i = 0; i < products.length; i++){
            products[i] = new TopWear(names[nextInt(0, names.length)].toString(), sizes[nextInt(0, sizes.length)].toString(),
                    colors[nextInt(0, colors.length)].toString(), nextInt(0, 101), nextInt(0, 501));
        }
        return products;
    }

    public ArrayList<Product> getRandomProducts(){
        ArrayList<Product> products = new ArrayList<>();
        BottomWear[] bottomWears = getRandomBottomWear();
        TopWear[] topWears = getRandomTopWear();
        for(int i = 0; i < bottomWears.length + topWears.length; i++){
            if(i < (bottomWears.length + topWears.length)/2) products.add(bottomWears[i]);
            else products.add(topWears[i - 5]);
        }
        return products;
    }

    public String nextLine() { return scanner.nextLine(); }

    public Owner readOwnerFromFile(){
        if(!scanner.hasNextLine()) return null;
        String[] ownerInfo = new String[3];
        String[] line;
        for(int i = 0; i < 3; i++){
           line = scanner.nextLine().split(" ");
           ownerInfo[i] = line[line.length - 1];
        }
        Owner owner = new Owner(ownerInfo[0], ownerInfo[1], ownerInfo[2]);
        try{
            owner.checkAttributes();
        }
        catch(InvalidPersonAttribute e){
            switch (e.getMessage()){
                case "Invalid name" -> System.out.println("Name must be at least 3 characters long: " + owner.name);
                case "Invalid phone number" -> System.out.println("Phone number must contain only digits: " + owner.phoneNumber);
                case "Invalid email" -> System.out.println("Invalid email: " + owner.email);
            }
        }
        return owner;
    }

    public ArrayList<Product> readProductsFromFile(){
        ArrayList<Product> products = new ArrayList<>();
        while(scanner.hasNextLine()){
            if(scanner.nextLine().equals("Store is empty")) return products;
            if(!scanner.hasNextLine()) break;
            String productName = scanner.nextLine().split(" ")[1].toUpperCase();
            productName = productName.substring(0, productName.length() - 1);

            String size = scanner.nextLine().split(" ")[2].toUpperCase();
            String color = scanner.nextLine().split(" ")[2].toUpperCase();
            int quantity = Integer.parseInt(scanner.nextLine().split(" ")[2]);
            int price = Integer.parseInt(scanner.nextLine().split(" ")[2]);

            boolean isBottomWear = false;
            boolean isTopWear = false;

            for (BottomWear.BottomWearType bottomWearType : BottomWear.BottomWearType.values()) {
                if (bottomWearType.toString().equals(productName)) {
                    isBottomWear = true;
                    break;
                }
            }
            for(TopWear.TopWearType topWearType : TopWear.TopWearType.values()){
                if(topWearType.toString().equals(productName)){
                    isTopWear = true;
                    break;
                }
            }

            if(isBottomWear){
                products.add(new BottomWear(productName, size, color, quantity, price));
            }
            if(isTopWear) {
                products.add(new TopWear(productName, size, color, quantity, price));
            }
        }
        return products;
    }

}
