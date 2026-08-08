import java.util.*;
interface Accounts{
    void deposit();
    void withdraw();
    
}
class Savings implements Accounts{
    public void deposit(){
        System.out.println("money is deposited from savings");
    }
    public void withdraw(){
        System.out.println("money was withdrawn from savings");
    }
}

class Current implements Accounts{
    public void deposit(){
        System.out.println("money is deposited from current");
    }
    public void withdraw(){
        System.out.println("money was withdrawn from current");
    }

}

class FixedDeposit implements Accounts{
       public void deposit(){
        System.out.println("money is deposited from FD");
    } 
    public void withdraw(){
        throw new Exception("No withdraw found");
    }
}

class BankClient{
    private List<Accounts> accounts;

    public BankClient(List<Accounts> accounts) {
        this.accounts = accounts;
    }
    public void processWithdraw(){
        for(Accounts ac : accounts){
            ac.withdraw();

        }
    }


}

public class LSPVoilated {

    public static void main(String[] args) {
        List<Accounts> accounts = new ArrayList<>();
        accounts.add(new Savings());
        accounts.add(new Current());
        accounts.add(new FixedDeposit());
        BankClient client = new BankClient(accounts);
        client.processWithdraw(); //get exception FD withdraw
    }

}
