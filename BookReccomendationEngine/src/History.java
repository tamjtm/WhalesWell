import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * History
 *
 *   This class represents an book purchased history in Book Recommendation Engine,
 *      which is a user's purchased book with the time she bought.
 *
 *   Created by Nutaya Pravalphreukul (Pear)   ID 59070501032
 *     9 May 2019
 *
 */
public class History
{
    /** purchased book */
    private Book purchasedBook;

    /** time that user purchased book */
    private Timestamp timestamp;

    /**
     *      Construct history instance and initialize all members in History Class for new history
     *
     * @param book  purchased book
     */
    public History(Book book)
    {
        purchasedBook = book;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    /**
     *      get purchased book from history
     *
     * @return  purchased book
     */
    public Book getBook()
    {
        return purchasedBook;
    }

    /**
     *      load purchased history and initialize book and timestamp member from kept data
     * @param time  that user purchase book
     * @param book  that user purchased
     */
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

    /**
     *      used for display History Class information
     *
     * @return  string of History Class
     */
    public String toString()
    {
        return timestamp+"\t" +purchasedBook.getKeyword().get(0); 
    }

}