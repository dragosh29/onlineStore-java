import javax.xml.crypto.Data;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


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

    public void saveStoreToFile(OnlineStore store){
        recreateFile("output.txt");
        outputDevice.storeToFile(store);
    }

    private void runFromScratch(){
        outputDevice.printMessageNl("\nDo you want to generate a dummy owner? (y/n)");
        String input = inputDevice.nextLine();
        while(!input.equals("y") && !input.equals("n") && !input.equals("N") && !input.equals("Y")){
            outputDevice.printMessageNl("Please enter y or n");
            input = inputDevice.nextLine();
        }
        Owner owner = null;
        if(input.equals("Y") || input.equals("y")){
            owner = new Owner("John", "1234567890", "abc@gmail.com");
        }
        else{
            owner = inputDevice.readOwnerFromConsole();
        }
        OnlineStore store = new OnlineStore(owner);
        outputDevice.printMessageNl("\nDo you want to generate random products? (y/n)");
        input = inputDevice.nextLine();
        while(!input.equals("y") && !input.equals("n") && !input.equals("N") && !input.equals("Y")){
            outputDevice.printMessageNl("Please enter y or n");
            input = inputDevice.nextLine();
        }

        if(input.equals("Y") || input.equals("y")){
            outputDevice.printStore(store);
            outputDevice.printMessageNl("\nAdding products:");
            ArrayList<Product> products = inputDevice.getRandomProducts();
            for(Product product:products){ outputDevice.printMessageNl(product);}
            store.setProductList(products);
        }

        saveStoreToFile(store);
        startCommandLineInterface(store);
    }

    private void runFromFile(){

        Owner owner = inputDevice.readOwnerFromFile();
        ArrayList<Product> products = inputDevice.readProductsFromFile();
        OnlineStore store = new OnlineStore(owner, products);
        outputDevice.printStore(store);
        startCommandLineInterface(store);
    }

    private void startCommandLineInterface(OnlineStore store) {
        outputDevice.printMessageNl("\tSelect an option:\n");
        outputDevice.printMessageNl("1. Display store state\t\t\t2. Group products by type");
        outputDevice.printMessageNl("3. Count products\t\t\t\t4. Remove sold out products");
        outputDevice.printMessageNl("5. Remove product\t\t\t\t6. Remove product type");
        outputDevice.printMessageNl("7. Increase product quantity\t8. Decrease product quantity");
        outputDevice.printMessageNl("9. Add product\t\t\t\t\t10. Exit");

        String input = inputDevice.nextLine();
        int option;
        if (input.isEmpty() || !input.matches("^[1-9]$|^10$")) {
            outputDevice.printMessageNl("Please enter a number between 1 and 10");
            startCommandLineInterface(store);
        }
        option = Integer.parseInt(input);

        switch (option) {
            case 1 -> {
                outputDevice.printMessageNl("\nStore state:\n");
                outputDevice.printStore(store);
                startCommandLineInterface(store);
            }
            case 2 -> {
                outputDevice.printMessageNl("\nGrouped products:\n");
                Map<String, ArrayList<Product>> groupedProducts = store.groupProductsByType();
                for (Map.Entry<String, ArrayList<Product>> entry : groupedProducts.entrySet()) {
                    outputDevice.printMessageNl(entry.getKey() + ":");
                    for (Product product : entry.getValue()) {
                        outputDevice.printMessageNl(product);
                    }
                }
                startCommandLineInterface(store);
            }
            case 3 -> {
                outputDevice.printMessageNl("\nNumber of products:\n " + store.countProducts() + "\n");
                startCommandLineInterface(store);
            }
            case 4 -> {
                outputDevice.printMessageNl("\nRemoving sold out products:\n");
                store.removeSoldOutProducts();
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 5 -> {
                outputDevice.printMessageNl("\nRemoving product\n");
                int index = inputDevice.getProductIndexFromConsole(store);
                store.removeProductIdx(index);
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 6 -> {
                outputDevice.printMessageNl("\nRemoving product type:\n");
                outputDevice.printMessageNl("Enter product type: ");
                input = inputDevice.nextLine().toUpperCase();
                if (input.isEmpty()) {
                    outputDevice.printMessageNl("Please enter a product type");
                    startCommandLineInterface(store);
                }
                if(!store.getProductTypes().contains(input)){
                    outputDevice.printMessageNl("Product type does not exist");
                    startCommandLineInterface(store);
                }
                store.removeProductType(input);
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 7 -> {
                outputDevice.printMessageNl("\nIncreasing product quantity:\n");
                int index = inputDevice.getProductIndexFromConsole(store);
                int quantity = inputDevice.getQuantityFromConsole();
                store.increaseProductQuantity(index, quantity);
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 8 -> {
                outputDevice.printMessageNl("\nDecreasing product quantity:\n");
                int index = inputDevice.getProductIndexFromConsole(store);
                int quantity = inputDevice.getQuantityFromConsole();
                store.decreaseProductQuantity(index, quantity);
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 9 -> {
                outputDevice.printMessageNl("\nAdding product:\n");

                ArrayList<String> nameAndType = inputDevice.getNameAndTypeFromConsole();
                String name = nameAndType.get(0);
                String type = nameAndType.get(1);

                String size = inputDevice.getSizeFromConsole();
                String color = inputDevice.getColorFromConsole();
                int price = inputDevice.getPriceFromConsole();
                int quantity = inputDevice.getQuantityFromConsole();

                Product product;
                if(Objects.equals(type, "bottomWear")){
                    product = new BottomWear(name, size, color, quantity, price);
                }
                else{
                    product = new TopWear(name, size, color, quantity, price);
                }
                store.addProduct(product);
                saveStoreToFile(store);
                startCommandLineInterface(store);
            }
            case 10 -> {
                outputDevice.printMessageNl("Exiting...");
                System.exit(0);
            }
        }
    }

    public InputDevice getInputDevice() {
        return inputDevice;
    }

    public OutputDevice getOutputDevice() {
        return outputDevice;
    }

    private void runFromDB(){
        DatabaseInitializer.initializeDatabase();
        outputDevice.printMessageNl("Starting store from database");
        String url = DatabaseInitializer.getURL();
        DatabaseHandler dbHandler = new DatabaseHandler(url);
        OnlineStore store = dbHandler.retrieveOnlineStoreFromDB();
        store.setDatabaseHandler(dbHandler);
        if (store.owner == null) {
            outputDevice.printMessageNl("No owner found in database");
            outputDevice.printMessageNl("Please enter owner details:");
            Owner owner = inputDevice.readOwnerFromConsole();
            store.owner = owner;
            dbHandler.insertOwner(owner);
        }
        startCommandLineInterface(store);
    }
    public void run(String[] args) {
        outputDevice.setFileOutputStream("output.txt");
        if(args.length != 1){
            outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
            System.exit(1);
        }
        else switch (args[0]) {
            case "scratch" -> runFromScratch();

            case "old" -> {
                outputDevice.printMessageNl("Starting store from file\nProvide file name:\n");
                String fileName = inputDevice.nextLine();
                Path filePath = Paths.get(fileName);
                if (Files.exists(filePath) && Files.isReadable(filePath)) {
                    inputDevice.setFileInputStream(fileName);
                    runFromFile();
                } else {
                    outputDevice.printMessageNl("File does not exist or cannot be read. Please try again.");
                    System.exit(1);
                }
            }

            case "db" -> runFromDB();

            default -> {
                outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
                System.exit(1);
            }
        }

    }
}
