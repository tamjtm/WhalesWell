# WhalesWell
CPE343 OOD Team Project : Book Recommendation Engine

1st upload : BookReccomendationEngine Folder, Sequence-Diagram-WW.xml, whaleswell_uc.xml

2nd upload : update class diagram (Book, Engine, Account, Customer) **- accountCollection : Hashtable<String, Account> must be static

3rd upload : update class diagram (Book) **- bookCollection : Hashtable<String, Book> must be static

4th upload : update class diagram
  Edit 'Book' class, getSearchBooks return ArrayList<Book>
  Edit 'Book' class. add method 'getBookCollection'
  Edit 'Engine' class. 'printSelectedBook' and 'buyBook' change parameter to (title:String)

5th upload : update sequence diagram - Buy Book

6th upload : update class diagram 
  Edit 'Book' class, change 'purchaser' datatype
  Edit 'Account' class, add 'getCustomer' method

7th upload : update code
  Add EngineUI Class (contain main() method) for display UI to user
  now user can use menu, login, logout, and register without checking any string format
  
8th upload : update code
  user can buy book, view content suggestion (haven't limit book amount of suggest yet)
  
9th upload : update code and class diagram
  Add searchBook() method in Engine Class and printBooks() method in EngineUI Class
  Edit menu to search book
  Update BookDB.txt (change all keyword to upper case)
  Add EngineUI Class and edit Engine Class for class diagram

10th upload: update class diagram
  Edit 'Book' class, change 'addPurchaser' parameter
  Edit 'Book' class, change 'getPurchaser' return datatype
