import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


// this class is used to test the functionality of the OnlineStore class and its methods
public class Application {
    InputDevice inputDevice;
    OutputDevice outputDevice;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    public void recreateFile(String fileName){

        outputDevice.closeFileOutputStream();
        inputDevice.closeFileInputStream();

        try{
            Files.deleteIfExists(Path.of(fileName));
        }
        catch(NoSuchFileException e){
            e.printStackTrace();
            System.out.println("The file to delete does not exist");
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error deleting file");
        }

        try{
            Files.createFile(Path.of(fileName));
            outputDevice.setFileOutputStream(fileName);
            inputDevice.setFileInputStream(fileName);
        }
        catch(FileAlreadyExistsException e){
            e.printStackTrace();
            System.out.println("The file to create already exists");
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Error creating file");
        }
    }

    private void testStore_1(){
        Owner owner = new Owner("John", "1234567890", "abc@gmail.com");
        OnlineStore store = new OnlineStore(owner);
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nAdding products:");
        ArrayList<Product> products = inputDevice.getRandomProducts();
        for(Product product:products){ outputDevice.printMessageNl(product);}
        store.setProductList(products);
        recreateFile("output.txt");
        outputDevice.storeToFile(store);
        outputDevice.printMessageNl("");
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nNow let's remove them one by one:\n");

        while(store.getProductList().length != 1){
            int randomIndex = inputDevice.nextInt(0, store.getProductList().length-1);
            outputDevice.printMessageNl("Removing product with index " + (randomIndex+1));
            store.removeProductIdx(randomIndex);
            outputDevice.printStore(store);
            outputDevice.printMessageNl("");
        }
        store.removeProductIdx(0);
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nNow let's test the removeProductType function: ");
        products = inputDevice.getRandomProducts();
        store.setProductList(products);
        outputDevice.printMessageNl("\nNew store content:");
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nRemoving all products with name TSHIRT:");
        store.removeProductType("TSHIRT");
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nRemoving all products with name JEANS:");
        store.removeProductType("JEANS");
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nRemoving all products with name SHIRT:");
        store.removeProductType("SHIRT");
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nRemoving all products with name SHORTS:");
        store.removeProductType("SHORTS");
        outputDevice.printStore(store);
    }

    private void testStore_2(){

        Owner owner = inputDevice.readOwnerFromFile();
        ArrayList<Product> products = inputDevice.readProductsFromFile();


        OnlineStore store = new OnlineStore(owner, products);
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nIncreasing quantity by 35 for all products:");
        for(Product p:products){
            p.increaseQuantity(35);
        }
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nDecreasing quantity by 50 for all products:");
        for(Product p:products){
            p.decreaseQuantity(50);
        }

        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nUse a function to remove all sold out products:");
        store.removeSoldOutProducts();
        outputDevice.printStore(store);

        recreateFile("output.txt");
        outputDevice.storeToFile(store);

        outputDevice.printMessageNl("\nUse a function to group products by type:");
        outputDevice.printMessageNl(store.groupProductsByType().toString());

        outputDevice.printMessageNl("\nUse a function to count products:");
        outputDevice.printMessageNl(store.countProducts(products).toString());
    }

    public void run(String[] args) {
        outputDevice.setFileOutputStream("output.txt");
        if(args.length != 1){
            outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
            System.exit(1);
        }
        else switch (args[0]) {
            case "scratch" -> testStore_1();

            case "old" -> {
                outputDevice.printMessageNl("Starting store from file\nProvide file name:\n");
                String fileName = inputDevice.nextLine();
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath) && Files.isReadable(filePath)) {
                    inputDevice.setFileInputStream(fileName);
                    testStore_2();
                } else {
                    outputDevice.printMessageNl("File does not exist or cannot be read. Please try again.");
                }
            }
            default -> {
                outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
                System.exit(1);
            }
        }

    }
}
