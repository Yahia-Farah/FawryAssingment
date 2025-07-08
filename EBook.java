class EBook extends Book {

    String fileType;
    
    public EBook(String isbn, String title, String year, double price, String author, String fileType) {
        super(isbn, title, year, price, author);
        this.fileType = fileType;
    }
    
}
