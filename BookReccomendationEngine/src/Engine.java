import java.util.ArrayList;
import java.util.Hashtable;

public class Engine
{
    static private Hashtable<String,ArrayList<Book>> bookCollection;
    Account currentUser;


    public Engine()
    {
        bookCollection = new Hashtable<String,ArrayList<Book>>();
        currentUser = null;
        initializeBook();
    }

    public void initializeBook()
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
        // for each keyword
        for(int i = 0; i < book.getKeyword().size(); i++)
        {
            keyword = book.getKeyword().get(i);

            // if there is matched keyword
            if(bookCollection.containsKey(keyword))
            {
                // get books which have matched keyword
                books = bookCollection.get(keyword);
                if(!books.add(book))
                {
                    System.out.println("Error - cannot add this book! (old keyword)");
                }
            }
            else
            {
                books = new ArrayList<>();
                // add first element of ArrayList
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

    public boolean login(String username, String password)
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();

        if (!accounts.containsKey(username))    // there is no this username in the system
        {
            System.out.println("ERROR - " + username + " does not exist");
            return false;
        }
        else if(currentUser != null)    // there is another user in the system
        {
            System.out.println("ERROR - system is busy");
            return false;
        }
        else
        {
            Account account = accounts.get(username);

            // wrong password
            if(account.getPassword().compareTo(password) != 0)
            {
                System.out.println("ERROR - wrong password for " + username);
                return false;
            }
            else
            {
                currentUser = accounts.get(username);
                currentUser.login();
                return true;
            }
        }
    }

    public boolean logout()
    {
        if(currentUser == null)
        {
            System.out.println("ERROR - system is empty");
            return false;
        }
        else
        {
            currentUser.logout();
            return true;
        }
    }

    public boolean register(String username, String password, String name, String surname)
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();
        if (accounts.containsKey(username))    // there is this username in the system
        {
            System.out.println("ERROR - " + username + " already exists");
            return false;
        }
        else
        {
            new Account(username, password, name, surname);
            return true;
        }
    }
}