import java.util.ArrayList;

public class Book
{
    private String title;
    private String author;
    private String category;
    private int length;
    private ArrayList<String> keyword;
    private int price;
    private int purchasedAmount;
    //private ArrayList<Account> purchaser;

    public Book(String title, String author, String category, int length, ArrayList<String> keyword, int price)
    {
        this.title = title;
        this.author = author;
        this.category = category;
        this.length = length;
        this.keyword = keyword;
        this.price = price;
        purchasedAmount = 0;
    }

    public ArrayList<String> getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        /*return "Title: " + title +
                "\nAuthor: " + author +
                "\nCategory: " + category +
                "\nNumber of Pages: " + length +
                "\nKeyword: " + keyword.toString() +
                "\nPrice: " + price + " ฿\n\n";*/
        return title;
    }
}
