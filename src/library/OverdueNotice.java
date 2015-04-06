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
		String returning = "";
		ArrayList<Book> overdueBooks = new ArrayList<Book>();
		for (Book book : this.patron.getBooks()){
			returning += book.getTitle();
			returning += " will be due at: ";
			returning += book.getDueDate();
			if (book.getDueDate() < this.todaysDate){
				overdueBooks.add(book);
			}
			returning += '\n';
		}
		if(overdueBooks.size()>0){
			returning += "Attention!!! You have overdue books: ";
			for (Book overdueBook : overdueBooks){
				returning += overdueBook.getTitle();
				returning += " ";
			}
		}
		else{
			returning += "No overdue books!";
		}		
		return returning;
	}

}
