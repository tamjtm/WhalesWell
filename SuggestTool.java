import java.lang.Math;
import java.util.*;

/**
 *
 * SuggestTool
 *
 *   This class represents a suggestion part of Book Recommendation Engine.
 *
 *   Created by Nutaya Pravalphreukul (Pear)  ID 59070501032
 *     9 May 2019
 *
 */
public class SuggestTool
{
    /** list of suggested book */
    private ArrayList<Book> suggestedBooks = new ArrayList<Book>();

    /**
     *      calculate suggest point up to priority for suggestion
     *
     * @param currentBook
     * @param latestBook
     * @return  suggest point
     */
    private Integer countMatchKeyword(Book currentBook,Book latestBook)
    {
        ArrayList<String> currentBookKey = currentBook.getKeyword();        // get keyword from current book
        ArrayList<String> latestBookKey = latestBook.getKeyword();          // get keyword from latest purchased book
        int counter = 0;            // suggest point

        for(int i=0;i<currentBookKey.size();i++)        // for each keyword in current book
        {
            for(int j=0;j<latestBookKey.size();j++)     // for each keyword in latest book
            {
                if(currentBookKey.get(i).compareTo(latestBookKey.get(j))==0)        // if they have same keyword
                {
                    counter = counter+2;        // high priority suggest point
                    break;
                }
            }
        }

        int difPrice = currentBook.getPrice()-latestBook.getPrice();
        int difLength = currentBook.getLength()-latestBook.getLength();
        
        if(Math.abs(difPrice)<=50)      // they have less than 50 price difference
        {
            counter++;      // low priority suggest point
        }
        if(Math.abs(difLength)<=50)     // they have less than 50 length difference
        {
            counter++;      // // low priority suggest point
        }

        return counter;
    }

    /**
     *      sort list of books up to suggest points
     *
     * @param latestBook
     * @return  sorted list of books
     */
    private ArrayList<Book> sortBooks(Book latestBook)
    {
        Hashtable<Book,Integer> suggestPoint = new Hashtable<Book,Integer>();       // book and suggest point table
        ArrayList<Book> sortedBooks = new ArrayList<Book>();                // sorted list of books

        for(int i = 0; i < suggestedBooks.size(); i++)        // for each book in the list
        {
            int point = countMatchKeyword(suggestedBooks.get(i), latestBook);   // calculate suggest point
            suggestPoint.put(suggestedBooks.get(i), point);     // collect point into table
        }

        //Transfer as List and sort it
        ArrayList<Map.Entry<Book, Integer>> bookList = new ArrayList<Map.Entry<Book, Integer>>(suggestPoint.entrySet());
        Collections.sort(bookList, new Comparator<Map.Entry<Book, Integer>>()
        {
            public int compare(Map.Entry<Book, Integer> o1, Map.Entry<Book, Integer> o2) 
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
 
        for(int j = 0; j < bookList.size(); j++)        // for each book in the sorted list
        {
            sortedBooks.add(bookList.get(j).getKey());      // add book to new sorted list
        }

        return sortedBooks;
     }

    /**
     *      suggest 5 books for user with content-based calculated process
     *
     * @param currentUser
     * @param bookCollection
     * @return  suggested book list
     */
    public ArrayList<Book> showContentSuggest(Account currentUser,Hashtable<String,ArrayList<Book>> bookCollection)
    {
        // Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        ArrayList<Book> tempBook = new ArrayList<Book>();


        // Haven't bought any book before
        if(customerHistory.size() == 0)
        {
            System.out.println("--- Not found book reference. Please buy any book.");
            return null;
        }
        else
        {
            Book latestBook = customerHistory.get(customerHistory.size()-1).getBook();      //Get latest book
            System.out.println("Reference on: \n" + latestBook);
            System.out.println("+ + + + + + + + + + + + + + + + + + + +");

            for(int i = 1; i < latestBook.getKeyword().size(); i++)       // for each keyword
            {
                String keyword = latestBook.getKeyword().get(i);    // get keyword
                if(bookCollection.containsKey(keyword))     // if there is this keyword in book collection
                {
                    tempBook = bookCollection.get(keyword); // get book list that have this keyword
                    if(tempBook.size() > 1)
                   {
                        for(int j = 0; j < tempBook.size(); j++)        // for each book in book list
                        {
                            if(!suggestedBooks.contains(tempBook.get(j)))   // if suggested book list doesn't have this book
                            {
                                suggestedBooks.add(tempBook.get(j));        // add this book to suggested book list
                            }
                        }
                    }
                }
            }

            for(int i = 0; i < customerHistory.size(); i++)       // for each history in customer's history
            {
                if(suggestedBooks.contains(customerHistory.get(i).getBook()))       // if customer has purchased this book before
                {
                    suggestedBooks.remove(customerHistory.get(i).getBook());        // remove this book from suggested book list
                }
            }

            suggestedBooks = sortBooks(latestBook);
            if(suggestedBooks.size() > 5)       // if there are more than 5 books in the suggested book list
            {
                while((suggestedBooks.size() - 5) > 0)  // remove books from the list from tail until there are 5 book in the list
                {
                    suggestedBooks.remove(suggestedBooks.size() - 1);
                }
            }
            return suggestedBooks;
        }
    }

    /**
     *      suggest 5 books for user with community-based calculated process
     *
     * @param currentUser
     * @return  suggested book list
     */
    public ArrayList<Book> showCommuSuggest(Account currentUser)
    {
        //Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        
        //Haven't bought any book before  
        if(customerHistory.size() == 0)
        {
            System.out.println("--- Not found book reference. Please buy any book.");
            return null;
        }
        else
        {
            //Get latest book
            Book latestBook = customerHistory.get(customerHistory.size()-1).getBook();
            ArrayList<Account> purchaser = latestBook.getPurchaser();
            purchaser.remove(currentUser);
            System.out.println("Reference on: \n"+latestBook);
            System.out.println("+ + + + + + + + + + + + + + + + + + + +");

            if(purchaser.size() == 0)       // this book hasn't purchased by other user
            {
                System.out.println("--- No user has bought this book before.");
                return null;
            }
            else
            {
                for(int i = 0; i < purchaser.size(); i++)       // for each account in purchaser list
                {
                    Customer otherCustomer = purchaser.get(i).getCustomer();
                    ArrayList<History> otherHistory = otherCustomer.getPurchasedHistory();      // get other book purchased history list
                    if(otherHistory.size() > 1)
                    {
                        for(int j = 0; j < otherHistory.size(); j++)        // for each history in other history list
                        {
                            Book temp = otherHistory.get(j).getBook();      // get book from other history list
                            if(!suggestedBooks.contains(temp))      // if there isn't this book in suggested books list
                            {
                                suggestedBooks.add(temp);       // add this book to suggest books list
                            }
                        }
                    }
                }

                for(int i = 0; i < customerHistory.size(); i++)     // for each history in current user's book purchased history list
                {
                    if(suggestedBooks.contains(customerHistory.get(i).getBook()))       // if there is this book in ther list
                    {
                        suggestedBooks.remove(customerHistory.get(i).getBook());        // remove this book
                    }
                }

                if(suggestedBooks.size() == 0)      // if there is no book to suggest
                {
                    System.out.println("--- Other purchaser(s) haven't bought other book.");
                    return null;
                }

                Collections.sort(suggestedBooks, new Comparator<Book>()     // comparator class
                {
                    public int compare(Book b1, Book b2) 
                    {
                        return b2.getPurchaseAmount() - b1.getPurchaseAmount();
                    }
                });

                if(suggestedBooks.size() > 5)     // if there are more than 5 books in the suggested book list
                {
                    while((suggestedBooks.size() - 5) > 0)      // remove books from the list from tail until there are 5 book in the list
                    {
                        suggestedBooks.remove(suggestedBooks.size()-1);
                    }
                }
                return suggestedBooks;
            }
        }
    }
}