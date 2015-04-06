package library;

public class Book { 
	 private String title;
	 private String author;
	 private int dueDate;

	/**
	 * The constructor for the class Book
	 * @param title
	 * @param author
	 */
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.dueDate = -1;
	}

	/**
	 * To get the the book's title
	 * @return
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * To get the book's author
	 * @return
	 */
	public String getAuthor(){
		return this.author;
	}

	/**
	 * To get the dueDate of the book
	 * @return
	 */
	public int getDueDate(){
		return this.dueDate;
	}

	/**
	 * To set the due date of the book to a specified date
	 * @param date
	 */
	public void checkOut(int date){
		this.dueDate = date;
	}

	/**
	 * To set the due date of this book to be -1
	 */
	public void checkIn(){
		this.dueDate = -1;		
	}

	/**
	 * To get a form of title, by author
	 */
	@Override
	public String toString(){
		String returning = "";
		returning += this.getTitle();
		returning += ", ";
		returning += "by ";
		returning += this.getAuthor();
		return returning;
	}
}
