import java.util.ArrayList;

public class Client implements Person{

    String name, phoneNumber, email;
    ArrayList<String> info = new ArrayList<>();

    public Client(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        info.add(name);
        info.add(phoneNumber);
        info.add(email);
    }

    @Override
    public String toString(){
        return "Client name is " + this.name + "\nClient phone number is " + this.phoneNumber + "\nClient email is " + this.email + "\n";
    }

    public ArrayList<String> getInfo(){ return info; }

}
