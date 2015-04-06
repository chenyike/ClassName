package library;

import java.util.*;

public class Patron {
	private String name;
	private Library library;
	private ArrayList<Book> listOfBooks;

	/**
	 * The constructor for the class Patron
	 * @param name
	 * @param library
	 */
	public Patron(String name, Library library) {
		this.name = name;
		this.library = library;	
		this.listOfBooks = new ArrayList<Book>();
	}

	/**
	 * return the patron's name
	 * @return
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * Adding this book to the list of the books 
	 * checked out by the patron
	 * @param book
	 */
	public void take(Book book){
		if (this.listOfBooks.size() < 3){
			this.listOfBooks.add(book);
		}
	}
	
	/**
	 * To remove the book from the list of books
	 * checked out by the patron
	 * @param book
	 */
	public void giveBack(Book book){
		this.listOfBooks.remove(book);
	}
	
	/**
	 * return the list of books checked out to this patron
	 * @return
	 */
	public ArrayList<Book> getBooks(){
		return this.listOfBooks;
	}
	
	/**
	 * Return string representation of the patron's name 
	 */
	@Override
	public String toString(){
		String returning = "";
		returning += this.getName();
		return returning;
	}


}
