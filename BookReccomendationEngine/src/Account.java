import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * Account
 *
 *   This class represents an user account that has registered
 *      in Book Recommendation Engine.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     8 May 2019
 *
 */
public class Account
{
    /** username of this account */
    private String username;

    /** password of this account */
    private String password;

    /** name of this account */
    private String name;

    /** surname of this account */
    private String surname;

    /** customer instance of this account */
    private Customer customer;

    /** collection of all account */
    private static Hashtable<String,Account> accountCollection = new Hashtable<String,Account>();

    /**
     *      construct an Account Instance and initialize all member.
     *  Then, add it to account collection.
     *
     * @param username
     * @param password
     * @param name
     * @param surname
     */
    public Account(String username, String password, String name, String surname)
    {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        customer = new Customer();
        accountCollection.put(username, this);
    }

    /**
     *      get username member
     *
     * @return username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     *      get password member
     *
     * @return password
     */
    public String getPassword()
    {
        return password;
    }  

    /**
     *      get name member
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     *      get surname member
     *
     * @return surname
     */
    public String getSurname()
    {
        return surname;
    }

    /**
     *      get customer object member
     *
     * @return username
     */
    public Customer getCustomer()
    {
        return customer;
    }

    /**
     *      get account collection member
     *
     * @return account collection
     */
    public static Hashtable<String, Account> getAccountCollection()
    {
        return accountCollection;
    }

    /**
     *      set password by passing new password
     *
     * @param password new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     *      set name by passing new name
     *
     * @param name new name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     *      set surname by passing new surname
     *
     * @param surname new surname
     */
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    
    /**
     *      log in by changing new log in status in customer instance to true
     */
    public void login()
    {
        customer.setLoginStatus(true);
    }

    /**
     *      log out by changing new log in status in customer instance to false
     */
    public void logout()
    {
        customer.setLoginStatus(false);
    }

    /**
     *      used for display Account Class information
     * @return string of Account Class
     */
    @Override
    public String toString() 
    {
        return name + " " + surname + " (@" + username + ")";
    }
}
