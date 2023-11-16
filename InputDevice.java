import java.util.concurrent.ThreadLocalRandom;

public class InputDevice {

    public int nextInt() { return ThreadLocalRandom.current().nextInt(1, 101); }
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
}
