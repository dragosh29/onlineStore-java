import java.util.ArrayList;

public class Owner implements Person{ //class A

    String name, phoneNumber, email;
    ArrayList<String> info = new ArrayList<>();

    public Owner(String name, String phoneNumber, String email){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        info.add(name);
        info.add(phoneNumber);
        info.add(email);
    }

    @Override
    public String toString(){
        return "Store owner is " + this.name + "\nOwner phone number is " + this.phoneNumber + "\nOwner email is " + this.email + "\n";
    }

    public ArrayList<String> getInfo(){ return info; }

}

