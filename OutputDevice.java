public class OutputDevice {
    public void writeMessageNl(Object message) {
        System.out.println(message);
    }
    public void writeMessage(Object message){ System.out.print(message); }

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
