import java.util.Arrays;

public class Application {
    InputDevice inputDevice;
    OutputDevice outputDevice;

    public Application(InputDevice inputDevice, OutputDevice outputDevice) {
        this.inputDevice = inputDevice;
        this.outputDevice = outputDevice;
    }

    private void sortNumbers(int[] array){
        Arrays.sort(array);
    }

    private void randomArraySort(){
        int[] array = inputDevice.getNumber();
        outputDevice.writeMessageNl("Unsorted array: ");
        for(int i = 0; i < 100; i++){
            outputDevice.writeMessage(array[i] + " ");
        }
        outputDevice.writeMessageNl("");
        sortNumbers(array);
        outputDevice.writeMessageNl("Sorted array: ");
        for(int i = 0; i < 100; i++){
            outputDevice.writeMessage(array[i] + " ");
        }
    }

    private int[] wordSizeHistogram(String line){
        int[] histogram = new int[46];
        String[] words = line.split(" ");
        for(String word : words){
            word = word.replaceAll("[^a-zA-Z ]", "");
            int wordLength = word.length();
            if(wordLength <= 45)
                histogram[wordLength]++;
            else
                outputDevice.writeMessageNl("Word too long: " + word);
            
        }
        return histogram;
    }
    private boolean checkException(int p1_guess, int p2_guess){
        if(p1_guess >= 10 && p1_guess < p2_guess){
            int multiple = p1_guess;
            while (multiple < p2_guess){
                if(p2_guess % multiple == 0){
                    return true;
                }
                multiple += p1_guess;
            }
        }
        if(p2_guess >= 10 && p2_guess < p1_guess){
            int multiple = p2_guess;
            while (multiple < p1_guess){
                if(p1_guess % multiple == 0){
                    return true;
                }
                multiple += p2_guess;
            }
        }
        return false;
    }

    private void exampleHistogram(){
        String line = inputDevice.getLine();
        int[] histogram = wordSizeHistogram(line);
        for(int i = 0; i < 46; i++){
            if(histogram[i] > 0)
                outputDevice.writeMessageNl(i + ": " + histogram[i]);
        }
    }
    private void playGame(int rounds){
        int p1_points = 0;
        int p2_points = 0;
        while(p1_points < rounds && p2_points < rounds){
            int p2_guess = inputDevice.nextInt();
            int p1_guess = inputDevice.nextInt();
            outputDevice.writeMessageNl("Player 1 guess: " + p1_guess);
            outputDevice.writeMessageNl("Player 2 guess: " + p2_guess);
            if(p1_guess == p2_guess){
                outputDevice.writeMessageNl("Tie!");
                p1_points+=2;
                p2_points+=2;
            }
            else if(p1_guess > p2_guess){
                if(checkException(p1_guess, p2_guess)){
                    outputDevice.writeMessageNl("Player 2 wins!");
                    p2_points++;
                }
                else{
                    outputDevice.writeMessageNl("Player 1 wins!");
                    p1_points++;
                }
            }
            else if(p2_guess > p1_guess){
                if(checkException(p2_guess, p1_guess)){
                    outputDevice.writeMessageNl("Player 1 wins!");
                    p1_points++;
                }
                else{
                    outputDevice.writeMessageNl("Player 2 wins!");
                    p2_points++;
                }
            }
            outputDevice.writeMessageNl("Player 1 points: " + p1_points);
            outputDevice.writeMessageNl("Player 2 points: " + p2_points + "\n");

            if(p1_points >= rounds){
                outputDevice.writeMessageNl("Player 1 wins the game!");
            }
            else if(p2_points >= rounds){
                outputDevice.writeMessageNl("Player 2 wins the game!");
            }
        }

    }

    private void testStore_1(){
        Owner owner = new Owner("John");
        OnlineStore store = new OnlineStore(owner);
        store.printStore();
        outputDevice.writeMessageNl("\nAdding products:");
        InputDevice inputDevice = new InputDevice();
        OutputDevice outputDevice = new OutputDevice();
        Product[] products = new Product[6];
        for(int i = 0; i < 5; i++){
            products[i] = inputDevice.generateRandomProduct();
            store.addProduct(products[i]);
            outputDevice.writeMessageNl("Adding " + products[i]);
        }
        outputDevice.writeMessageNl("");
        store.printProducts();
        outputDevice.writeMessageNl("\n" + store.productList.size() + " products added. Now let's remove them one by one:\n");

        while(!store.productList.isEmpty()){
            int randomIndex = inputDevice.nextInt(0, store.productList.size()-1);
            outputDevice.writeMessageNl("Removing " + store.productList.get(randomIndex).name);
            store.removeProduct(store.productList.get(randomIndex).name);
            store.printProducts();
            outputDevice.writeMessageNl("");
        }
    }

    private void testStore_2(){
        Owner owner = new Owner("Peter");
        Product[] products = new Product[5];
        products[0] = new Product("Apple", 10, 5);
        products[1] = new Product("Banana", 20, 10);
        products[2] = new Product("Orange", 30, 15);
        products[3] = new Product("Apple", 30, 100);
        products[4] = new Product("Pineapple", 40, 20);
        outputDevice.writeMessageNl("Creating store with the following products:");
        for(Product p:products){
            outputDevice.writeMessageNl(p);
        }
        OnlineStore store = new OnlineStore(owner, products);
        outputDevice.writeMessageNl("");
        store.printStore();
        outputDevice.writeMessageNl("\nIncreasing quantity by 35 for all products:");
        for(Product p:products){
            p.increaseQuantity(35);
        }
        store.printStore();
        outputDevice.writeMessageNl("\nDecreasing quantity by 50 for all products:");
        for(Product p:products){
            p.decreaseQuantity(50);
        }
        store.printStore();
        outputDevice.writeMessageNl("\nUse a function to remove all sold out products:");
        store.removeSoldOutProducts();
        store.printStore();
        outputDevice.writeMessageNl("\nResetting all store content:");
        store.resetProducts();
        store.printStore();
    }
    public void run(String[] args) {
        if(args.length == 2){
            int rounds = Integer.valueOf(args[1]);
            playGame(rounds);
        }
        else switch (args[0]) {
            case "words":
                exampleHistogram();
                break;
            case "numbers":
                randomArraySort();
                break;
            case "game":
                playGame(5);
                break;
            case "store_1":
                testStore_1();
                break;
            case "store_2":
                testStore_2();
                break;
            default:
                System.exit(1);
        }

    }
}
