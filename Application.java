import java.util.Arrays;

public class Application {
    InputDevice inputDevice;
    OutputDevice outputDevice;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }


    private void testStore_1(){
        Owner owner = new Owner("John", "1234567890", "abc@gmail.com");
        OnlineStore store = new OnlineStore(owner);
        outputDevice.printStore(store);
        outputDevice.printMessageNl("\nAdding products:");
        Product[] products = inputDevice.getRandomProducts();
        for(Product product:products){ outputDevice.printMessageNl(product);}
        store.setProductList(products);
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

//    private void testStore_2(){
//
//        Product[] products = new Product[5];
//        products[0] = new Product("Apple", 10, 5);
//        products[1] = new Product("Banana", 20, 10);
//        products[2] = new Product("Orange", 30, 15);
//        products[3] = new Product("Apple", 30, 100);
//        products[4] = new Product("Pineapple", 40, 20);
//        outputDevice.writeMessageNl("Creating store with the following products:");
//        for(Product p:products){
//            outputDevice.writeMessageNl(p);
//        }
//        OnlineStore store = new OnlineStore(owner, products);
//        outputDevice.writeMessageNl("");
//        store.printStore();
//        outputDevice.writeMessageNl("\nIncreasing quantity by 35 for all products:");
//        for(Product p:products){
//            p.increaseQuantity(35);
//        }
//        store.printStore();
//        outputDevice.writeMessageNl("\nDecreasing quantity by 50 for all products:");
//        for(Product p:products){
//            p.decreaseQuantity(50);
//        }
//        store.printStore();
//        outputDevice.writeMessageNl("\nUse a function to remove all sold out products:");
//        store.removeSoldOutProducts();
//        store.printStore();
//        outputDevice.writeMessageNl("\nResetting all store content:");
//        store.resetProducts();
//        store.printStore();
//    }

    public void run(String[] args) {
        outputDevice.setFileOutputStream("output.txt");
        if(args.length != 1){
            outputDevice.printMessageNl("Usage: java Application <store_1|store_2>");
            System.exit(1);
        }
        else switch (args[0]) {
            case "scratch" -> {
                testStore_1();
            }

           case "old" -> {
               outputDevice.printMessageNl("Starting store from file\nProvide file name:\n");
               String fileName = inputDevice.nextLine();
               inputDevice.setFileInputStream(fileName);
//               testStore_2();
           }
            default -> System.exit(1);
        }

    }
}
