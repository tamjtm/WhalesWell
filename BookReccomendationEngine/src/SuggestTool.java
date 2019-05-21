import java.lang.Math;
import java.util.*;

public class SuggestTool
{
    private ArrayList<Book> suggestedBooks = new ArrayList<Book>();

    private Integer countMatchKeyword(Book currentBook,Book latestBook)
    {
        ArrayList<String> currentBookKey = currentBook.getKeyword();
        ArrayList<String> latestBookKey = latestBook.getKeyword();
        int counter=0;

        for(int i=0;i<currentBookKey.size();i++)
        {
            for(int j=0;j<latestBookKey.size();j++)
            {
                if(currentBookKey.get(i).compareTo(latestBookKey.get(j))==0)
                {
                    counter=counter+2;
                    break;
                }
            }
        }

        int difPrice = currentBook.getPrice()-latestBook.getPrice();
        int difLength = currentBook.getLength()-latestBook.getLength();
        
        if(Math.abs(difPrice)<=50)
        {
            counter++;
        }
        if(Math.abs(difLength)<=50)
        {
            counter++;
        }

        return counter;
    }

    private ArrayList<Book> sortBooks(Book latestBook)
    {
        Hashtable<Book,Integer> suggestPoint = new Hashtable<Book,Integer>();
        ArrayList<Book> sortedBooks = new ArrayList<Book>();

        for(int i=0;i<suggestedBooks.size();i++)
        {
            int point = countMatchKeyword(suggestedBooks.get(i), latestBook);
            suggestPoint.put(suggestedBooks.get(i), point);
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
 
        for(int j=0;j<bookList.size();j++)
        {
            sortedBooks.add(bookList.get(j).getKey());
        }

        return sortedBooks;
     }

    public ArrayList<Book> showContentSuggest(Account currentUser,Hashtable<String,ArrayList<Book>> bookCollection)
    {
        //Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        ArrayList<Book> tempBook = new ArrayList<Book>();


        //Haven't bought any book before  
        if(customerHistory.size()==0)
        {
            System.out.println("--- Not found book reference. Please buy any book.");
            return null;
        }
        else
        {
            //Get latest book
            Book latestBook = customerHistory.get(customerHistory.size()-1).getBook();
            System.out.println("Reference on: \n"+latestBook);
            System.out.println("+ + + + + + + + + + + + + + + + + + + +");

            for(int i=1; i<latestBook.getKeyword().size(); i++)
            {
                String keyword = latestBook.getKeyword().get(i);
                if(bookCollection.containsKey(keyword))
                {
                    tempBook = bookCollection.get(keyword);
                    if(tempBook.size()>1)
                   {
                        for(int j=0;j<tempBook.size();j++)
                        {
                            if(!suggestedBooks.contains(tempBook.get(j)))
                            {
                                suggestedBooks.add(tempBook.get(j));
                            }
                        }
                    }
                }
            }
            for(int i=0;i<customerHistory.size();i++)
            {
                if(suggestedBooks.contains(customerHistory.get(i).getBook()))
                {
                    suggestedBooks.remove(customerHistory.get(i).getBook());
                }
            }
            suggestedBooks = sortBooks(latestBook);
            if(suggestedBooks.size()>5)
            {
                while((suggestedBooks.size()-5)>0)
                {
                    suggestedBooks.remove(suggestedBooks.size()-1);
                }
            }
            return suggestedBooks;
        }
    }

    public ArrayList<Book> showCommuSuggest(Account currentUser)
    {
        //Find latest book from customer history
        Customer customer = currentUser.getCustomer();
        ArrayList<History> customerHistory = customer.getPurchasedHistory();
        
        //Haven't bought any book before  
        if(customerHistory.size()==0)
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
            if(purchaser.size()==0)
            {
                System.out.println("--- No user has bought this book before.");
                return null;
            }
            else
            {
                for(int i=0;i<purchaser.size();i++)
                {
                    Customer otherCustomer = purchaser.get(i).getCustomer();
                    ArrayList<History> otherHistory = otherCustomer.getPurchasedHistory();
                    if(otherHistory.size()>1)
                    {
                        for(int j=0;j<otherHistory.size();j++)
                        {
                            Book temp = otherHistory.get(j).getBook();
                            if(!suggestedBooks.contains(temp))
                            {
                                suggestedBooks.add(temp);
                            }
                        }
                    }
                }
                for(int i=0;i<customerHistory.size();i++)
                {
                    if(suggestedBooks.contains(customerHistory.get(i).getBook()))
                    {
                        suggestedBooks.remove(customerHistory.get(i).getBook());
                    }
                }
                if(suggestedBooks.size()==0)
                {
                    System.out.println("--- Other purchaser(s) haven't bought other book.");
                    return null;
                }

                Collections.sort(suggestedBooks, new Comparator<Book>()
                {
                    public int compare(Book b1, Book b2) 
                    {
                        return b2.getPurchaseAmount() - b1.getPurchaseAmount();
                    }
                });

                if(suggestedBooks.size()>5)
                {
                    while((suggestedBooks.size()-5)>0)
                    {
                        suggestedBooks.remove(suggestedBooks.size()-1);
                    }
                }
                return suggestedBooks;
            }
        }
    }
}