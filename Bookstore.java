class Bookstore {
    private Book[] inventory;
    private int count;
    
    public Bookstore(int capacity) {
        inventory = new Book[capacity];
        count = 0;
    }
    
    public void addBook(Book book) {
        if (count < inventory.length) {
            inventory[count++] = book;
        } else {
            System.out.println("BookStore: Inventory full");
        }
    }
    
    public Book[] removeOutdatedBooks(String currentYear, int maxYears) {
        Book[] outdated = new Book[inventory.length];
        int outdatedCount = 0;
        
        for (int i = 0; i < count; i++) {
            int bookYear = Integer.parseInt(inventory[i].year);
            int current = Integer.parseInt(currentYear);
            
            if (current - bookYear > maxYears) {
                
                for (int j = i; j < count - 1; j++) {
                    inventory[j] = inventory[j + 1];
                }
                count--;
                i--; 
            }
        }
        
        Book[] result = new Book[outdatedCount];
        System.arraycopy(outdated, 0, result, 0, outdatedCount);
        return result;
    }
    
    public double buyBook(String isbn, int quantity, String email, String address) {
        for (int i = 0; i < count; i++) {
            if (inventory[i].isbn.equals(isbn)) {
                if (inventory[i] instanceof PaperBook) {
                    PaperBook pb = (PaperBook) inventory[i];
                    if (pb.stock < quantity) {
                        System.out.println("BookStore: Insufficient stock for " + pb.title);
                    }
                    pb.stock -= quantity;
                    System.out.println("BookStore: Shipping " + quantity + "" + 
                                      pb.title + " to " + address);
                    return quantity * pb.price;
                } 

                else if (inventory[i] instanceof EBook) {
                    EBook eb = (EBook) inventory[i];
                    System.out.println("BookStore: Sending " + eb.title + 
                                      " (" + eb.fileType + ") to " + email);
                    return quantity * eb.price;
                } 

                else if (inventory[i] instanceof ShowcaseBook) {
                    System.out.println("BookStore: " + inventory[i].title + 
                                             " is for showcase only");
                }
            }
        }
        System.out.println("BookStore: Book not found with ISBN: " + isbn);
        return quantity;
    }
}
