import java.beans.Customizer;
import java.util.ArrayList;
import java.util.Hashtable;

public class Book
{
    private String title;
    private String author;
    private String category;
    private int length;
    private ArrayList<String> keyword;
    private int price;
    private int purchasedAmount;
    private ArrayList<Account> purchaser;
    private static Hashtable<String,Book> bookCollection = new Hashtable<String,Book>();

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

    public String getTitle()
    {
        return title;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    public static Hashtable<String, Book> getBookCollection()
    {
        return bookCollection;
    }

    public int getPurchaseAmount()
    {
        return purchasedAmount;
    }

    public ArrayList<Account> getPurchaser()
    {
        return purchaser;
    }

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

    @Override
    public String toString() {
        
        return "\nTitle: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nNumber of Pages: " + length +
                "\nKeyword: " + keyword.toString() +
                "\nPrice: " + price + " Baht\n";
                
        //return title + "\n";
    }
}
