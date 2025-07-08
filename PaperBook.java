
class PaperBook extends Book {
    
    int stock;
    
    public PaperBook(String isbn, String title, String year, double price, String author, int stock) {
        super(isbn, title, year, price, author);
        this.stock = stock;
    }

}

