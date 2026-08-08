import java.util.*;
interface NonWithDrawableAccounts{
    void deposit();
}
interface WithDrawableAccounts extends NonWithDrawableAccounts{
    void withdraw();
    
}
class Savings implements WithDrawableAccounts{
    public void deposit(){
        System.out.println("money is deposited from savings");
    }
    public void withdraw(){
        System.out.println("money was withdrawn from savings");
    }
}

class Current implements WithDrawableAccounts{
    public void deposit(){
        System.out.println("money is deposited from current");
    }
    public void withdraw(){
        System.out.println("money was withdrawn from current");
    }

}

class FixedDeposit implements NonWithDrawableAccounts{
       public void deposit(){
        System.out.println("money is deposited from FD");
    } 
    public void withdraw(){
        throw new Exception("No withdraw found");
    }
}
class BankClient{
    private List<NonWithDrawableAccounts> depAccounts;
    private List<WithDrawableAccounts> defaultAccounts;

    public BankClient(List<NonWithDrawableAccounts> depAccounts,List<WithDrawableAccounts> defaultAccounts ) {
        this.defaultAccounts = defaultAccounts;
        this.depAccounts = depAccounts;
    }
    public void processWithdraw(){
        for(WithDrawableAccounts ac : defaultAccounts){
            ac.withdraw();

        }
    }


}

public class LSPFollowed{
    public static void main(String[] args) {
       List<NonWithDrawableAccounts> depAccounts = new ArrayList<>();
        List<WithDrawableAccounts> defaultsAccounts = new ArrayList<>();
        defaultsAccounts.add(new Savings());
        defaultsAccounts.add(new Current());
        depAccounts.add(new FixedDeposit());
        BankClient client = new BankClient(depAccounts,defaultsAccounts);
        client.processWithdraw(); //works because it only calls withdrawble accounts
    }
}