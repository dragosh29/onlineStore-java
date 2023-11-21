public class Main {
    public static void main(String[] args) {
            OutputDevice outputDevice = new OutputDevice();
            InputDevice inputDevice = new InputDevice();


            if(args.length == 1 && args[0].equals("scratch")){
                outputDevice.printMessageNl("Creating store from scratch");
                args = new String[]{"scratch"};
            }
            else if(args.length == 0 ){
                outputDevice.printMessageNl("Starting store from file\nProvide file name:\n");
                String fileName = inputDevice.nextLine();

                inputDevice.setFileInputStream(fileName);
            }
            else{
                outputDevice.printMessageNl("Usage: java Main <scratch>");
                System.exit(1);
            }
            for(String arg:args) outputDevice.printMessage(arg + " ");
            outputDevice.writeMessageNl("\n");
            Application app = new Application(inputDevice, outputDevice);
            app.run(args);
        }
    }
