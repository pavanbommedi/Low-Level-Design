import java.util.ArrayList;
import java.util.List;

class Product{
    private double price;
    private String name;
    //This prevents outside classes from modifying object state directly.

    public Product(double price, String name){
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
    // This is a HAS-A relationship.
    // A shopping cart has products.
    //Products can exist without a shopping cart. Aggregation

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
}

class CartInvoicePrinter{
    public void printInvoice(ShoppingCart cart){ //Composition over Inheritance
        //"The printer and database classes don't own a shopping cart. They simply perform an operation on any cart they're given. Passing the cart as a method parameter keeps these classes stateless, makes them reusable, and avoids unnecessary object coupling."
        System.out.println("Products Invoice");
        for(Product p : cart.getProducts()){
            System.out.println(p.getName()+" -$"+p.getPrice());
        }
        System.out.println("Total : $"+cart.calcTotalPrice());
    }
}

class CartDBStorage{
    public void saveToDB(ShoppingCart cart){
        //DB logic
        System.out.println("Saving Shopping cart to Database");
    }

}


public class SRP_followed {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(new Product(200, "Peanut Butter"));
        cart.addProduct(new Product(500, "Protein Oats"));
        cart.addProduct(new Product(600, "Creatine"));

        System.out.println(cart.getProducts());
        System.out.println(cart.calcTotalPrice());
        CartInvoicePrinter invoice = new CartInvoicePrinter();
        invoice.printInvoice(cart);
        CartDBStorage dbStore = new CartDBStorage();
        dbStore.saveToDB(cart);
    }

}

// Product
//     ↓
// Stores product information

// ShoppingCart
//     ↓
// Manages products and calculates total

// CartInvoicePrinter
//     ↓
// Prints invoice

// CartDBStorage
//     ↓
// Saves cart to database