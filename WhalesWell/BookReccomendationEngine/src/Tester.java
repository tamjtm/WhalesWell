public class Tester
{
    public static void main(String args[])
    {
        Engine engine = new Engine();
        while (true)
        {
            System.out.println("Menu1   1.Register  2.Login  0.exit");
            int choose = IOUtils.getInteger("Select number: ");
                                            
            if(choose == 1)
            {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String usr = IOUtils.getString("Username: ");
                String pw = IOUtils.getString("PW: ");
                String name = IOUtils.getString("Name: ");   
                String sur = IOUtils.getString("Surname: ");
                if(engine.register(usr, pw, name, sur))
                {
                    System.out.println("Register Successful");
                }
            }
            else if(choose == 2)
            {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                String usr = IOUtils.getString("Username: ");
                String pw = IOUtils.getString("PW: ");
                if(engine.login(usr, pw)==1)
                {
                    System.out.println("Login Successful\n\n");
                    while(true)
                    {
                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                        System.out.println("Menu  1.Show All  2.Select Book  3.Logout");
                        choose = IOUtils.getInteger("Select number: ");
                        if (choose == 1)
                        {
                            engine.printAll();
                        }
                        else if(choose == 2)
                        {
                            String title = IOUtils.getString("Please enter book title");
                            if(engine.printSelectedBook(title))
                            {
                                String response = IOUtils.getString("Do you want to buy this book? [Y/N]");
                                if ((response.startsWith("Y")) || (response.startsWith("y")))
                                {
                                    engine.buyBook();
                                    System.out.println("Buying '"+title+"' successfully");
                                }
                            }
                            else
                            {
    
                            }
                        }
                        if(choose == 3)
                        {
                            engine.logout();
                            break;
                        }
                    }
                }
            }
            else if(choose==0)
            {
                System.out.println("Exiting...");
                break;
            }

        }

   
    }
}
