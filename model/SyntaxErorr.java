package model;

/**
 * This class is created to throw a syntax error
 * whenever according to the production rules
 */
public class SyntaxErorr extends Exception{
	/**
	 * Feilds
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	/*
	 * Constructor
	 */
	public SyntaxErorr( String message) {
		this.message= message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = "Parsing error due to " + message;
	}
}