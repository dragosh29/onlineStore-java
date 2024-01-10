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
    Scanner scanner, fileScanner;

    InputDevice(){
        this.consoleInputStream = System.in;
        this.scanner = new Scanner(System.in);
        this.fileScanner = null;
    }

    public void setFileInputStream(String fileName) {
        try {
            this.fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.fileScanner = new Scanner(fileInputStream);
    }

    public void closeFileInputStream() {
        if (this.fileInputStream == null) return;
        try {
            this.fileInputStream.close();
            this.fileScanner.close();
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
        if(!fileScanner.hasNextLine()) return null;
        String[] ownerInfo = new String[3];
        String[] line;
        for(int i = 0; i < 3; i++){
           line = fileScanner.nextLine().split(" ");
           ownerInfo[i] = line[line.length - 1];
        }
        Owner owner = new Owner(ownerInfo[0], ownerInfo[1], ownerInfo[2]);
        try{
            owner.checkAttributes();
        }
        catch(InvalidPersonAttribute e){
            switch (e.getMessage()){
                case "Invalid name" -> System.out.println("Name must be at least 3 characters long: " + owner.name);
                case "Invalid phone number" -> System.out.println("Phone number must contain at least 10 digits: " + owner.phoneNumber);
                case "Invalid email" -> System.out.println("Invalid email: " + owner.email);
            }
        }
        return owner;
    }

    public Owner readOwnerFromConsole(){
        System.out.println("Enter owner name: ");
        String name = scanner.nextLine();
        System.out.println("Enter owner phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter owner email: ");
        String email = scanner.nextLine();
        Owner owner = new Owner(name, phoneNumber, email);
        try{
            owner.checkAttributes();
        }
        catch(InvalidPersonAttribute e){
            switch (e.getMessage()){
                case "Invalid name" -> System.out.println("Name must be at least 3 characters long: " + owner.name);
                case "Invalid phone number" -> System.out.println("Phone number must contain at least 10 digits: " + owner.phoneNumber);
                case "Invalid email" -> System.out.println("Invalid email: " + owner.email);
            }
            readOwnerFromConsole();
        }
        return owner;
    }

    public ArrayList<Product> readProductsFromFile(){
        ArrayList<Product> products = new ArrayList<>();
        while(fileScanner.hasNextLine()){
            if(fileScanner.nextLine().equals("Store is empty")) return products;
            if(!fileScanner.hasNextLine()) break;
            String productName = fileScanner.nextLine().split(" ")[1].toUpperCase();
            productName = productName.substring(0, productName.length() - 1);

            String size = fileScanner.nextLine().split(" ")[2].toUpperCase();
            String color = fileScanner.nextLine().split(" ")[2].toUpperCase();
            int quantity = Integer.parseInt(fileScanner.nextLine().split(" ")[2]);
            int price = Integer.parseInt(fileScanner.nextLine().split(" ")[2]);

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

    public String getSizeFromConsole(){
        System.out.println("Enter size: ");
        String size = scanner.nextLine().toUpperCase();
        while(!Product.checkSize(size)){
            System.out.println("Invalid size. Enter size: ");
            size = scanner.nextLine().toUpperCase();
        }
        return size;
    }

    public String getColorFromConsole(){
        System.out.println("Enter color: ");
        String color = scanner.nextLine().toUpperCase();
        while(!Product.checkColor(color)){
            System.out.println("Invalid color. Enter color: ");
            color = scanner.nextLine().toUpperCase();
        }
        return color;
    }

    public int getPriceFromConsole(){
        int price = -1;
        while(price < 0){
            System.out.println("Enter price: ");
            try {
                price = Integer.parseInt(scanner.nextLine());
                if(!Product.checkPrice(price)){
                    System.out.println("Invalid price. Please enter a number greater than 0.");
                    price = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return price;
    }

    public int getQuantityFromConsole(){
        int quantity = -1;
        while(quantity < 0){
            System.out.println("Enter quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if(!Product.checkQuantity(quantity)){
                    System.out.println("Invalid quantity. Please enter a number greater than 0.");
                    quantity = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        return quantity;
    }

    public ArrayList<String> getNameAndTypeFromConsole(){
        String name = null, type = null;
        boolean done = false;
        while(!done){
            System.out.println("Enter name: ");
            name = scanner.nextLine().toUpperCase();
            try{
                type = Product.checkType(name);
                done = true;
            }catch(InvalidProductTypeException e){
                System.out.println("Invalid type: " + name);
                done = false;
            }
        }

        ArrayList<String> nameAndType = new ArrayList<>();
        nameAndType.add(name);
        nameAndType.add(type);
        return nameAndType;
    }

    public int getProductIndexFromConsole(OnlineStore store) {
        int index = -1;
        while (index < 0 || index >= store.getProductList().length) {
            System.out.println("Enter product index: ");
            String option = scanner.nextLine();
            if (!option.matches("[1-9][0-9]*")) {
                System.out.println("Invalid index. Please enter a number greater than 0.");
                continue;
            }
            index = Integer.parseInt(option) - 1;
            if (index < 0 || index >= store.getProductList().length) {
                System.out.println("Invalid index. Please enter a number between 1 and " + store.getProductList().length);
            }
        }
        return index;
    }
}
