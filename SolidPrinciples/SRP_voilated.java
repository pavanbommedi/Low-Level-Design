import java.util.ArrayList;
import java.util.List;

class Product{
    private double price;
    private String name;

    Product(double price, String name){
        this.price = price;
        this.name = name;
    }
    public double getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }

    @Override
public String toString() {
    return "Product{name='" + name + "', price=" + price + "}";
}

}

class ShoppingCart{
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p){
        products.add(p);
    }

    public List<Product> getProducts(){
        return products;
    }

    public double calcTotalPrice(){
        double price = 0;
        for(Product p : products){
            price+=p.getPrice();
        }
        return price;
    }

    //Voilating SRP should be in other class
    public void printInvoice(){
        System.out.println("Products Invoice");
        for(Product p : products){
            System.out.println(p.getName()+" -$"+p.getPrice());
        }
        System.out.println("Total : $"+calcTotalPrice());
    }

    //Voilating SRP should be in other class
    public void saveToDB(){
        //DB logic
        System.out.println("Saving Shopping cart to Database");
    }
}

public class SRP_voilated {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product(200, "Peanut Butter"));
        cart.addProduct(new Product(500, "Protein Oats"));
        cart.addProduct(new Product(600, "Creatine"));

        System.out.println(cart.getProducts());
        System.out.println(cart.calcTotalPrice());
        cart.printInvoice();
        cart.saveToDB();

    }

}


// So now the class has three responsibilities:

// ShoppingCart

// 1. Manage products
// 2. Print invoice
// 3. Save to database

// That's exactly what SRP says not to do.