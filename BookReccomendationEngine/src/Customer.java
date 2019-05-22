import java.util.ArrayList;

/**
 *
 * Customer
 *
 *   This class represents a customer in Book Recommendation Engine
 *      for keep login status and purchased histories of account.
 *
 *   Created by Chanisa Phengphon (Chertam)  ID 59070501088
 *     8 May 2019
 *
 */
public class Customer
{
    /** login status of account: true when user's active and false when user's offline */
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
     *      add purchased book history to collection,
     *  use for loading account history to collection when initialize system.
     * 
     * @param   history
     * @return  true if adding is success
     */
    public boolean loadPurchasedHistory(History history)
    {
        purchasedHistory.add(history);
        return true;
    }

    /**
     *      add new purchased book to purchased book history,
     *  use when user buy new book.
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
