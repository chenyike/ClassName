package library;
import java.util.*;

public class OverdueNotice {
	private Patron patron;
	private int todaysDate;

	/**
	 * The constructor for the class OverdueNotice
	 * @param patron
	 * @param todaysDate
	 */
	public OverdueNotice(Patron patron, int todaysDate) {
		this.patron = patron;
		this.todaysDate = todaysDate;		
	}
	
	

	/**
	 * return the patron
	 * @return
	 */
	public Patron getPatron() {
		return this.patron;
	}

	/**
	 * return today's date
	 * @return
	 */
	public int getTodaysDate() {
		return this.todaysDate;
	}

	/**
	 * The string representation of the Overdue notice!
	 */
	@Override
	public String toString(){
		String returning = "Today is Day: " + this.todaysDate+"\n";
		ArrayList<Book> overdueBooks = new ArrayList<Book>();
		for (Book book : this.patron.getBooks()){
			returning += book.getTitle();
			returning += " will be due (or is due) on Day: ";
			returning += book.getDueDate();
			if (book.getDueDate() < this.todaysDate){
				overdueBooks.add(book);
			}
			returning += '\n';
		}
		if(overdueBooks.size()>0){
			returning += "Attention!!!\n" + this.patron + " has overdue books: ";
			for (Book overdueBook : overdueBooks){
				returning += overdueBook.getTitle();
				returning += " \t\t\n";
			}
		}
		else{
			returning += "No overdue books!";
		}		
		return returning;
	}

}
