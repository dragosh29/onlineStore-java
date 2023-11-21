import java.io.FileOutputStream;
import java.io.OutputStream;

public class OutputDevice {

    OutputStream consoleOutputStream;
    FileOutputStream fileOutputStream;

    OutputDevice(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
        this.consoleOutputStream = System.out;
    }
    OutputDevice() {
        this.consoleOutputStream = System.out;
    }

    public void writeMessageNl(Object message) {
        try
        {
            fileOutputStream.write(message.toString().getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void writeMessage(Object message) {
        try
        {
            fileOutputStream.write(message.toString().getBytes());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printMessage(Object message) { System.out.print(message); }
    public void printMessageNl(Object message) { System.out.println(message); }

    public void printStore(OnlineStore store){
        writeMessageNl(store.owner);
        if(store.getProductList().length == 0) writeMessageNl("Store is empty");
        int i = 1;
        for(Product product : store.getProductList()){
            writeMessageNl(i + ". " + product.toString());
            i++;
        }
    }

}
