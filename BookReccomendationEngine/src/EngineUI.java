
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
            System.out.println("  4. Select book");
            System.out.println("  5. Buy book");
            System.out.println("-------------------------------------------------");

            System.out.printf("Choose your command number or others to logout.. ");
            String command = IOUtils.getBareString();

            switch (command) {
                case "1":
                    engine.printAll();
                    break;
                case "2":
                    System.out.println("\nContent-based book suggestion");
                    engine.showContentSuggest();
                    break;
                case "3":
                    System.out.println("\nCommunity-based book suggestion");
                    break;
                case "4":
                    String title = IOUtils.getString("Please enter book title");
                    if(engine.printSelectedBook(title))
                    {
                        System.out.println("Back to Menu..");
                    }
                    break;
                case "5":
                    if(engine.buyBook())
                    {
                        System.out.println("Buying successfully");
                    }
                    break;
                default:
                    engine.logout();
                    return;
            }
        }
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
