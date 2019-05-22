import java.util.ArrayList;

/**
 *
 * Customer
 *
 *   This class represents a book in Book Recommendation Engine.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     8 May 2019
 *
 */

public class Customer
{
    /** login status of account */
    private boolean loginStatus;

    /** purchased book history collection of account */
    private ArrayList<History> purchasedHistory = new ArrayList<History>();

    /**
     *      construct customer instance and initialize login status
     */
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

    /**
     *      set log in status of account
     *
     * @param loginStatus
     */
    public void setLoginStatus(boolean loginStatus)
    {
        this.loginStatus = loginStatus;
    }

    /**
     *      get purchased book history from collection
     *
     * @return  collection of history
     */
    public ArrayList<History> getPurchasedHistory()
    {
        return purchasedHistory;
    }

    /**
     *      add purchased book history to collection
     * @param   history
     * @return  true if adding is success
     */
    public boolean loadPurchasedHistory(History history)
    {
        purchasedHistory.add(history);
        return true;
    }

    /**
     *      add new purchased book to purchased book history
     *
     * @param   purchasedBook
     * @return  true if adding is success
     */
    public boolean addPurchasedHistory(Book purchasedBook)
    {
        History newPurchased = new History(purchasedBook);
        purchasedHistory.add(newPurchased);
        return true;
    }
}
