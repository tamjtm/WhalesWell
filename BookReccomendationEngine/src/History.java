import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void loadHistory(String time,Book book)
    {
        try 
        {
            this.purchasedBook = book;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(time);
            this.timestamp = new java.sql.Timestamp(parsedDate.getTime());
        } catch(Exception e) { 
            //this generic but you can control another types of exception
            // look the origin of excption 
        }
    }

    public String toString()
    {
        return timestamp+"\t" +purchasedBook.getKeyword().get(0); 
    }

}