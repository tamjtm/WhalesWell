import java.util.ArrayList;

public class BookCollection
{
    private ArrayList<Book> books = new ArrayList<Book>();

    public boolean addBook(Book book)
    {
        return books.add(book);
    }

    public void printAll()
    {
        for(int i = 0; i < books.size(); i++)
        {
            System.out.println(books.get(i));
        }
    }
}
