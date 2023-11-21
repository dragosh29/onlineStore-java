import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.scanner = new Scanner(fileInputStream);
    }
    public int nextInt(int minBound, int maxBound) { return ThreadLocalRandom.current().nextInt(minBound, maxBound); }

    private BottomWear[] getRandomBottomWear(){
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
        String[] colors = {"red", "blue", "green", "yellow", "black", "white"};
        BottomWear.BottomWearType[] names = BottomWear.BottomWearType.values();
        BottomWear[] products = new BottomWear[5];
        for(int i = 0; i < products.length; i++){
            products[i] = new BottomWear(names[nextInt(0, names.length)].toString(), sizes[nextInt(0, sizes.length)], colors[nextInt(0, colors.length)], nextInt(0, 101), nextInt(0, 501));
        }
        return products;
    }

    private TopWear[] getRandomTopWear(){
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
        String[] colors = {"red", "blue", "green", "yellow", "black", "white"};
        TopWear.TopWearType[] names = TopWear.TopWearType.values();
        TopWear[] products = new TopWear[5];
        for(int i = 0; i < products.length; i++){
            products[i] = new TopWear(names[nextInt(0, names.length)].toString(), sizes[nextInt(0, sizes.length)], colors[nextInt(0, colors.length)], nextInt(0, 101), nextInt(0, 501));
        }
        return products;
    }

    public Product[] getRandomProducts(){
        Product[] products = new Product[10];
        BottomWear[] bottomWears = getRandomBottomWear();
        TopWear[] topWears = getRandomTopWear();
        for(int i = 0; i < products.length; i++){
            if(i < 5) products[i] = bottomWears[i];
            else products[i] = topWears[i - 5];
        }
        return products;
    }

    public String nextLine() { return scanner.nextLine(); }

    public Owner readOwnerFromFile(){
        String[] ownerInfo = new String[3];
        String[] line;
        for(int i = 0; i < 3; i++){
           line = scanner.nextLine().split(" ");
              ownerInfo[i] = line[line.length - 1];
        }
        return new Owner(ownerInfo[0], ownerInfo[1], ownerInfo[2]);
    }

    public ArrayList<Product> readProductsFromFile(){
        ArrayList<Product> products = new ArrayList<>();
        while(scanner.hasNextLine()){
            String[] productInfo = scanner.nextLine().split(" ");
            boolean isBottomWear = false;
            for (BottomWear.BottomWearType bottomWearType : BottomWear.BottomWearType.values()) {
                if (bottomWearType.toString().equals(productInfo[0])) {
                    isBottomWear = true;
                    break;
                }
            }
            if(isBottomWear){
                products.add(new BottomWear(productInfo[0], productInfo[1], productInfo[2], Integer.parseInt(productInfo[3]), Integer.parseInt(productInfo[4])));
            }
            else {
                products.add(new TopWear(productInfo[0], productInfo[1], productInfo[2], Integer.parseInt(productInfo[3]), Integer.parseInt(productInfo[4])));
            }
        }
        return products;
    }

}
