import java.util.*;

class Product {
    String name;
    double price;
    int quantity;
    boolean perishable;
    boolean shippable;
    double weight;
    Date expiry;

    Product(String name, double price, int quantity, boolean perishable, boolean shippable, double weight, Date expiry) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.perishable = perishable;
        this.shippable = shippable;
        this.weight = weight;
        this.expiry = expiry;
    }

    boolean isExpired() {
        if (!perishable || expiry == null) return false;
        return new Date().after(expiry);
    }

    boolean isAvailable(int q) {
        return quantity >= q && !isExpired();
    }
}

class Customer {
    String name;
    double balance;

    Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    void deduct(double amount) {
        balance -= amount;
    }
}

class CartItem {
    Product product;
    int quantity;

    CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double totalPrice() {
        return product.price * quantity;
    }
}

class Cart {
    List<CartItem> items = new ArrayList<>();

    void add(Product p, int q) {
        if (!p.isAvailable(q)) throw new RuntimeException("Unavailable or expired");
        items.add(new CartItem(p, q));
    }

    boolean isEmpty() {
        return items.isEmpty();
    }

    double getSubtotal() {
        double total = 0;
        for (CartItem i : items) total += i.totalPrice();
        return total;
    }

    boolean hasExpired() {
        for (CartItem i : items)
            if (i.product.isExpired()) return true;
        return false;
    }

    List<CartItem> getShippables() {
        List<CartItem> s = new ArrayList<>();
        for (CartItem i : items)
            if (i.product.shippable) s.add(i);
        return s;
    }
}

class ShippingService {
    static void ship(List<CartItem> items) {
        System.out.println("** Shipment notice **");
        double totalWeight = 0;
        for (CartItem item : items) {
            double w = item.product.weight * item.quantity;
            totalWeight += w;
            System.out.println(item.quantity + "x " + item.product.name + "\t\t" + (int) (w * 1000) + "g");
        }
        System.out.println("Total package weight " + String.format("%.1f", totalWeight) + "kg\n");
    }
}

class Checkout {
    static void process(Customer customer, Cart cart) {
        if (cart.isEmpty()) throw new RuntimeException("Cart is empty");
        if (cart.hasExpired()) throw new RuntimeException("Expired item in cart");

        double subtotal = cart.getSubtotal();
        double shipping = 30;
        double total = subtotal + shipping;

        if (customer.balance < total) throw new RuntimeException("Insufficient balance");

        List<CartItem> toShip = cart.getShippables();
        if (!toShip.isEmpty()) ShippingService.ship(toShip);

        customer.deduct(total);
        for (CartItem item : cart.items) {
            item.product.quantity -= item.quantity;
        }

        System.out.println("** Checkout receipt **");
        for (CartItem i : cart.items)
            System.out.println(i.quantity + "x " + i.product.name + "\t\t" + (int) i.totalPrice());
        System.out.println("----------------------");
        System.out.println("Subtotal\t\t" + (int) subtotal);
        System.out.println("Shipping\t\t" + (int) shipping);
        System.out.println("Amount\t\t\t" + (int) total);
        System.out.println("Remaining balance\t" + (int) customer.balance);
    }
}

public class Main {
    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 5);

        Product cheese = new Product("Cheese", 100, 5, true, true, 0.2, cal.getTime());
        Product biscuits = new Product("Biscuits", 150, 2, true, true, 0.7, cal.getTime());
        Product scratchCard = new Product("Scratch Card", 50, 10, false, false, 0, null);

        Customer c = new Customer("Yahia", 1000);
        Cart cart = new Cart();

        cart.add(cheese, 2);
        cart.add(biscuits, 1);
        cart.add(scratchCard, 1);

        Checkout.process(c, cart);
    }
}
