import java.util.ArrayList;

public class Client implements Person, Comparable<Client>{

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
    public int compareTo(Client o) {
        if(this.name.compareTo(o.name) != 0) return this.name.compareTo(o.name);
        if(this.phoneNumber.compareTo(o.phoneNumber) != 0) return this.phoneNumber.compareTo(o.phoneNumber);
        if(this.email.compareTo(o.email) != 0) return this.email.compareTo(o.email);
        return 0;
    }

    @Override
    public String toString(){
        return "Client name is " + this.name + "\nClient phone number is " + this.phoneNumber + "\nClient email is " + this.email + "\n";
    }

    public ArrayList<String> getInfo(){ return info; }

}
