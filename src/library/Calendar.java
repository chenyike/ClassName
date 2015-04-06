package library;

public class Calendar {
	private int date;

	/**
	 * Constructor for the class Calendar
	 */
	public Calendar() {
		this.date = 0;
	}
	
	/**
	 * To get the current date
	 * @return
	 */
	public int getDate(){
		return this.date;
	}
	
	/**
	 * Increment the date
	 */
	public void advance(){
		this.date ++;
	}

	
}
