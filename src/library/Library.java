package library;
import java.util.*;
import java.io.*;

public class Library {
    private boolean okToPrint;
    private ArrayList<Book> libraryBooks;
    private HashMap<String, Patron> patron;
    private Calendar calendar;
    private boolean openOrNot;
    private Patron servingPatron;
    private HashMap<Integer, Book> numberedListOfServing;
    private ArrayList<Book> searchBooks;
    private HashMap<Integer, Book> numberedListOfSearch;
    private boolean serveOrNot;
    private boolean searchOrNot;

    /**
     * The constructor for the class
     */
    public Library() {
        this.okToPrint = true;
        this.libraryBooks = this.readBookCollection();
        this.patron = new HashMap<String, Patron>();
        this.calendar = new Calendar();
        this.openOrNot = false;
        this.numberedListOfServing = new HashMap<Integer, Book>();
        this.searchBooks = new ArrayList<Book>();
        this.numberedListOfSearch = new HashMap<Integer, Book>();
        this.serveOrNot = false;
        this.searchOrNot = false;
        this.servingPatron = new Patron(null,null);
    }

    /**
     * The second constructor for the class
     * @param collection
     */
    public Library(ArrayList<Book> collection){
        this.okToPrint = false;
        this.libraryBooks = collection;
        this.patron = new HashMap<String, Patron>();
        this.calendar = new Calendar();
        this.openOrNot = false;
        this.numberedListOfServing = new HashMap<Integer, Book>();
        this.searchBooks = new ArrayList<Book>();
        this.numberedListOfSearch = new HashMap<Integer, Book>();
        this.serveOrNot = false;
        this.searchOrNot = false;
        this.servingPatron = new Patron(null,null);
    }


    /**
     * The start() method for the user
     */
    public void start(){
    	this.println("Welcome to this digital library! \nPlease choose from the following options and enter them (case sensitive) to proceed your journey: \n"
        		+ "	open\n"
        		+ "	issueCard xxx\n"
        		+ "	serve xxx\n"
        		+ "	search xxxx\n"
        		+ "	checkOut x,x,x\n"
        		+ "	checkIn x,x,x\n"
        		+ "	close\n"
        		+ "	quit \n\n");
        while (true){
            Scanner scanner = new Scanner(System.in);
            this.println("Enter a command: ");
            String newCommand = scanner.nextLine();
            //delete the blanks
            newCommand = newCommand.trim();
            if (newCommand.equals("quit")){
                this.quit();
            }
            else{
                //open
                if (newCommand.equals("open") && !this.openOrNot){
                    this.open();
                }
                else if (newCommand.equals("open") && this.openOrNot){
                    this.println("The Library has already been open!!!");
                }
                else if ((!newCommand.equals("open")) && (!this.openOrNot)){
                    this.println("Library is closed! No operations allowed!");
                }

                //issue card
                else if (newCommand.contains("issueCard") && this.openOrNot){
                    String nameOfPatron = newCommand.substring(9).trim();
                    this.issueCard(nameOfPatron);
                }

                //serve patron
                else if (newCommand.contains("serve") && this.openOrNot){
                    String nameOfPatron = newCommand.substring(5).trim();
                    this.serve(nameOfPatron);
                    this.serveOrNot = true;
                }

                //check in (In the form of "checkIn 1,2,3")
                else if (newCommand.contains("checkIn") && this.openOrNot){
                    this.toCheckIn(newCommand);
                }

                //search books
                else if (newCommand.contains("search") && this.openOrNot){
                    String nameOfBooks = newCommand.substring(6).trim();
                    this.search(nameOfBooks);
                    this.searchOrNot = true;
                }

                //check out (In the form of "checkOut 1,2,3")
                else if (newCommand.contains("checkOut") && this.openOrNot){
                    this.toCheckOut(newCommand);
                }

                //close library
                else if (newCommand.equals("close")){
                    this.close();
                }   
                else {
                    this.println("Please enter the right command!!");
                }
            }
        }
    }

    /**
     *Before implementing checkIn, we need to check if the patron is served or not, 
     *if he/she is served, we extract the numberlist, then use checkNum function to see if the numberlist is out of range or not
     *if everything is correct, within the checkNum function, we implement checkIn
     */
    public void toCheckIn(String newCommand){
        if(this.serveOrNot == false){
            this.println("Please serve first!");
        }
        else{
            String bookNumbers = newCommand.substring(7).trim();
            if (bookNumbers.length() == 0){
                this.println("Please enter the book number to check in!");
            }
            else{
                String[] bookNumberList = bookNumbers.split(",");
                //check see if the numberlist is out of range or not, if everything is correct, within the checkNum function, we implement checkIn
                checkNum(bookNumberList, true);
            } 
            //this.println(printPatronInfo());
        }
        
    }

    /**
     *Before implementing checkOut, we need to check if the patron is served or not, 
     *if he/she is served, we extract the numberlist, then use checkNum function to see if the numberlist is out of range or not
     *if everything is correct, within the checkNum function, we implement checkOut
     */
    public void toCheckOut(String newCommand){
        if (this.serveOrNot == false ){
            this.println("Please serve first!");
        }
        else if(this.searchOrNot == false){
            this.println("Please search first!");
        }
        else{
            String bookNumbers = newCommand.substring(8).trim();
            if (bookNumbers.length() == 0){
                this.println("Please enter the book number to check out!");
            }
            else{
                String[] bookNumberList = bookNumbers.split(",");
                //check see if the numberlist is out of range or not, if everything is correct, within the checkNum function, we implement checkOut
                if (bookNumberList.length <= 3){
                    checkNum(bookNumberList, false);
                }
                else{
                    this.println("No more than 3 books at a time!!!");
                }
            }
            this.println(printPatronInfo());
        }
 
    }

    /**
     *Check if the bookNumberList is out of range or not, if everything is correct, we implement checkIn/checkOut
     */
   public void checkNum(String[] bookNumberList,boolean isCheckIn){
        int j = 0;
        //check Duplicate
        if(bookNumberList.length == 2){
            if(Integer.parseInt(bookNumberList[0].trim())==Integer.parseInt(bookNumberList[1].trim()) ){
                j++;
            }
        }
        //check Duplicate
        if(bookNumberList.length == 3 ){
            if(Integer.parseInt(bookNumberList[0].trim())==Integer.parseInt(bookNumberList[2].trim()) || Integer.parseInt(bookNumberList[0].trim())==Integer.parseInt(bookNumberList[1].trim()) || Integer.parseInt(bookNumberList[1].trim())==Integer.parseInt(bookNumberList[2].trim())){
                j++;
            }
        }
        //check if the input numbers are all in numberedListOfSearch or not
        if (!isCheckIn){
            for (int i=0; i<bookNumberList.length; i++){
                int num = Integer.parseInt(bookNumberList[i].trim());
                if (! this.numberedListOfSearch.containsKey(num) ){
                    j ++;
                }
            }
        }
        else if (isCheckIn){
            for (int i=0; i<bookNumberList.length; i++){
                int num = Integer.parseInt(bookNumberList[i].trim());
                if (! this.numberedListOfServing.containsKey(num) ){
                    j ++;
                }
            }
        }
        
        //If input check is correct, implement checkIn or checkOut function
        if (j == 0){
            for (int k=0; k<bookNumberList.length; k++){
                int number = Integer.parseInt(bookNumberList[k].trim());
                if (this.numberedListOfServing.containsKey(number) && isCheckIn){
                    this.checkIn(number);
                }
                else if (this.numberedListOfSearch.containsKey(number) && ! isCheckIn){
                    this.checkOut(number);
                    this.searchOrNot = false;
                }
                else{
                    this.println("Number "+ number + " is out of range!");
                }
            }
        }
        //if input is wrong, then print error message
        else{
            this.println("At least one number has some problems, please check your input!");
        }
    }

    /**
     * If okToPrint is true, prints the message
     * @param message
     */
    public void print(String message){
        if (this.okToPrint){
            System.out.print(message);
        }
    }

    /**
     * If okToPrint is true, prints the message (println)
     * @param message
     */
    public void println(String message){
        if (this.okToPrint){
            System.out.println(message);
        }
    }

    /**
     * Starts the day (by advancing the calendar). 
     * Then sends overdue notices to all patrons with overdue books
     * @return
     */
    public ArrayList<OverdueNotice> open(){
        this.openOrNot = true;
        this.calendar.advance();
        this.println("Library is opened today!");
        ArrayList<OverdueNotice> overdueList = this.createOverdueNotices();
        for (OverdueNotice notice : overdueList){
            this.println(notice.toString());
        }           
        return  overdueList;
    }

    /**
     * Checks each Patron to see whether he/she has books which were due yesterday.
     * @return
     */
    public ArrayList<OverdueNotice> createOverdueNotices(){
        ArrayList<OverdueNotice> overdueList = new ArrayList<OverdueNotice>();
        Set<String> patronNames = this.patron.keySet();
        for (String patronName : patronNames){
            ArrayList<Book> books = this.patron.get(patronName).getBooks();
            for (Book book : books){
                if (book.getDueDate() == (this.calendar.getDate()-1)){
                    overdueList.add(new OverdueNotice(this.patron.get(patronName),this.calendar.getDate()));
                    break;
                }
            }
        }
        return overdueList;
    }

    /**
     * Issues a library card to the person with this name.
     * @param nameOfPatron
     * @return
     */
    public Patron issueCard(String nameOfPatron){
        //Check whether the patron has already had a card!
        if (!this.patron.containsKey(nameOfPatron)){
            Patron newPatron = new Patron(nameOfPatron,this);
            this.patron.put(nameOfPatron, newPatron);
            this.println("OK! A card has beed issued to "+ nameOfPatron);
            return newPatron;
        }
        else{
            this.println("No two cards! This Patron has already had a Library Card!!!");
            return null;
        }

    }

    /**
     * A function merely prints out the patron's information
     */
    public String printPatronInfo(){
        String printingStr = "";
        if (this.servingPatron.getBooks().size() > 0){
            printingStr += "The books currently checked out to this patron are:";
            printingStr += '\n';
            printingStr += "{";
            for (int i = 0;i < (this.servingPatron.getBooks().size());i++){
                this.numberedListOfServing.put(i+1, this.servingPatron.getBooks().get(i));
                //print the numbered list out!
                printingStr += (i+1);
                printingStr += " : ";
                printingStr += this.servingPatron.getBooks().get(i).toString();
                printingStr += "; ";
            }
            printingStr = printingStr.substring(0, printingStr.length()-2);
            printingStr += "}";
        }
        else {
            printingStr = "This patron currently possesses no book. ";
        }
        return printingStr;
    }

    /**
     * serve the patron
     */
    public Patron serve(String nameOfPatron){
        this.numberedListOfServing = new HashMap<Integer, Book>();

        //Check whether the patron has a card
        if (this.patron.containsKey(nameOfPatron)){
            this.servingPatron = this.patron.get(nameOfPatron);
            //check whether the patron has check out books!
            this.println(nameOfPatron + " is being served!");
            //print out patron's information
            this.println(printPatronInfo());
            return this.servingPatron;
        }
        //if the patron does not have a card
        else {
            this.println(nameOfPatron + " does not have a card!");
            return null;
        }
    }

    /**
     * The listed books are being returned by the current Patron
     * Checked in!
     * @param bookNumbers
     * @return
     */
    public ArrayList<Book> checkIn(int... bookNumbers){
        ArrayList<Book> checkInBooks = new ArrayList<Book>();
        for (int number : bookNumbers){
            if(this.numberedListOfServing.containsKey(number)){
                this.numberedListOfServing.get(number).checkIn();
                this.libraryBooks.add(this.numberedListOfServing.get(number));
                checkInBooks.add(this.numberedListOfServing.get(number));
                this.servingPatron.giveBack(this.numberedListOfServing.get(number));
                this.println(this.numberedListOfServing.get(number).getTitle()+" Check In successfully!");
                this.numberedListOfServing.remove(number);
            }
            else{
                this.println("Number "+number +" is out of range! Please enter the number in range!!");
            }
        }           
        return checkInBooks;
    }

    
    /**
     * Printing out and saving in an instance variable, an ArrayList<Book> of books 
     * whose title or author (or both) contains this string.
     * @param part
     * @return
     */
    public ArrayList<Book> search(String part){ 
        this.searchBooks = new ArrayList<Book>();
        this.numberedListOfSearch = new HashMap<Integer, Book>();
        if (part.length()>=4){
            //Adding all the searching books into an ArrayList<Book>
            //No duplicating for books having the same title
            boolean bookExist = false;
            for (Book book : this.libraryBooks){
                if ((book.getTitle().toLowerCase().contains(part.toLowerCase())) || (book.getAuthor().toLowerCase().contains(part.toLowerCase()))){
                    //Check whether any books having the same title in the list
                    for (Book bookSearch : this.searchBooks){
                        if (bookSearch.getTitle().equals(book.getTitle())){
                            bookExist = true;
                            break;
                        }
                    }
                    //If none!
                    if(!bookExist){
                        this.searchBooks.add(book);
                    }
                }
                bookExist = false;
            }
            //To put into a numbered list for printing
            //No duplicating for books having the same title
            for (int i =0; i < (this.searchBooks.size());i++){
                // put the book in the HashMap for printing             
                this.numberedListOfSearch.put(i+1, this.searchBooks.get(i));
            }

            //To print out
            this.println("The searching process has finished!");
            String researchBooks="";
            //check whether the numbered list is empty or not
            if(this.numberedListOfSearch.size() >0){
                researchBooks += "{";
                for (int i = 0; i < this.numberedListOfSearch.size();i++){
                    researchBooks += (i+1);
                    researchBooks += ": ";
                    researchBooks += this.numberedListOfSearch.get(i+1).toString();
                    researchBooks += "; ";
                }
                researchBooks = researchBooks.substring(0, researchBooks.length()-2);
                researchBooks += "}";           
            }
            //if the numbered list is empty
            else if ( this.numberedListOfSearch.size() == 0){
                researchBooks += "Nothing found!";
            }
            this.println(researchBooks);
        }

        //If not enough input key words!
        else{
            this.println("Please enter more key words! At least four!!");
        }
        return this.searchBooks;
    }

    /**
     * Either checks out the book to the Patron currently being served 
     * or tells why the operation is not permitted
     * @param bookNumbers
     * @return
     */
    public ArrayList<Book> checkOut(int... bookNumbers){
        ArrayList<Book> checkOutBooks = new ArrayList<Book>();
        for (int number : bookNumbers){
            //No more than 3 books allowed!
            if(this.numberedListOfSearch.containsKey(number)){
                if(this.servingPatron.getBooks().size()<3){
                    this.libraryBooks.remove(this.numberedListOfSearch.get(number));
                    this.numberedListOfSearch.get(number).checkOut(this.calendar.getDate()+7);
                    this.servingPatron.take(this.numberedListOfSearch.get(number));
                    checkOutBooks.add(this.numberedListOfSearch.get(number));   
                    this.println( this.numberedListOfSearch.get(number).getTitle()+" check out successfully!");
                    this.numberedListOfSearch.remove(number);
                }
                else{
                    this.println("The patron already have checked out 3 books!!! No more books!");
                }
            }
            else{
                this.println("Number"+number +" is out of range! Please enter the number in range!!");
            }
        }
        //Update the serving HashMap!
        for (int i = 0;i < (this.servingPatron.getBooks().size());i++){
            this.numberedListOfServing.put(i+1, this.servingPatron.getBooks().get(i));
        }
        return checkOutBooks;
    }

    /**
     * To close the library
     */
    public void close(){
        this.println("Library has been closed! See you tomorrow morning!!");
        this.openOrNot = false;
        this.searchOrNot = false;
        this.serveOrNot = false;
    }

    /**
     * To end the program
     */
    public void quit(){
        this.println("Bad news!!! The library has been cancelled!");
        System.exit(0);

    }

    /**
     * The getter for libraryBooks
     * @return
     */
    public ArrayList<Book> getLibraryBooks() {
        return this.libraryBooks;
    }


    /**
     * The getter for this.calendar
     * @return
     */
    public Calendar getCalendar() {
        return this.calendar;
    }



    /**
     * get the open or not
     * @return
     */
    public boolean isOpenOrNot() {
        return this.openOrNot;
    }

    /**
     * The main function
     * @param args
     */
    public static void main(String[] args){
        Library newLibrary = new Library();
        newLibrary.start();
    }

    /**
     * To read in from a txt file
     * @return
     */
    private ArrayList<Book> readBookCollection() {
        File file = new File("books.txt");
        ArrayList<Book> collection = new ArrayList<Book>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                line = line.trim();
                if (line.equals("")) continue; // ignore possible blank lines
                String[] bookInfo = line.split(" :: ");
                collection.add(new Book(bookInfo[0], bookInfo[1]));
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return collection;
    }

}