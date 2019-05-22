import java.beans.Customizer;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * Book
 *
 *   This class represents a book in Book Recommendation Engine.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     8 May 2019
 *
 */
public class Book
{
    /** title of book */
    private String title;

    /** author of book */
    private String author;

    /** category of book */
    private String category;

    /** number of page */
    private int length;

    /** related keyword of book for searching and suggestion*/
    private ArrayList<String> keyword;

    /** price of book */
    private int price;

    /** purchased amount of book */
    private int purchasedAmount;

    /** purchasers that purchasing book */
    private ArrayList<Account> purchaser;

    /** collection of all book in engine */
    private static Hashtable<String,Book> bookCollection = new Hashtable<String,Book>();

    /**
     *      construct a book instance and initialize all member.
     *  Then, add a book to book collection.
     *
     * @param title book's title
     * @param author book's author
     * @param category book's category
     * @param length number of page
     * @param keyword book's keywords
     * @param price book's price
     */
    public Book(String title, String author, String category, int length, ArrayList<String> keyword, int price)
    {
        this.title = title;
        this.author = author;
        this.category = category;
        this.length = length;
        this.keyword = keyword;
        this.price = price;
        purchasedAmount = 0;
        purchaser = new ArrayList<Account>();
        bookCollection.put(title, this);
    }

    /**
     *      get title of book
     *
     * @return  title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *      get a list of keywords
     *
     * @return  keywords
     */
    public ArrayList<String> getKeyword() {
        return keyword;
    }

    /**
     *      get collection of all book
     *
     * @return  book collection
     */
    public static Hashtable<String, Book> getBookCollection()
    {
        return bookCollection;
    }

    /**
     *      get purchased amount of book
     *
     * @return  purchased amount
     */
    public int getPurchaseAmount()
    {
        return purchasedAmount;
    }

    /**
     *      get list of purchasers
     *
     * @return  purchasers
     */
    public ArrayList<Account> getPurchaser()
    {
        return purchaser;
    }

    /**
     *      get price of book
     *
     * @return  price
     */
    public int getPrice()
    {
        return price;
    }

    /**
     *      get number of page
     *
     * @return  number of page
     */
    public int getLength()
    {
        return length;
    }

    /**
     *      add purchaser to purchasers list.
     *  Using when there is a new purchaser or initial system
     *  and load book purchaser.
     *
     * @param   currentPurchaser purchaser who bought this book
     * @return  true if adding purchaser is success
     */
    public boolean addPurchaser(Account currentPurchaser)
    {
        //if user hasn't bought this book before
        if (!purchaser.contains(currentPurchaser))
        {
            purchaser.add(currentPurchaser);            
        }
        //For check, if user was added to list
        if(purchaser.contains(currentPurchaser))
        {
            purchasedAmount++;
            return true;
        }
        else
        {
            return false;
        }
    }

     /**
     *      used for display Book Class information
     * @return string of book detail
     */
    @Override
    public String toString() 
    {    
        return "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nNumber of Pages: " + length +
                "\nPrice: " + price + " Baht\n";
    }
}
