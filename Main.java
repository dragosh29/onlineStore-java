import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        OutputDevice outputDevice = new OutputDevice();
        InputDevice inputDevice = new InputDevice();


        if(args.length == 1 && args[0].equals("scratch")){
            outputDevice.printMessageNl("Creating store from scratch");
            try{
                Files.delete(Path.of("output.txt"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            File file = new File("output.txt");
            try{
                Files.createFile(Path.of("output.txt"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
            args = new String[]{"scratch"};
        }
        else if(args.length == 0 ){

            args = new String[]{"old"};
        }
        else{
            outputDevice.printMessageNl("Usage: java Main <scratch>");
            System.exit(1);
        }
        for(String arg:args) outputDevice.printMessage(arg + " ");
        outputDevice.printMessageNl("\n");
        Application app = new Application(inputDevice, outputDevice);
        app.run(args);
    }
}
