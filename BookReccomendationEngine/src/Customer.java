import java.util.ArrayList;

public class Customer
{
    private boolean loginStatus;
    private ArrayList<History> purchasedHistory = new ArrayList<History>();

    public Customer()
    {
        loginStatus = false;
    }
/*
    public boolean getLoginStatus()
    {
        return loginStatus;
    }
*/
    public void setLoginStatus(boolean loginStatus)
    {
        this.loginStatus = loginStatus;
    }


    public ArrayList<History> getPurchasedHistory()
    {
        return purchasedHistory;
    }

    public boolean loadPurchasedHistory(History history)
    {
        purchasedHistory.add(history);
        return true;
    }

    public boolean addPurchasedHistory(Book purchasedBook)
    {
        History newPurchased = new History(purchasedBook);
        purchasedHistory.add(newPurchased);
        return true;
    }
}
