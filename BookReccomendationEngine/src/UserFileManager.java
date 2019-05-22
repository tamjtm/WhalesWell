import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import static java.lang.Integer.parseInt;

/**
 * UserFileManager
 * 
 *   Class to manage information about user data from a file
 *      including read from file write to file
 *      so this class can create user data and save them.
 *
 *   Each line of the file has the following structure
 *
 *      b,b,b,b,0154-05-10 23:53:25.865	THE MISTER;0154-05-10 23:53:34.462	UGLY LOVE
 *
 *   First field is username
 *   Second field is password
 *   Third field is name
 *   Fourth field is surname
 *   Fifth field is purchased history
 * 
 *   Each field is splitted by comma.
 *
 *   Created by Nutaya Pravalphreukul (Pear)  ID 59070501032
 *     12 May 2019
 *
 */
public class UserFileManager extends TextFileReader
{
    /**
     *      initialize account by using the first four fields      
     * 
     * @return all fields that were splitted
     */
    private String[] loadUserData()
    {
        String line;
        String fields[] = null;
        String strHistory = null;
        do
        {
            line = getNextLine();
            if(line != null)
            {
                fields = line.split(",");       // split fields by comma
                Account newAccount = new Account(fields[0], fields[1], fields[2], fields[3]);
                strHistory = fields[4];
            }
        }
        while ((strHistory == null) && (line != null));     // if there is line or (string of) history left 
        return fields;
    }
 
    /**
     *     
     *      load user information from UserData.txt file
     *  and add all account information to each class in system
     */
    public void loadAccount()
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();
        Hashtable<String,Book> bookShelf = Book.getBookCollection();

        if(!open("UserData.txt"))
        {
            System.out.println("FAIL!\n\n");
            System.exit(1);
        }

        String customerRecord[] = loadUserData();               // get all splitted field from each line
        while (customerRecord != null)
        {
            Account account = accounts.get(customerRecord[0]);          // get account from username
            Customer customer = account.getCustomer();                  // get customer object from account
            String strHistory[] = customerRecord[4].split(";");         // split pack of history by semi-colon
            for(int i=0;i<strHistory.length;i++)
            {
                String subHistory[] = strHistory[i].split("\t");        // split timestamp and book by '/t'
                Book purchasedBook = bookShelf.get(subHistory[1]);      // get book from title(second string)
                purchasedBook.addPurchaser(account);                    // add account as purchaser of this book
                History history = new History(purchasedBook);           // initial history from book instance
                history.loadHistory(subHistory[0], purchasedBook);      // load timestamp to the history
                customer.loadPurchasedHistory(history);                 // add to customer's purchased histories collection
            }
            customerRecord = loadUserData();
        }
        close();
    }

    /**
     *      save all user data into UserData.txt file
     * 
     * @return true if saving is success
     */
    public boolean saveUser()
    {
        try
        {
            File file = new  File("UserData.txt");

            if (!file.exists())         // if there is no this file
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            Hashtable<String,Account> accounts = Account.getAccountCollection();
            Set<String> keys = accounts.keySet();
            for(String key: keys)
            {
                Account account = accounts.get(key);
                bw.write(account.getUsername()+","+account.getPassword()+","+      // write account profile detail
                        account.getName()+","+account.getSurname()+",");
                ArrayList<History> histories = account.getCustomer().getPurchasedHistory();
                for(int i=0;i<histories.size()-1;i++)
                {
                    bw.write(histories.get(i).toString()+";");                     // write each purchased history detail
                }
                bw.write(histories.get(histories.size()-1).toString());
                bw.write("\n");
            }

            bw.close();

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return true;
    }
}