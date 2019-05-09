import java.util.ArrayList;
import java.util.Hashtable;


public class Engine
{
    static private Hashtable<String,ArrayList<Book>> bookCollection;
    private Account currentUser;


    public Engine()
    {
        bookCollection = new Hashtable<String,ArrayList<Book>>();
        currentUser = null;
        initializeBook();
    }

    public Account getCurrentUser()
    {
        return currentUser;
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

    public boolean printSelectedBook(String title)
    {
        Hashtable<String, Book> bookShelf = Book.getBookCollection();
        //If there is matched book
        if(bookShelf.containsKey(title))    
        {
            Book selectedBook = bookShelf.get(title);
            System.out.println(selectedBook);
            return true;   
        }
        else
        {
            System.out.println("--- Cannot find the book.");
            return false;   
        }
    }

    public boolean buyBook()
    {
        String title = IOUtils.getString("Please enter book title");
        if(printSelectedBook(title))
        {
            String response = IOUtils.getString("Confirm buying... [Y/N]");
            if ((response.startsWith("Y")) || (response.startsWith("y")))
            {
                Hashtable<String, Book> bookShelf = Book.getBookCollection();
                Book book = bookShelf.get(title);
                
                //Add book's purchaser
                if(book.addPurchaser(currentUser))
                {
                    //Add book to customer's history
                    Customer customer = currentUser.getCustomer();
                    customer.addPurchasedHistory(book);
                }
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
        
    }

    public ArrayList<Book> showContentSuggest()
    {
        //Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        ArrayList<Book> suggestedBooks = new ArrayList<Book>();
        ArrayList<Book> tempBook = new ArrayList<Book>();
        //Haven't bought any book before  
        if(customerHistory.size()==0)
        {
            System.out.println("--- Not found book reference.");
            return null;
        }
        else
        {
            //Get latest book
            Book latestBook = customerHistory.get(customerHistory.size()-1).getBook();
            System.out.println("Reference on: \n"+latestBook+"\n");

            for(int i=1; i<latestBook.getKeyword().size(); i++)
            {
                String keyword = latestBook.getKeyword().get(i);
                if(bookCollection.containsKey(keyword))
                {
                    tempBook = bookCollection.get(keyword);
                    if(tempBook.size()>1)
                   {
                        for(int j=0;j<tempBook.size();j++)
                        {
                            if(!suggestedBooks.contains(tempBook.get(j)))
                            {
                                suggestedBooks.add(tempBook.get(j));
                            }
                        }
                    }
                }
            }
            if(suggestedBooks.contains(latestBook))
            {
                suggestedBooks.remove(latestBook);
            }

            return suggestedBooks;
        }
    }

    public ArrayList<Book> showCommuSuggest()
    {
        //Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        ArrayList<Book> suggestedBooks = new ArrayList<Book>();
        //Haven't bought any book before  
        if(customerHistory.size()==0)
        {
            System.out.println("--- Not found book reference.");
            return null;
        }
        else
        {
            //Get latest book
            Book latestBook = customerHistory.get(customerHistory.size()-1).getBook();
            ArrayList<Account> purchaser = latestBook.getPurchaser();
            purchaser.remove(currentUser);
            System.out.println("Reference on: \n"+latestBook+"\n");
            if(purchaser.size()==0)
            {
                System.out.println("--- No user has bought this book before.");
                return null;
            }
            else
            {
                for(int i=0;i<purchaser.size();i++)
                {
                    Customer otherCustomer = purchaser.get(i).getCustomer();
                    ArrayList<History> otherHistory = otherCustomer.getPurchasedHistory();
                    if(otherHistory.size()>1)
                    {
                        for(int j=0;j<otherHistory.size();j++)
                        {
                            Book temp = otherHistory.get(j).getBook();
                            if(!suggestedBooks.contains(temp))
                            {
                                suggestedBooks.add(temp);
                            }
                        }
                    }
                }
                if(suggestedBooks.contains(latestBook))
                {
                    suggestedBooks.remove(latestBook);
                }
                if(suggestedBooks.size()==0)
                {
                    System.out.println("--- Other purchaser(s) haven't bought other book.");
                    return null;
                }
            }
            return suggestedBooks;
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
    
}
