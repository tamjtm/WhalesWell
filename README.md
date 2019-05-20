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
  
11th upload: update code and sequence diagram
  Code: 'Engine' class, edit contentSuggest and add commuSuggest
  Sequence: Edit Content-based suggestion diagram

12th upload: update code
  Add showEditProfilePage() and showProfilePage() method in EngineUI (ไม่แก้โปรไฟล์ไม่ได้ ถึงไม่พิมพ์ก็จะเปลี่ยนให้อยู่ดี)
  Modify Account Class (add setter and getter method)

13th upload: update class diagram and sequence diagram
  Class: Edit 'Engine' class, change 'showContentSuggest','showCommuSuggest','buyBook' parameter and/or return datatype
  Sequence: Edit Community-based suggestion diagram
  
14th upload: update code
  Make 'suggest' methods to return (<)10 books in 'Engine' class
  Add 'saveUserDataFile()' method in 'Engine'
  Add 'UserData.txt' to record user's information
  Modify 'EngineUI' class to run exit and save file, and show user's history in 'View profile' page
  Cannot load user data file yet (/* in comment code of 'FileManager' and 'Engine' class */)
  
15th upload: update code and class diagram
  Class:  Edit 'FileManager' class, add methods
          Edit 'Engine' class, add 'saveUserDataFile' and 'loadAccount' methods
          Edit 'History' class, add 'loadHistory' method
          Edit 'Customer' class, add 'loadPurchasedHistory' method
  Code: Can save to and load data from 'UserData.txt' file
        Edit 'Engine' class, create loadAccount() method and add to initialize()
        Edit 'FileManager' class, add 'loadUserData' method to create account and get data from file 
        Other edit, can see in class diagram above.

16th upload: update code
  Code: Modify editProfile() method complete!
        Modify Book Class (add purchaser initializing to constructor method) and Add getTitile() method
        Modify EngineUI Class (mainMenuPage) and Add new method (printBookList)
        Modify Engine Class (showSuggest UI, saveUserDataFile, buyBook) and Add new method (getSelectedBook, initializeAccount)
        Move file method to FileMenager Class

17th upload: update code & class diagram
  Code: Modify EngineUI Class (about UI, loop, solve index problem)
  Class: Relate with code (modifiy Engine, EngineUI, Book + Add FileManager Class)
