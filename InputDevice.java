import java.util.concurrent.ThreadLocalRandom;

public class InputDevice {
    public String getType() {
        return "random";
    }

    public int nextInt() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
    public int nextInt(int minBound, int maxBound) {
        return ThreadLocalRandom.current().nextInt(minBound, maxBound+1);
    }

    String getLine(){
        String line = "The quick brown fox jumps over the lazy dog.";
        return line;
    }
    public int[] getNumber() {
        int[] arr = new int[100];
        for(int i = 0; i < 100; i++){
            arr[i] = nextInt();
        }
        return arr;
    }

    public BottomWear getRandomBottomWear(){
        String[] sizes = {"XS", "S", "M", "L", "XL", "XXL"};
        String[] colors = {"red", "blue", "green", "yellow", "black", "white"};

        BottomWear[] products = new BottomWear[10];
        for(int i = 0; i < 10; i++){
        }
    }

}
