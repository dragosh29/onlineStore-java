public class Main {
    public static void main(String[] args) {
            OutputDevice outputDevice = new OutputDevice();
            if(args.length > 2 || args.length < 1){
                outputDevice.writeMessageNl("Usage: java Main [words/numbers/store_1/store_2/game] [no. of game rounds]");
                System.exit(1);
            }
            InputDevice inputDevice = new InputDevice();
            for(String arg:args) outputDevice.writeMessage(arg + " ");
            outputDevice.writeMessageNl("\n");
            Application app = new Application(inputDevice, outputDevice);
            app.run(args);
        }
    }
