package model;

import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * in this class I create a tokenizer based on these rules: (0) All “names” and
 * “integer-value” are user defined names and values in the source code. (1) The
 * tokens in bold letters are reserved words. (2) The words between “ …” are
 * terminals (tokens).
 */
public class Tokenizer {
	/*
	 * Fields
	 */
	private StringBuilder inputfile;
	private ArrayList<String> generatedTokens;
	private int currentIndex;
	/*
	 * bold in the given file were reserved so I add them to this static arraylist
	 */
	public static final List<String> reservedWords = Arrays.asList("project", "const", "var", "int", "routine", "end",
			"start", "input", "output", "if", "then", "endif", "else-part", "else", "loop", "do");

	/*
	 * between the " " in the given file were terminals so I add them to this static
	 * arrayList
	 */
	public static final List<String> terminals = Arrays.asList(".", ";", "name", "integer-value", ",", ":=", "(", ")",
			"+", "-", "*", "/", "%", "=", "<>", "<", "<=", ">", ">=");

	/*
	 * Constructor
	 */
	/**
	 * @param input
	 */
	public Tokenizer(StringBuilder inputfile) {
		this.inputfile = inputfile;
		this.generatedTokens = new ArrayList<>();
		this.currentIndex = 0;
	}

	/*
	 * This method is used to tokenize the file inputted
	 */
	public ArrayList<String> tokenize() {
		StringBuilder currentToken = new StringBuilder();

		while (currentIndex < inputfile.length()) {
			char currentChar = inputfile.charAt(currentIndex);

			if (Character.isWhitespace(currentChar)) {
				currentIndex++;
				continue;
			}

			if (currentChar == ':' && currentIndex + 1 < inputfile.length()
					&& inputfile.charAt(currentIndex + 1) == '=') {
				// Tokenize ":=" as a single token
				if (currentToken.length() > 0) {
					addToken(currentToken.toString());
					currentToken.setLength(0);
				}
				addToken(":=");
				currentIndex += 2;
				continue;
			}

			if (currentChar == '<' && currentIndex + 1 < inputfile.length()
					&& inputfile.charAt(currentIndex + 1) == '>') {
				// Tokenize "<>" as a single token
				if (currentToken.length() > 0) {
					addToken(currentToken.toString());
					currentToken.setLength(0);
				}
				addToken("<>");
				currentIndex += 2;
				continue;
			}

			if (currentChar == '<' && currentIndex + 1 < inputfile.length()
					&& inputfile.charAt(currentIndex + 1) == '=') {
				// Tokenize "<=" as a single token
				if (currentToken.length() > 0) {
					addToken(currentToken.toString());
					currentToken.setLength(0);
				}
				addToken("<=");
				currentIndex += 2;
				continue;
			}

			if (currentChar == '>' && currentIndex + 1 < inputfile.length()
					&& inputfile.charAt(currentIndex + 1) == '=') {
				// Tokenize ">=" as a single token
				if (currentToken.length() > 0) {
					addToken(currentToken.toString());
					currentToken.setLength(0);
				}
				addToken(">=");
				currentIndex += 2;
				continue;
			}

			if (isTerminalCharacter(currentChar)) {
				/*
				 * If token is terminal here
				 */
				if (currentToken.length() > 0) {
					addToken(currentToken.toString());
					currentToken.setLength(0);
				}
				addToken(String.valueOf(currentChar));
			} else {

				currentToken.append(currentChar);
				String tokenString = currentToken.toString();

				if (Character.isDigit(currentChar)) {
					if (currentToken.length() > 1 && Character.isDigit(tokenString.charAt(0))) {
						// Tokenize the previous part of the token as a reserved word
						String reservedPart = tokenString.substring(0, tokenString.length() - 1);
						if (reservedWords.contains(reservedPart)) {
							addToken(reservedPart);
							currentToken.setLength(0);
							currentToken.append(currentChar);
						}
					}
				}

				else {
					/*
					 * 
					 * If resever words here
					 */

					if (reservedWords.contains(tokenString)) {
						addToken(tokenString);
						currentToken.setLength(0);
					}

				}
			}
			currentIndex++;
		}
		if (currentToken.length() > 0) {
			addToken(currentToken.toString());
		}
		return generatedTokens;
	}

	/*
	 * Check if the chrachter is terminal
	 */
	/**
	 * @param character
	 * @return
	 */
	private boolean isTerminalCharacter(char character) {
		return terminals.contains(String.valueOf(character));
	}

	/*
	 * This method checks if the token is a reserved word
	 */
	/**
	 * @param token
	 */
	private void addToken(String token) {
		if (reservedWords.contains(token)) {
			// generatedTokens.add(token.toUpperCase());
		} else {
			// generatedTokens.add(token);
		}
		generatedTokens.add(token);
	}

	/*
	 * Getters and setters
	 */
	/**
	 * @return the input file
	 */
	public StringBuilder getInputfile() {
		return inputfile;
	}

	/**
	 * @param input file the input file to set
	 */
	public void setInputfile(StringBuilder inputfile) {
		this.inputfile = inputfile;
	}

	/**
	 * @return the generatedTokens
	 */
	public ArrayList<String> getGeneratedTokens() {
		return generatedTokens;
	}

	/**
	 * @param generatedTokens the generatedTokens to set
	 */
	public void setGeneratedTokens(ArrayList<String> generatedTokens) {
		this.generatedTokens = generatedTokens;
	}

	/**
	 * @return the currentIndex
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @param currentIndex the currentIndex to set
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
}