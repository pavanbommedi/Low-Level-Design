import java.util.*;

enum DeliveryPartnerAvailabilty{
    AVAILABLE,
    ASSIGNED,
    BUSY
}

class DeliveryPartner{
    private String partnerId;
    private String name;
    private String phoneNumber;
    private DeliveryPartnerAvailabilty availabilty;
    private Order assignedOrder;

    DeliveryPartner(String name,String phoneNumber){
        this.name = name;
        this.partnerId=UUID.randomUUID().toString();
        this.phoneNumber=phoneNumber;
        this.availabilty=DeliveryPartnerAvailabilty.AVAILABLE;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isAvailable() {
        return availabilty==DeliveryPartnerAvailabilty.AVAILABLE;
    }

    public void assignOrder(Order order){
        if(!isAvailable()){
             throw new IllegalStateException(
                    "Delivery partner is not available");
        }
        this.assignedOrder=order;
        this.availabilty=DeliveryPartnerAvailabilty.ASSIGNED;
    }
    public void release() {

        this.assignedOrder = null;

        this.availabilty =
                DeliveryPartnerAvailabilty.AVAILABLE;
    }
    
}
enum PaymentStatus{
    SUCCESS,
    FAILED
}
abstract class Payment{
    protected PaymentStatus paymentStatus;
    abstract void pay(double amount);
    public PaymentStatus getPaymentStatus(){
        return paymentStatus;
    }
    protected void setPaymentStatus(PaymentStatus status){
        this.paymentStatus=status;
    }
}
class CreditCardPayment extends Payment{
    void pay(double amount){
        //Payment logic
        System.out.println("credit card payment succesfull");
        setPaymentStatus(PaymentStatus.SUCCESS);
    }
}
class UPIPayment extends Payment{
    void pay(double amount){
        //Payment logic
        System.out.println("UPI Payment payment succesfull");
        setPaymentStatus(PaymentStatus.SUCCESS);
    }
}
class CashPayment extends Payment{
    void pay(double amount){
        //Payment logic
        System.out.println("Cash payment succesfull");
        setPaymentStatus(PaymentStatus.SUCCESS);
    }
}
class DeliveryManager{
    List<DeliveryPartner> deliveryPartners;

    DeliveryManager(){
        deliveryPartners=new ArrayList<>();
    }

    public void addDeliveryPartner(String name,String phoneNumer){
        DeliveryPartner partner = new DeliveryPartner(name, phoneNumer);
        deliveryPartners.add(partner);
        System.out.println("Delivery partner "+partner.getName()+" is added");
    }
    public DeliveryPartner findAvailableDeliveryPartner(){
        for(DeliveryPartner partner:deliveryPartners){
            if(partner.isAvailable()){
                return partner;
            }
        }
        return null;
    }
    public boolean assignPartner(Order order) {

        DeliveryPartner partner =
                findAvailableDeliveryPartner();


        if (partner == null) {

            return false;
        }


        partner.assignOrder(order);

        order.assignDeliveryPartner(
                partner
        );

        return true;
    }


    public void releasePartner(Order order) {

        DeliveryPartner partner =
                order.getDeliveryPartner();


        if (partner != null) {

            partner.release();
        }
    }
}
class OrderService{
    private DeliveryManager deliveryManager;

    OrderService(DeliveryManager deliveryManager){
        this.deliveryManager=deliveryManager;
    }
    public void assignDeliveryPartner(Order order){
        DeliveryPartner partner = deliveryManager.findAvailableDeliveryPartner();
        if(partner!=null) {
            order.assignDeliveryPartner(partner);
            partner.assignOrder(order);
        }
        else System.out.println("No delivery partner found");
    }
    public void completeDelivery(
            Order order
    ) {

        order.updateOrderStatus(
                OrderStatus.DELIVERED
        );

        deliveryManager.releasePartner(
                order
        );
    }
}
enum OrderStatus{
    PLACED,
    CONFIRMED,
    PREPARING,
    READY_PICKUP,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED
}
class Order{
    private String orderId;
    private Customer customer;
    private Restaurant restaurant;
    private List<OrderItem> orderItems;
    private double totalAmount;
    private OrderStatus orderStatus;
    private DeliveryPartner deliveryPartner;


    Order(Customer customer,Restaurant restaurant,List<OrderItem> orderItems,double totalAmount){
        this.customer=customer;
        this.restaurant=restaurant;
        this.orderItems=new ArrayList<>(orderItems);
        this.totalAmount = totalAmount;
        this.orderId = UUID.randomUUID().toString();
        this.orderStatus=OrderStatus.PLACED;
      

    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }


    public double getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public DeliveryPartner getDeliveryPartner(){
        return deliveryPartner;
    }
     // Method to progress the order through its lifecycle
    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        System.out.println("Status of order: "+orderStatus);
    }

    // public void addOrderItem(OrderItem item){
    //     orderItems.add(item);
    //     System.out.println("orderItem "+item.getName()+" addded to cart");

    // }
    // public double totalPrice(List<OrderItem> items){
    //     double price = 0;
    //     for(OrderItem item:items){
    //         price+=item.getPrice();
    //     }
    //     return price;
    // }
    public void assignDeliveryPartner(DeliveryPartner partner) {
        // 1. Set the partner for this specific order
        this.deliveryPartner = partner;
        System.out.println("Partner " + partner.getName() + " assigned to order " + this.orderId);
    }

    public void displayOrder(){
        for(OrderItem item:orderItems){
            System.out.println("name: "+item.getName()+" quantity: "+item.getQuantity()+" price: "+item.getPrice());
        }
    }
    public boolean canCancel() {

        return orderStatus ==
                        OrderStatus.PLACED

                || orderStatus ==
                        OrderStatus.CONFIRMED

                || orderStatus ==
                        OrderStatus.PREPARING

                || orderStatus ==
                        OrderStatus.READY_PICKUP;
    }
    
}
class OrderItem{
    private int itemId;
    private String name;
    private double price;
    private int quantity;

    OrderItem(int itemId,String name,double price,int quantity){
        this.itemId=itemId;
        this.name = name;
        this.price = price;
        this.quantity=quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity(){
        return quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public double getTotalPrice(){
        return this.price*this.quantity;
    }
    
}
class Cart{
    private Map<MenuItem,Integer> cartItems;
    private Restaurant restaurant;

    Cart(){
        this.cartItems = new LinkedHashMap<>();
        this.restaurant=null;
    }

    public void addItem(MenuItem item,int quantity,Restaurant res){
         // 1. Lock the cart to this restaurant if it's currently empty
        if(this.restaurant==null) {
            this.restaurant=res;
        }
        if(res.equals(this.restaurant)) cartItems.put(item, cartItems.getOrDefault(item, 0) + quantity);
        else System.out.println("items should be in same restaurant");

    }
    public void removeItem(MenuItem item,int quantity){
        if(!cartItems.containsKey(item)) return;
        int current_quantity = cartItems.get(item);
        if(current_quantity<=quantity){
            cartItems.remove(item);
            System.out.println(item.getName() + " removed from cart.");
        } 
        else{
            cartItems.put(item, current_quantity-quantity);
            System.out.println(quantity + "x " + item.getName() + " removed.");
        }

        if(cartItems.isEmpty()) this.restaurant = null;
    }
    public double calcTotalPrice(){
        double amount = 0;
        for(Map.Entry<MenuItem,Integer> entry : cartItems.entrySet()){
            amount += entry.getValue()*entry.getKey().getPrice();
        }
        return amount;
    }
    public Map<MenuItem,Integer> getCartItems(){
        return cartItems;
    }
    public void diplayCart(){
        for(Map.Entry<MenuItem,Integer> entry : cartItems.entrySet()){
            System.out.println("item: "+entry.getKey()+" quantity: "+entry.getValue());
        }
    }
    public void clearCart(){
        cartItems.clear();
        this.restaurant = null;
        System.out.println("cart is cleared");
    }
    public Restaurant getRestaurant(){
        return restaurant;
    }

}
class MenuItem{
    private int itemId;
    private String name;
    private double price;

    MenuItem(int id ,String name,double price){
        if (price < 0) {
            throw new IllegalArgumentException(
                    "Price cannot be negative"
            ); }
        this.itemId=id;
        this.name=name;
        this.price=price;
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return itemId + " - " + name + " - ₹" + price;
    }
    
}
class FoodDeliverySystem{
    List<Restaurant> restaurants;

    FoodDeliverySystem(){
        restaurants =new ArrayList<>();
    }

    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
        System.out.println(
    "Restaurant " + restaurant.getName() + " is added to system"
);
    }
    public void removeRestaurant(int resId){
        for(Restaurant res:restaurants){
            if(res.getResId()==(resId)){
                 restaurants.remove(res);
                 //Use iterator to avoid concurrentModificationException
                 return;
            }
        }
        System.out.println("Restaurant not found to remove");
    }
    public void viewRestaurants(){
        for(Restaurant res : restaurants){
            System.out.println(res.getName());
        }
    }
    
}
class Customer{
    private int customerId;
    private String name;
    private String phoneNumber;
    private Cart cart;
    private FoodDeliverySystem fdsSystem;
    private OrderPlaceService orderPlaceService;
    private Order currentOrder;

    Customer(int id,String name,String phoneNumber,FoodDeliverySystem fdsSystem,OrderPlaceService orderPlaceService){
        this.name = name;
        this.customerId=id;
        this.phoneNumber=phoneNumber;
        this.fdsSystem=fdsSystem;
        this.cart = new Cart();
        this.orderPlaceService=orderPlaceService;
    }

    public int  getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void viewRestaurant(){
        fdsSystem.viewRestaurants();
    }
    public void addItemsToCart(MenuItem item,int quantity,Restaurant restaurant){
        cart.addItem(item, quantity, restaurant);
    }
    public void removeItemsFromCart(MenuItem item,int quantity){
        cart.removeItem(item, quantity);
    }
    public void viewCart(){
        cart.diplayCart();
    }
   public Order placeOrder(
            Payment payment
    ) {

        Order order =
                orderPlaceService
                        .checkoutAndPlaceOrder(
                                this,
                                cart,
                                payment
                        );


        if (order != null) {

            currentOrder = order;
        }


        return order;
    }
    public void cancelOrder() {

        if (currentOrder == null) {

            System.out.println(
                    "No current order"
            );

            return;
        }


        if (currentOrder.canCancel()) {

            currentOrder.updateOrderStatus(
                    OrderStatus.CANCELLED
            );

            System.out.println(
                    "Order cancelled successfully"
            );

        } else {

            System.out.println(
                    "Order cannot be cancelled at status: "
                            + currentOrder
                            .getOrderStatus()
            );
        }
    }
    public Order getCurrentOrder() {

        return currentOrder;
    }


}
class OrderPlaceService {
    private OrderService orderService; // To assign delivery partners

    public OrderPlaceService(OrderService orderService) {
        this.orderService = orderService;
    }

    // FIX: Accept the Customer and their Cart to build the order dynamically
    public Order checkoutAndPlaceOrder(Customer customer, Cart cart, Payment payment) {
        if (cart.getCartItems().isEmpty()) {
            System.out.println("Cart is empty! Cannot place order.");
            return null;
        }
        Restaurant restaurant = cart.getRestaurant();
        if (restaurant == null) {

            System.out.println(
                    "Restaurant missing from cart."
            );

            return null;
        }

        // 1. Convert Cart items into OrderItems
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;
        for (Map.Entry<MenuItem, Integer> entry : cart.getCartItems().entrySet()) {
            MenuItem menu = entry.getKey();
            int qty = entry.getValue();
            
            OrderItem orderItem = new OrderItem(menu.getItemId(), menu.getName(), menu.getPrice(), qty);
            orderItems.add(orderItem);
            totalAmount += orderItem.getTotalPrice(); // Assuming OrderItem.getTotalPrice() exists
        }

        // 2. Instantiate the real order object
        Order order = new Order(customer, restaurant, orderItems, totalAmount);
        order.updateOrderStatus(OrderStatus.PLACED);
        System.out.println("Order " + order.getOrderId() + " has been placed.");

        // 3. Process payment
        payment.pay(order.getTotalAmount());
        if (!payment.getPaymentStatus().equals(PaymentStatus.SUCCESS)) {
            System.out.println("Payment failed. Terminating order.");
            order.updateOrderStatus(OrderStatus.CANCELLED);
            return order; 
        }
        order.updateOrderStatus(OrderStatus.CONFIRMED);

        // 4. Simulate restaurant processing asynchronously/sequentially 
        order.updateOrderStatus(OrderStatus.PREPARING);
        
        order.updateOrderStatus(OrderStatus.READY_PICKUP);

        // 5. Try assigning delivery partner
        orderService.assignDeliveryPartner(order);
        
        // FIX: Verify if a partner was actually found before updating status
        if (order.getDeliveryPartner() != null) {
            order.updateOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
            order.updateOrderStatus(OrderStatus.DELIVERED);
        } else {
            System.out.println("Order is delayed. Waiting for a delivery partner.");
            // In a real system, you would queue this up rather than blocking the thread
        }

        // 6. Clear customer cart upon absolute checkout success
        cart.clearCart(); // Assuming you add a clear method to Cart
        
        return order;
    }
}
class Restaurant{
    private int resId;
    private String name;
    private String location;
    private List<MenuItem> menu;

    Restaurant(int resId,String name,String location){
        this.resId=resId;
        this.name=name;
        this.location=location;
        this.menu = new ArrayList<>();
    }

    public int getResId() {
        return resId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }
    public void addMenuItem(MenuItem item){
        if (findMenuItem(item.getItemId()) != null) { //Edge 

            throw new IllegalArgumentException(
                    "Menu item already exists"
            );
        }
        menu.add(item);
        System.out.println("item "+item.getName()+" is added to menu item list of "+this.name);
    }
    public void removeMenuItem(int itemId){
        for(MenuItem item:menu){
            if(item.getItemId()==(itemId)){
                menu.remove(item);
                return;
            }
        }
        System.out.println("item not found to remove");
    }
    public void displayMenu(){
        for(MenuItem item:menu){
            System.out.println("item: "+item.getName()+" price: "+item.getPrice());
        }
    }
    public MenuItem findMenuItem(int itemId){
        for(MenuItem item:menu){
            if(item.getItemId()==(itemId)) return item;
        }
        return null;
    }
    @Override
    public String toString() {
        return resId + " - " + name;
    }
    
}

public class FoodDeliveryClient {
    public static void main(String[] args) {
        FoodDeliverySystem foodDeliverySystem =new FoodDeliverySystem();
        DeliveryManager deliveryManager =new DeliveryManager();
        OrderService orderService =new OrderService(deliveryManager);
        OrderPlaceService orderPlaceService =new OrderPlaceService(orderService);
        //Create a Restaurant
        Restaurant paradise =
                new Restaurant(
                        1,
                        "Paradise Biryani",
                        "Hyderabad"
                );
        //Create Menu Items
        MenuItem biryani =
                new MenuItem(
                        101,
                        "Chicken Biryani",
                        280
                );


        MenuItem coke =
                new MenuItem(
                        102,
                        "Coke",
                        50
                );


        MenuItem dessert =
                new MenuItem(
                        103,
                        "Double Ka Meetha",
                        120
                );
        
        //Add Menu Items to Restaurant
        paradise.addMenuItem(
                biryani
        );


        paradise.addMenuItem(
                coke
        );


        paradise.addMenuItem(
                dessert
        );
        // Add Restaurant to System
        foodDeliverySystem.addRestaurant(
                paradise
        );
        //Another Restaurant
        Restaurant pizzaHub =
                new Restaurant(
                        2,
                        "Pizza Hub",
                        "Hyderabad"
                );


        MenuItem pizza =
                new MenuItem(
                        201,
                        "Farmhouse Pizza",
                        350
                );


        pizzaHub.addMenuItem(
                pizza
        );


        foodDeliverySystem.addRestaurant(
                pizzaHub
        );
        //Delivery Partners
        deliveryManager.addDeliveryPartner(
                "Ravi",
                "9000001"
        );


        deliveryManager.addDeliveryPartner(
                "Kiran",
                "9000002"
        );
        //Customer
        Customer customer =
                new Customer(
                        1001,
                        "Pavan",
                        "9876543210",
                        foodDeliverySystem,
                        orderPlaceService
                );
        //View Restaurants
        customer.viewRestaurant();
        paradise.displayMenu();
        customer.addItemsToCart(biryani,2, paradise);
        customer.addItemsToCart(
                coke,
                2,
                paradise
        );


        customer.addItemsToCart(
                dessert,
                1,
                paradise
        );
        customer.viewCart();
        //Place Order
        Payment payment =
                new UPIPayment();


        Order order =
                customer.placeOrder(
                        payment
                );
        //diplay order
        if (order != null) {

            order.displayOrder();
        }
    }
}
