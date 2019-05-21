import javax.swing.text.html.HTMLDocument;
import java.io.BufferedWriter;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Engine
{
    private static Engine engineInstance = new Engine();
    private static Hashtable<String,ArrayList<Book>> bookCollection;
    private Account currentUser;


    private Engine()
    {
        bookCollection = new Hashtable<String,ArrayList<Book>>();
        currentUser = null;
        initializeBook();
        initializeAccount();
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

    public static Engine getEngineInstance()
    {
        return engineInstance;
    }

    public Account getCurrentUser()
    {
        return currentUser;
    }

    public ArrayList<Book> getAllBooks()
    {
        Hashtable<String, Book> bookShelf = Book.getBookCollection();
        ArrayList<Book> allBooks = new ArrayList<Book>();
        Set<String> keys = bookShelf.keySet();
        for(String key: keys)
        {
            allBooks.add(bookShelf.get(key));
        }
        return allBooks;
    }

    public Book getSelectedBook(ArrayList<Book> books, int index)
    {
        Iterator<Book> bookIterator = books.iterator();
        Book book = null;
        int i = 0;
        do
        {
            book = bookIterator.next();
            i++;
        }
        while (bookIterator.hasNext() && i < index);

        return book;
    }

    public Hashtable<String,ArrayList<Book>> getBookCollection()
    {
        return bookCollection;
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

    public void initializeAccount()
    {
        FileManager fileManager = new FileManager();
        fileManager.loadAccount();
    }

    public boolean isUsernameExist(String username)
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();

        if (accounts.containsKey(username))    // there is this username in the system
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public ArrayList<Book> searchBook(String keyword)
    {
        ArrayList<Book> foundBook = new ArrayList<Book>();

        keyword = keyword.toUpperCase();
        if(!bookCollection.containsKey(keyword))
        {
            System.out.println("\tNo " + keyword + " in library");
            return null;
        }
        else
        {
            foundBook = bookCollection.get(keyword);
            return foundBook;
        }
    }    

    public boolean buyBook(String title)
    {
        Hashtable<String, Book> bookShelf = Book.getBookCollection();
        title = title.toUpperCase();
        Book book = bookShelf.get(title);

        //Add book's purchaser
        if(book.addPurchaser(currentUser))
        {
            //Add book to customer's history
            Customer customer = currentUser.getCustomer();
            customer.addPurchasedHistory(book);
            return true;
        }
        else
        {
            System.out.println("ERROR - cannot buy book");
            return false;
        }
    }

    public int login(String username, String password)
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();

        if(!isUsernameExist(username))  // there is no this username in the system
        {
            System.out.println("\tERROR - " + username + " does not exist");
            return -1;
        }
        else if(currentUser != null)    // there is another user in the system
        {
            System.out.println("\tERROR - system is busy");
            return -2;
        }
        else
        {
            Account account = accounts.get(username);

            // wrong password
            if(account.getPassword().compareTo(password) != 0)
            {
                System.out.println("\tERROR - wrong password for " + username);
                return 0;
            }
            else
            {
                currentUser = accounts.get(username);
                currentUser.login();
                return 1;
            }
        }
    }

    public boolean logout()
    {
        if(currentUser == null)
        {
            System.out.println("\tERROR - system is empty");
            return false;
        }
        else
        {
            currentUser.logout();
            currentUser = null;
            return true;
        }
    }

    public boolean register(String username, String password, String name, String surname)
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();
        if (accounts.containsKey(username))    // there is this username in the system
        {
            System.out.println("\tERROR - " + username + " already exists");
            return false;
        }
        else
        {
            new Account(username, password, name, surname);
            return true;
        }
    }

    public boolean saveUserDataFile()
    {
        FileManager fileManager = new FileManager();
        return fileManager.saveUser();
    }

   
}
