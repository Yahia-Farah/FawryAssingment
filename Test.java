public class Test {
    public static void main(String[] args) {
        Bookstore store = new Bookstore(500);
        
       
        store.addBook(new PaperBook("PL123", "Java Programming", "2018", 29.99, "Mahmoud Ali", 10));
        store.addBook(new EBook("PL456", "Python Basics", "2020", 19.99, "Yahia Ahmed", "PDF"));
        store.addBook(new ShowcaseBook("PL789", "Learn PHP", "2024", 0.0, "Youssef Ehab"));
        
      
        Book[] outdated = store.removeOutdatedBooks("2023", 3);
        System.out.println("BookStore: Removed " + outdated.length + " outdated books");
        

        try {
            double total = store.buyBook("PL123", 2, "", "Al Maadi");
            System.out.println("BookStore: Paid amount: EGP" + total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            store.buyBook("PL789", 1, "your-email@example.com", "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        try {
            double total = store.buyBook("PL456", 1, "user@example.com", "");
            System.out.println("BookStore: Paid amount: EGP" + total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
       
        try {
            store.buyBook("invalid", 1, "", "");
        } catch (Exception e) {
            System.out.println(e.getMessage());
 }
}
}