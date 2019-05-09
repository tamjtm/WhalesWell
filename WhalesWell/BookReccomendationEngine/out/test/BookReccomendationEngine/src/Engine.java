import java.util.ArrayList;
import java.util.Hashtable;

public class Engine
{
    private Hashtable<String,ArrayList<Book>> bookCollection;

    public Engine()
    {
        bookCollection = new Hashtable<String,ArrayList<Book>>();
        initialize();
    }

    public void initialize()
    {
        FileManager reader = new FileManager();

        if(!reader.open("BookDB.txt"))
        {
            System.out.println("FAIL!\n\n");
            System.exit(1);
        }

        Book nextBook = reader.loadBook();
        while (nextBook != null)
        {
            handleKeyword(nextBook);
            nextBook = reader.loadBook();
        }

        reader.close();
    }

    private void handleKeyword(Book book)
    {
        String keyword;
        ArrayList<Book> books;
        /* for each keyword */
        for(int i = 0; i < book.getKeyword().size(); i++)
        {
            keyword = book.getKeyword().get(i);

            /* if there is matched keyword */
            if(bookCollection.containsKey(keyword))
            {
                /* get books which have matched keyword */
                books = bookCollection.get(keyword);
                if(!books.add(book))
                {
                    System.out.println("Error - cannot add this book! (old keyword)");
                }
            }
            else
            {
                books = new ArrayList<>();
                /* add first element of ArrayList */
                if(!books.add(book))
                {
                    System.out.println("Error - cannot add this book! (new keyword)");
                }
                bookCollection.put(keyword, books);
            }
        }
    }

    public void printAll()
    {
        System.out.println(bookCollection);
    }
}
