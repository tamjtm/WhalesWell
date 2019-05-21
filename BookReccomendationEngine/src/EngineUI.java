import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;

public class EngineUI
{
    private static Engine engine;
    private static SuggestTool suggestTool;

    public EngineUI()
    {
        engine = engine.getEngineInstance();
        suggestTool = new SuggestTool();
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

                System.out.printf("\nType Y to confirm registration or other key to cancel.. ");
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
        System.out.println("** You can type register/exit too.\n");
        System.out.printf("Username: ");
        String username = IOUtils.getBareString();

        if(username.equalsIgnoreCase("register"))
        {
            showRegisterPage();
        }
        else if(username.equalsIgnoreCase("exit"))
        {
            exit();
            return;
        }
        else if (engine.isUsernameExist(username))   // no this username exist
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
                System.out.printf("\nThis username is your account? Type Y to login again or other key to cancel.. ");
                String tryingStatus = IOUtils.getBareString();
                if (tryingStatus.equalsIgnoreCase("Y"))
                {
                    // go to login page
                    return;
                }
                else
                {
                    return;
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
            System.out.printf("\nThis username does not exist. Type Y to register or other key to cancel.. ");
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
            System.out.println("  5. View profile");
            System.out.println("  6. Log out");
            System.out.println("-------------------------------------------------");

            System.out.printf("Choose your command number or type exit.. ");
            String command = IOUtils.getBareString();

            switch (command) {
                case "1":   // show all books
                    System.out.println("\n\t < < ALL BOOK > > >");
                    ArrayList<Book> allBooks = engine.getAllBooks();
                    printBookList(allBooks);
                    break;
                case "2":   // show Content-based book suggestion
                    System.out.println("\n\t< < < CONTENT-BASED BOOK SUGGESTION > > >");
                    ArrayList<Book> suggestBook = suggestTool.showContentSuggest(engine.getCurrentUser(),engine.getBookCollection());
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "3":   // show Community-based book suggestion
                    System.out.println("\n\t< < < COMMUNITY-BASED BOOK SUGGESTION > > >");
                    suggestBook = suggestTool.showCommuSuggest(engine.getCurrentUser());
                    if(suggestBook != null)
                    {
                        printBookList(suggestBook);
                    }
                    break;
                case "4":   // search book
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
                case "5":   // view profile
                    System.out.println("\n\t< < < PROFILE > > >");
                    showProfilePage();
                    break;
                case "6":   // log out
                    engine.saveUserDataFile();
                    System.out.println("\tSaving success!");
                    engine.logout();
                    System.out.println("\tLogout success!");
                    return;
                case "exit":
                    exit();
                    break;
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

        do {

            System.out.println("Name: " + name);
            System.out.println("Surname: " + surname);
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            System.out.println("History: ");
            if (purchased.size() == 0) {
                System.out.println("\t-");
            } else {
                for (int i = 0; i < purchased.size(); i++) {
                    System.out.println("   - " + purchased.get(i).toString());
                }
            }

            System.out.printf("\n\tType Y to edit profile or type back.. ");
            String answer = IOUtils.getBareString();

            // if user want to edit profile
            if (answer.equalsIgnoreCase("Y"))
            {
                showEditProfilePage(user);
            }
            else if (answer.equalsIgnoreCase("back"))
            {
                return;
            }
            System.out.println();
        }
        while (true);
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

        if (confirmStatus.equalsIgnoreCase("cancel"))
        {
            return;
        }

        if(password.charAt(0) != '\n' && password.charAt(0) != ' ')
        {
            user.setPassword(password);
        }

        if(name.charAt(0) != '\n' && password.charAt(0) != ' ')
        {
            user.setName(name);
        }

        if(surname.charAt(0) != '\n' && password.charAt(0) != ' ')
        {
            user.setSurname(surname);
        }

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
        do {

            int bookNumber;
            if (books != null)
            {
                System.out.println();
                printTitles(books);
            }

            System.out.printf("\n\tchoose book number to view book details or type back.. ");
            String bookNumberString = IOUtils.getBareString();

            if (bookNumberString.equalsIgnoreCase("back"))
            {
                return;
            }

            try
            {
                bookNumber = Integer.parseInt(bookNumberString);
            }
            catch (NumberFormatException | NullPointerException e)
            {
                System.out.println("\tERROR - please choose book number again\n");
                continue;
            }

            if (bookNumber < 1 || bookNumber > books.size())
            {
                System.out.println("\tERROR - this book number out of range\n");
                continue;
            }
            else
            {
                Book selectedBook = engine.getSelectedBook(books, bookNumber);
                if (selectedBook != null)
                {
                    System.out.println(selectedBook);
                    System.out.printf("\ttype Y to buy this book or other key to back.. ");
                    String buyingStatus = IOUtils.getBareString();
                    if (buyingStatus.equalsIgnoreCase("y"))
                    {
                        if (engine.buyBook(selectedBook.getTitle()))
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
