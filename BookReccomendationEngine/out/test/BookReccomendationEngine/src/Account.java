import java.util.ArrayList;
import java.util.Hashtable;

public class Account
{
    private String username;
    private String password;
    private String name;
    private String surname;
    private Customer customer;
    static private Hashtable<String,Account> accountCollection = new Hashtable<String, Account>();

    public Account(String username, String password, String name, String surname)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        customer = new Customer();
        accountCollection.put(username, this);
    }

    public String getPassword()
    {
        return password;
    }

    public static Hashtable<String, Account> getAccountCollection()
    {
        return accountCollection;
    }

    public void login()
    {
        customer.setLoginStatus(true);
    }

    public void logout()
    {
        customer.setLoginStatus(false);
    }

    @Override
    public String toString() {
        return name + " " + surname + " (@" + username + ")";
    }
}
