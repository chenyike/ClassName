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
    }


    /**
     * The start() method for the user
     */
    public void start(){
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
                if (newCommand.equals("open")){
                    this.open();
                }
                else if ((!newCommand.equals("open")) && (!this.openOrNot)){
                    this.println("Library is closed! No operations allowed!");
                }

                //issue card
                if (newCommand.contains("issueCard") && this.openOrNot){
                    String nameOfPatron = newCommand.substring(9).trim();
                    this.issueCard(nameOfPatron);
                }

                //serve patron
                if (newCommand.contains("serve") && this.openOrNot){
                    String nameOfPatron = newCommand.substring(5).trim();
                    this.serve(nameOfPatron);
                }

                //check in
                if (newCommand.contains("checkIn") && this.openOrNot){
                    String bookNumbers = newCommand.substring(7).trim();
                    String[] bookNumberList = bookNumbers.split(",");
                    if (bookNumberList.length == 1){
                        int num = Integer.parseInt(bookNumberList[0].trim()); 
                        this.checkIn(num);
                    }
                    else if (bookNumberList.length == 2){
                        int num1 = Integer.parseInt(bookNumberList[0].trim()); 
                        int num2 = Integer.parseInt(bookNumberList[1].trim()); 
                        this.checkIn(num1);
                        this.checkIn(num2);
                    }
                    else if (bookNumberList.length == 3){
                        int num1 = Integer.parseInt(bookNumberList[0].trim()); 
                        int num2 = Integer.parseInt(bookNumberList[1].trim()); 
                        int num3 = Integer.parseInt(bookNumberList[1].trim()); 
                        this.checkIn(num1);
                        this.checkIn(num2);
                        this.checkIn(num3);
                    }
                }

                
                //search books
                if (newCommand.contains("search") && this.openOrNot){
                    String nameOfBooks = newCommand.substring(6).trim();
                    this.search(nameOfBooks);
                }

                //check out
                if (newCommand.contains("checkOut") && this.openOrNot){
                    String bookNumbers = newCommand.substring(8).trim();
                    String[] bookNumberList = bookNumbers.split(",");
                    if (bookNumberList.length == 1){
                        int num = Integer.parseInt(bookNumberList[0].trim()); 
                        this.checkOut(num);
                    }
                    else if (bookNumberList.length == 2){
                        int num1 = Integer.parseInt(bookNumberList[0].trim()); 
                        int num2 = Integer.parseInt(bookNumberList[1].trim()); 
                        this.checkOut(num1);
                        this.checkOut(num2);
                    }
                    else if (bookNumberList.length == 3){
                        int num1 = Integer.parseInt(bookNumberList[0].trim()); 
                        int num2 = Integer.parseInt(bookNumberList[1].trim()); 
                        int num3 = Integer.parseInt(bookNumberList[1].trim()); 
                        this.checkOut(num1);
                        this.checkOut(num2);
                        this.checkOut(num3);
                    }
                    else{
                        this.println("No more than 3 books at a time!!!");
                    }
                }

                //close library
                if (newCommand.equals("close")){
                    this.close();
                }       
            }
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
        this.println("Library has opened today!");
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
     * Begin checking books out to (or in from) the named patron.
     * @param nameOfPatron
     * @return
     */
    public Patron serve(String nameOfPatron){
        this.numberedListOfServing = new HashMap<Integer, Book>();
        String printingStr = "";
        //Check whether the patron has a card
        if (this.patron.containsKey(nameOfPatron)){             
            this.servingPatron = this.patron.get(nameOfPatron); 
            //check whether the patron has check out books!
            if (this.servingPatron.getBooks().size() > 0){
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
                this.println(nameOfPatron + " is being served!");
                this.println(printingStr);
            }
            else{
                //if no check out books
                this.println(nameOfPatron + " is being served!");
                this.println("No check out book!!!");
            }
            return this.servingPatron;
        }   
        //if the patron does not have a card
        else{
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
            this.numberedListOfServing.get(number).checkIn();;
            this.libraryBooks.add(this.numberedListOfServing.get(number));
            checkInBooks.add(this.numberedListOfServing.get(number));
            this.servingPatron.giveBack(this.numberedListOfServing.get(number));
        }
        this.println("Check In process has finished!");
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
            this.println("Please enter more key words!");
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
            if(this.servingPatron.getBooks().size()<3){
                this.libraryBooks.remove(this.numberedListOfSearch.get(number));
                this.numberedListOfSearch.get(number).checkOut(this.calendar.getDate()+7);
                this.servingPatron.take(this.numberedListOfSearch.get(number));
                checkOutBooks.add(this.numberedListOfSearch.get(number));   
                this.println("One book check out successfully!");
            }
            else{
                this.println("The patron already have checked out 3 books!!! No more books!");
            }
        }
        return checkOutBooks;
    }

    /**
     * To close the library
     */
    public void close(){
        this.println("Library has been closed! See you tommorrow morning!!");
        this.openOrNot = false;
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
