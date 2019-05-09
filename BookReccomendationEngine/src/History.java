import java.sql.Timestamp;

public class History
{
    private Book purchasedBook;
    private Timestamp timestamp;

    public History(Book book)
    {
        purchasedBook = book;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Book getBook()
    {
        return purchasedBook;
    }

    public String toString()
    {
        return "Time: "+timestamp+"\tTitle: " +purchasedBook.getKeyword().get(0); 
    }

}