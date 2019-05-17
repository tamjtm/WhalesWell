import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

public class EngineUI
{
    private static Engine engine;

    public EngineUI()
    {
        engine = new Engine();
    }

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

            System.out.printf("\nType Y to confirm registration.. ");
            confirmStatus = IOUtils.getBareString();
        }
        while (!confirmStatus.equalsIgnoreCase("Y"));

        boolean bRegister = engine.register(username, password, name, surname);
        if (bRegister)
        {
            // if register success, go to login page
            System.out.println("\tRegistration success!");
        }
        else
        {
            System.out.println("\tERROR - register failed");
        }
    }

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

                System.out.printf("\nType Y to confirm registration.. ");
                confirmStatus = IOUtils.getBareString();
            }
            while (!confirmStatus.equalsIgnoreCase("Y"));

            boolean bRegister = engine.register(username, password, name, surname);
            if (bRegister)
            {
                // if register success, go to login page
                System.out.println("\tRegistration success!");
                return;
            }
        }
    }

    private static void showLoginPage()
    {
        System.out.println("\n------------------ L O G I N ------------------");
        System.out.printf("Username: ");
        String username = IOUtils.getBareString();

        if (engine.isUsernameExist(username))   // no this username exist
        {
            System.out.printf("Password: ");
            String password = IOUtils.getBareString();

            int loginStatus = engine.login(username, password);
            if (loginStatus == 1)       // login success
            {
                System.out.println("\tLogin success!");
                showMenuPage();
            }
            else if(loginStatus == 0)
            {
                System.out.printf("\nThis username is your account?. Type Y to login again.. ");
                String tryingStatus = IOUtils.getBareString();
                if (tryingStatus.equalsIgnoreCase("Y"))
                {
                    // go to login page
                    return;
                }
                else
                {
                    showRegisterPage();
                }
            }
            else
            {
                // if login fail, go to login page
                return;
            }
        }
        else
        {
            System.out.printf("\nThis username does not exist. Type Y to register.. ");
            String registerStatus = IOUtils.getBareString();

            // if user want to register
            if(registerStatus.equalsIgnoreCase("Y"))
            {
                showRegisterPage(username);
            }
            else
            {
                // if user doesn't want to register, go to login page
                return;
            }
        }
    }

    private static void showMenuPage()
    {
        while (true)
        {
            System.out.println("\n------------------- M E N U -------------------");
            System.out.println("  1. Show all books");
            System.out.println("  2. Show content-based book suggestion");
            System.out.println("  3. Show community-based book suggestion");
            System.out.println("  4. Search book");
            System.out.println("  5. Buy book");
            System.out.println("  6. View profile");
            System.out.println("  7. Log out");
            System.out.println("-------------------------------------------------");

            System.out.printf("Choose your command number or type exit to close program.. ");
            String command = IOUtils.getBareString();

            switch (command) {
                case "1":   // show all books
                    ArrayList<Book> allBooks = engine.getAllBooks();
                    printBookList(allBooks);
                    break;
                case "2":   // show Content-based book suggestion
                    ArrayList<Book> suggestBook = engine.showContentSuggest();
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "3":   // show Community-based book suggestion
                    suggestBook = engine.showCommuSuggest();
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "4":   // search book
                    String keyword = IOUtils.getString("Please enter book keyword :");
                    ArrayList<Book> foundBooks = engine.searchBook(keyword);
                    if(foundBooks != null)
                    {
                        printBookList(foundBooks);
                    }
                    break;
                case "5":   // buy book
                    String title = IOUtils.getString("\tPlease enter book title");
                    System.out.println(engine.getSelectedBook(title));
                    String response = IOUtils.getString("Confirm buying... [Y/N]");
                    if ((response.startsWith("Y")) || (response.startsWith("y")))
                    {
                        if(engine.buyBook(title))
                        {
                            System.out.println("\tBuying successfully!");
                        }
                    }
                    break;
                case "6":   // view profile
                    showProfilePage();
                    break;
                case "7":   // log out
                    engine.logout();
                    System.out.println("\tLogout success!");
                    return;
                case "exit":
                    System.out.printf("\n\tType Y to close program.. ");
                    String answer = IOUtils.getBareString();

                    // if user want to exit
                    if (answer.equalsIgnoreCase("Y"))
                    {
                        engine.saveUserDataFile();
                        System.out.println("\tSaving success!");
                        System.exit(0);
                    }
                    return;
                default:
                    System.out.println("\tplease try again");
                    break;
            }
        }
    }

    private static void showProfilePage()
    {
        Account user = engine.getCurrentUser();
        String username = user.getUsername();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        ArrayList<History> purchased = user.getCustomer().getPurchasedHistory();

        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("History: ");
        if(purchased.size()==0)
        {
            System.out.println("\t-");
        }
        else
        {
            for(int i=0; i<purchased.size();i++)
            {
                System.out.println("   - "+purchased.get(i).toString());
            }    
        }

        System.out.printf("\n\tType Y to edit profile.. ");
        String answer = IOUtils.getBareString();

        // if user want to edit profile
        if (answer.equalsIgnoreCase("Y"))
        {
            showEditProfilePage(user);
        }
        return;
    }

    private static void showEditProfilePage(Account user)
    {
        String confirmStatus;
        String password;
        String name;
        String surname;

        do
        {
            System.out.println("\n--------------- E D I T  P R O F I L E ---------------");
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

        if (confirmStatus.equalsIgnoreCase("cancel"))
        {
            return;
        }

        if(password.charAt(0) != '\n')
        {
            user.setPassword(password);
        }

        if(password.charAt(0) != '\n')
        {
            user.setName(name);
        }

        if(password.charAt(0) != '\n')
        {
            user.setSurname(surname);
        }

    }

    private static void printBooks(ArrayList<Book> books)
    {
        Iterator<Book> bookIterator = books.iterator();
        do
        {
            Book book = bookIterator.next();
            System.out.println(book);
        }
        while (bookIterator.hasNext());
    }

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

    private static void printBookList(ArrayList<Book> books)
    {

            if (books != null) {
                printTitles(books);
            }
            System.out.printf("\n\tchoose book number to view book details.. ");
            String bookNumber = IOUtils.getBareString();
            if ((bookNumber.charAt(0) == '\n' || (bookNumber.charAt(0) < 49 || bookNumber.charAt(0) > 57))) {
                return;
            } else {
                Book selectedBook = engine.getSelectedBook(books, Integer.parseInt(bookNumber));
                if (selectedBook != null) {
                    System.out.println(selectedBook);
                    System.out.printf("\ttype Y to buy this book or press enter to back.. ");
                    String buyingStatus = IOUtils.getBareString();
                    if (buyingStatus.equalsIgnoreCase("y")) {
                        if (engine.buyBook(selectedBook.getTitle())) {
                            System.out.println("\n\tBuying successfully!");
                        }
                    }
                } else {
                    System.out.println("ERROR - cannot print book list");
                    return;
                }
            }
        return;
    }

    public static void main(String args[])
    {
        EngineUI engineUI = new EngineUI();

        // loop until user login -> if user already login, logout
        while (engine.getCurrentUser() == null)
        {
            showLoginPage();
        }
    }
}
