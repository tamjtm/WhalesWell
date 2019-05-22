import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * EngineUI
 *
 *   This class represents a User Interface of Book Recommendation Engine.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     10 May 2019
 *
 */
public class EngineUI
{
    /** instance of Engine Class for sending data to display */
    private static Engine engine;

    /** instance of SuggestTool Class for operating book suggestion */
    private static SuggestTool suggestTool;

    /**
     *      construct EngineUI Class and create instance of member
     */
    public EngineUI()
    {
        engine = engine.getEngineInstance();
        suggestTool = new SuggestTool();
    }

    /**
     *      start engine here
     *
     * @param args
     */
    public static void main(String args[])
    {
        EngineUI engineUI = new EngineUI();

        // loop until user login -> if user already login, logout
        while (engine.getCurrentUser() == null)
        {
            showLoginPage();
        }
    }

    /**
     *      display register page for registration with username that user want to register
     *
     * @param username  input username that user want to register
     */
    private static void showRegisterPage(String username)
    {
        String confirmStatus;
        String password;
        String name;
        String surname;
        do
        {
            System.out.println("\n--------------- R E G I S T E R ---------------");
            System.out.println("Username: " + username);
            System.out.printf("Password: ");
            password = IOUtils.getBareString();
            System.out.printf("Name: ");
            name = IOUtils.getBareString();
            System.out.printf("Surname: ");
            surname = IOUtils.getBareString();

            System.out.printf("\nType Y to confirm registration or other key to cancel.. ");
            confirmStatus = IOUtils.getBareString();
        }
        while (!confirmStatus.equalsIgnoreCase("Y"));

        // register by calling register from Engine Class
        boolean bRegister = engine.register(username, password, name, surname);
        if (bRegister)
        {
            System.out.println("\tRegistration success!");
        }
        else
        {
            System.out.println("\tERROR - register failed");
        }
    }

    /**
     *      display register page for registration without username that user want to register
     */
    private static void showRegisterPage()
    {
        String confirmStatus;
        String username;
        String password;
        String name;
        String surname;
        while (true)
        {
            do {
                System.out.println("\n--------------- R E G I S T E R ---------------");
                System.out.printf("Username: ");
                username = IOUtils.getBareString();
                System.out.printf("Password: ");
                password = IOUtils.getBareString();
                System.out.printf("Name: ");
                name = IOUtils.getBareString();
                System.out.printf("Surname: ");
                surname = IOUtils.getBareString();

                System.out.printf("\nType Y to confirm registration or other key to cancel.. ");
                confirmStatus = IOUtils.getBareString();
            }
            while (!confirmStatus.equalsIgnoreCase("Y"));

            // register by calling register from Engine Class
            boolean bRegister = engine.register(username, password, name, surname);
            if (bRegister)
            {
                System.out.println("\tRegistration success!");
                return;
            }
        }
    }

    /**
     *      display login page for logging in
     */
    private static void showLoginPage()
    {
        System.out.println("\n------------------ L O G I N ------------------");
        System.out.println("** You can type register/exit too.\n");
        System.out.printf("Username: ");
        String username = IOUtils.getBareString();

        if(username.equalsIgnoreCase("register"))       // if user input register
        {
            showRegisterPage();
        }
        else if(username.equalsIgnoreCase("exit"))      // if user input exit
        {
            exit();
            return;
        }
        else if (engine.isUsernameExist(username))       // there is this username in the system
        {
            System.out.printf("Password: ");
            String password = IOUtils.getBareString();

            int loginStatus = engine.login(username, password);     // try to login
            if (loginStatus == 1)       // login success
            {
                System.out.println("\tLogin success!");
                showMenuPage();
            }
            else if(loginStatus == 0)   // wrong password
            {
                System.out.printf("\nThis username is your account? Type Y to login again or other key to cancel.. ");
                String tryingStatus = IOUtils.getBareString();
                if (tryingStatus.equalsIgnoreCase("Y"))     // user input Y
                {
                    return;
                }
                else
                {
                    return;
                }
            }
            else
            {
                return;
            }
        }
        else        // there isn't this username in the system
        {
            System.out.printf("\nThis username does not exist. Type Y to register or other key to cancel.. ");
            String registerStatus = IOUtils.getBareString();

            if(registerStatus.equalsIgnoreCase("Y"))        // if user want to register (input Y)
            {
                showRegisterPage(username);
            }
            else
            {
                return;
            }
        }
    }

    /**
     *      display main menu page
     */
    private static void showMenuPage()
    {
        while (true)
        {
            System.out.println("\n------------------- M E N U -------------------");
            System.out.println("  1. Show all books");
            System.out.println("  2. Show content-based book suggestion");
            System.out.println("  3. Show community-based book suggestion");
            System.out.println("  4. Search book");
            System.out.println("  5. View profile");
            System.out.println("  6. Log out");
            System.out.println("-------------------------------------------------");

            System.out.printf("Choose your command number or type exit.. ");
            String command = IOUtils.getBareString();

            switch (command) {
                case "1":   // user choose to show all books
                    System.out.println("\n\t < < ALL BOOK > > >");
                    ArrayList<Book> allBooks = engine.getAllBooks();
                    printBookList(allBooks);
                    break;
                case "2":   // user choose to show Content-based book suggestion
                    System.out.println("\n\t< < < CONTENT-BASED BOOK SUGGESTION > > >");
                    ArrayList<Book> suggestBook = suggestTool.showContentSuggest(engine.getCurrentUser(),engine.getBookCollection());
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "3":   // user choose to show Community-based book suggestion
                    System.out.println("\n\t< < < COMMUNITY-BASED BOOK SUGGESTION > > >");
                    suggestBook = suggestTool.showCommuSuggest(engine.getCurrentUser());
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "4":   // user choose to search book
                    System.out.println("\n\t< < < SEARCH BOOK > > >");
                    String keyword = IOUtils.getString("Please enter book keyword or press enter to back:");
                    if(keyword.charAt(0) == '\n')
                    {
                        break;
                    }

                    ArrayList<Book> foundBooks = engine.searchBook(keyword);
                    if(foundBooks != null)
                    {
                        printBookList(foundBooks);
                    }
                    break;
                case "5":   // user choose to view profile
                    System.out.println("\n\t< < < PROFILE > > >");
                    showProfilePage();
                    break;
                case "6":   // user choose to log out
                    engine.saveUserDataFile();
                    System.out.println("\tSaving success!");
                    engine.logout();
                    System.out.println("\tLogout success!");
                    return;
                case "exit":    // user choose to exit (input exit)
                    exit();
                    break;
                default:        // user input others
                    System.out.println("\tplease try again");
                    break;
            }
        }
    }

    /**
     *      display profile page that show information of account
     */
    private static void showProfilePage()
    {
        Account user = engine.getCurrentUser();
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        ArrayList<History> purchased = user.getCustomer().getPurchasedHistory();

        do {

            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("History: ");
            if (purchased.size() == 0)      // if user hasn't purchased book before
            {
                System.out.println("\t-");
            }
            else
            {
                for (int i = 0; i < purchased.size(); i++)  // display purchased books history
                {
                    System.out.println("   - " + purchased.get(i).toString());
                }
            }

            System.out.printf("\n\tType Y to edit profile or type back.. ");
            String answer = IOUtils.getBareString();

            if (answer.equalsIgnoreCase("Y"))            // if user want to edit profile
            {
                showEditProfilePage(user);
            }
            else if (answer.equalsIgnoreCase("back"))    // if user want to back to menu
            {
                return;
            }
            System.out.println();
        }
        while (true);
    }

    /**
     *      display edit profile page for editing user's profile
     *
     * @param user
     */
    private static void showEditProfilePage(Account user)
    {
        String confirmStatus;
        String password;
        String name;
        String surname;

        do
        {
            System.out.println("\n--------------- E D I T  P R O F I L E ---------------");
            System.out.println("** press enter if you don't want to change that field.");
            System.out.printf("New password: ");
            password = IOUtils.getBareString();
            System.out.printf("New Name: ");
            name = IOUtils.getBareString();
            System.out.printf("New Surname: ");
            surname = IOUtils.getBareString();

             System.out.printf("\n\tType Y to confirm registration or type cancel to go to menu.. ");
             confirmStatus = IOUtils.getBareString();
         }
        while (!confirmStatus.equalsIgnoreCase("Y") && !confirmStatus.equalsIgnoreCase("cancel"));

        if (confirmStatus.equalsIgnoreCase("cancel"))       // if user want to cancel editing
        {
            return;
        }

        if(password.charAt(0) != '\n' && password.charAt(0) != ' ')     // user doesn't change password
        {
            user.setPassword(password);
        }

        if(name.charAt(0) != '\n' && password.charAt(0) != ' ')         // user doesn't change name
        {
            user.setName(name);
        }

        if(surname.charAt(0) != '\n' && password.charAt(0) != ' ')      // user doesn't change surname
        {
            user.setSurname(surname);
        }

    }

    /**
     *      display list of book titles with index for choosing to view book details
     *
     * @param books which mean list of books that want to be displayed
     */
    private static void printTitles(ArrayList<Book> books)
    {
        Iterator<Book> bookIterator = books.iterator();
        int i = 1;

        do
        {
            Book book = bookIterator.next();
            System.out.println(i + ". " + book.getTitle());
            i++;
        }
        while (bookIterator.hasNext());
    }

    /**
     *      display list of books and ask for buying confirmation
     *
     * @param books
     */
    private static void printBookList(ArrayList<Book> books)
    {
        do
        {
            int bookNumber;
            if (books != null)      // if list of books  is not empty, display the list
            {
                System.out.println();
                printTitles(books);
            }

            System.out.printf("\n\tchoose book number to view book details or type back.. ");
            String bookNumberString = IOUtils.getBareString();

            if (bookNumberString.equalsIgnoreCase("back"))      // user want to back to menu
            {
                return;
            }

            try
            {
                bookNumber = Integer.parseInt(bookNumberString);        // parse input string to integer
            }
            catch (NumberFormatException | NullPointerException e)      // string can't be parsed
            {
                System.out.println("\tERROR - please choose book number again\n");
                continue;
            }

            if (bookNumber < 1 || bookNumber > books.size())            // book number is out of range
            {
                System.out.println("\tERROR - this book number out of range\n");
                continue;
            }
            else
            {
                Book selectedBook = engine.getSelectedBook(books, bookNumber);      // get book at book number position
                if (selectedBook != null)           // if book can be got
                {
                    System.out.println(selectedBook);
                    System.out.printf("\ttype Y to buy this book or other key to back.. ");
                    String buyingStatus = IOUtils.getBareString();
                    if (buyingStatus.equalsIgnoreCase("y"))     // user want to buy the book
                    {
                        if (engine.buyBook(selectedBook.getTitle()))        // buy the book by calling buyBook() method from Engine Class
                        {
                            System.out.println("\n\tBuying successfully!");
                            return;
                        }
                    }
                }
                else
                {
                    System.out.println("ERROR - cannot print book list");
                    return;
                }
            }
        }
        while (true);
    }

    /**
     *      save user data and close the program
     */
    private static void exit()
    {
        System.out.printf("\n\tType Y to close program or other key to cancel.. ");
        String answer = IOUtils.getBareString();

        // if user want to exit
        if (answer.equalsIgnoreCase("Y"))
        {
            engine.saveUserDataFile();
            System.out.println("\tSaving success!");
            System.exit(0);
        }
        return;
    }

    
}
