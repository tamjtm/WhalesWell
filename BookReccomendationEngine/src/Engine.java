import javax.swing.text.html.HTMLDocument;
import java.io.BufferedWriter;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Engine
 *
 *   This class represents a main engine in Book Recommendation Engine.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *      8 May 2019
 *
 *   Modified by Nutaya Pravalphreukul (Pear)   ID 59070501032
 *      9 May 2019
 *      Add buyBook() and saveUserDataFile() method
 *
 */
public class Engine
{
    /** instance of Engine Class for singleton pattern */
    private static Engine engineInstance = new Engine();

    /** book collection grouped by keyword */
    private static Hashtable<String,ArrayList<Book>> bookCollection;

    /** current user that logging in at the time */
    private Account currentUser;

    /**
     *      Construct an engine instance for singleton pattern and initialize all member of system.
     */
    private Engine()
    {
        bookCollection = new Hashtable<String,ArrayList<Book>>();
        currentUser = null;
        initializeBook();
        initializeAccount();
    }

     /**
     *      load book data from BookDB.txt file
     */
    private void initializeBook()
    {
        BookFileReader reader = new BookFileReader();

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

    /**
     *     load account data by calling loadAccount() method in UserFileManager Class
     */
    private void initializeAccount()
    {
        UserFileManager userFileManager = new UserFileManager();
        userFileManager.loadAccount();
    }

    /**
     *     collect book in books collection grouped by keyword of the book
     *
     * @param book  that will be added to collection
     */
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

    /**
     *      get instance of engine for singleton pattern
     *
     * @return  engine instance
     */
    public static Engine getEngineInstance()
    {
        return engineInstance;
    }

    /**
     *      get current user that logging in at the time
     *
     * @return  account of current user
     */
    public Account getCurrentUser()
    {
        return currentUser;
    }

    /**
     *      get list of all books that collected in Book class
     *
     * @return  list of all books
     */
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

    /**
     *      get the book at the index from list of books
     *
     * @param books list of books that user want to get
     * @param index position of the book that user want to get
     * @return  the book at the index from list of books
     */
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

    /**
     *      get collection of all books grouped by keyword
     *
     * @return  book collection
     */
    public Hashtable<String,ArrayList<Book>> getBookCollection()
    {
        return bookCollection;
    }

    
    /**
     *      check existing of username in account collection
     *
     * @param username
     * @return  true if username exists
     */
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

    /**
     *      search book by passing keyword in book collection
     * 
     * @param keyword user's searched keyword 
     * @return  lists of book that matched the keyword
     */
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

     /**
     *      buy the book that matched the passed title
     *
     * @param title
     * @return true if buying is success
     */
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

    /**
     *      check that username and password are matched in account correction.
     *  If they are matched, call login() method from Account Class.
     *
     * @param username passed username
     * @param password passed password
     * @return  log in status which have meaning following:
     *              -2 :    there is another user in the engine
     *              -1 :    there is no username in account collection
     *               0 :    input wrong password
     *               1 :    login success
     *
     */
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

    /**
     *     logout for current user by calling logout() method from Account Class
     *
     * @return  true if logout success
     */
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

    /**
     *      register by create account instance with passed data
     *
     * @param username
     * @param password
     * @param name
     * @param surname
     * @return  true if registration is success
     *          false if username is already existed
     */
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

    /**
     *      save user data by calling saveUser() method from UserFileManager Class
     *
     * @return  true if saving is success
     */
    public boolean saveUserDataFile()
    {
        UserFileManager userFileManager = new UserFileManager();
        return userFileManager.saveUser();
    }

   
}
