import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import static java.lang.Integer.parseInt;

public class UserFileManager extends TextFileReader
{
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
                fields = line.split(",");
                Account newAccount = new Account(fields[0], fields[1], fields[2], fields[3]);
                strHistory = fields[4];
            }
        }
        while ((strHistory == null) && (line != null));
        return fields;
    }
    
    public void loadAccount()
    {
        Hashtable<String,Account> accounts = Account.getAccountCollection();
        Hashtable<String,Book> bookShelf = Book.getBookCollection();

        if(!open("UserData.txt"))
        {
            System.out.println("FAIL!\n\n");
            System.exit(1);
        }

        String customerRecord[] = loadUserData();
        while (customerRecord != null)
        {
            Account account = accounts.get(customerRecord[0]);
            Customer customer = account.getCustomer();
            String strHistory[] = customerRecord[4].split(";");
            for(int i=0;i<strHistory.length;i++)
            {
                String subHistory[] = strHistory[i].split("\t");
                Book purchasedBook = bookShelf.get(subHistory[1]);
                purchasedBook.addPurchaser(account);
                History history = new History(purchasedBook);
                history.loadHistory(subHistory[0], purchasedBook);
                customer.loadPurchasedHistory(history);
            }
            customerRecord = loadUserData();
        }
        close();
    }


    public boolean saveUser()
    {
        try
        {
            File file = new  File("UserData.txt");

            if (!file.exists())
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
                bw.write(account.getUsername()+","+account.getPassword()+","+
                        account.getName()+","+account.getSurname()+",");
                ArrayList<History> histories = account.getCustomer().getPurchasedHistory();
                for(int i=0;i<histories.size()-1;i++)
                {
                    bw.write(histories.get(i).toString()+";");
                }
                bw.write(histories.get(histories.size()-1).toString());
                bw.write("\n");
            }

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}