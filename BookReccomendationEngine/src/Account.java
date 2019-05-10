import java.util.ArrayList;
import java.util.Hashtable;

public class Account
{
    private String username;
    private String password;
    private String name;
    private String surname;
    private Customer customer;
    static private Hashtable<String,Account> accountCollection = new Hashtable<String,Account>();

    public Account(String username, String password, String name, String surname)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        customer = new Customer();
        accountCollection.put(username, this);
    }

    public String getUsername()
    {
        return username;
    }
    
    public String getPassword()
    {
        return password;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public static Hashtable<String, Account> getAccountCollection()
    {
        return accountCollection;
    }

    public Customer getCustomer()
    {
        return customer;
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
