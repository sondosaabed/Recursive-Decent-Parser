
package model;

import java.util.ArrayList;

/**
 * In this class I implement the recursive decent parser
 */
public class RecursiveDecentParser {
	/*
	 * Feilds
	 */
	private int index;
	private String currentToken;
	private boolean answer = true;
	private String error = "";
	ArrayList<String> tokens;

	/*
	 * constructor
	 */
	/**
	 * @param tokens
	 */
	public RecursiveDecentParser(ArrayList<String> tokens) {
		this.tokens = tokens;
		this.index = 0;
		this.currentToken = tokens.get(0);
	}

	/**
	 * This is the method if called then the file is parsed
	 * 
	 * @throws SyntaxErorr
	 */
	public void parse() throws SyntaxErorr {
		try {
			projectDeclaration();
		} catch (SyntaxErorr e) {
			ERORR("Expected token not found at " + currentToken);
		}
	}

	/**
	 * this methid set the index to the next token index
	 */
	private boolean nextToken() {
		index++;
		if (index < tokens.size()) {
			currentToken = tokens.get(index);
			return true;
		} else {
			currentToken = null;
			return false;
		}
	}

	/*
	 * I created this method to hanldle error modes panicly were i put the next
	 * token as null
	 */
	/**
	 * @param string
	 * @throws SyntaxErorr
	 */
	private void ERORR(String string) throws SyntaxErorr {
		answer = false;
		SyntaxErorr e = new SyntaxErorr(currentToken);
		e.setMessage(string);
		setError(e.getMessage());
		System.out.println(e.getMessage());
		/*
		 * The panic mode
		 */
		currentToken = null;
	}

	/*
	 * I ceated this method to handle the invalid user defined naming
	 */
	/**
	 * @return
	 * @throws SyntaxErorr
	 */
	private boolean invalidName() throws SyntaxErorr {
		if (Tokenizer.reservedWords.contains(currentToken)) {
			answer = false;
			@SuppressWarnings("unused")
			SyntaxErorr e = new SyntaxErorr(currentToken);
			String message = "Invalid naming at " + currentToken + " used reserved word";
			setError(message);
			currentToken = null;
			return true;
		} else {
			nextToken();
			return false;
		}
	}

	/*
	 * I created this method to handle when the value integer is invalid
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void invalidINT() throws SyntaxErorr {
		try {
			@SuppressWarnings("unused")
			int value = Integer.parseInt(currentToken);
			nextToken();
		} catch (Exception e1) {
			answer = false;
			@SuppressWarnings("unused")
			SyntaxErorr e = new SyntaxErorr(currentToken);
			String message = "Invalid integer value at " + currentToken;
			setError(message);
			currentToken = null;
		}
	}

	/*
	 * This method returns the answer in string to be displayed to usr this is the
	 * final answer of the parsing process
	 */
	/**
	 * @return answer if passed or failure
	 */
	public String returnResult() {
		if (answer == true) {
			return "parsed";
		} else {
			return "failed";
		}
	}

	/*
	 * This method is created for the reason that in each production rule instead of
	 * writing the same logic of matching an expected token I use this method to
	 * match if it doesn't match what is expected like a semi colon for example it
	 * will call the ERROR method which will return the error
	 */
	/**
	 * @param expectedToken
	 * @return
	 * @throws SyntaxErorr
	 */
	private boolean match(String expectedToken) throws SyntaxErorr {
		if (currentToken == null) {
			return false;
		}
		String nextToken = currentToken;
		nextToken();
		if (nextToken == null || !nextToken.equals(expectedToken)) {
			//ERORR("Expected: " + expectedToken + ", but found: " + (nextToken != null ? nextToken : "end of input"));
			return false;
		}
		return true;
	}

	/*
	 * ************ In the next section The production rules are handled each in a
	 * method **********************
	 * 
	 */

	/*
	 * This is the first production rule to start with
	 * 
	 * Rule: project-declaration -> project-def "."
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void projectDeclaration() throws SyntaxErorr {
		projectDef();
		match(".");
	}

	/*
	 * Rule: project-def -> project-heading declarations compound-stmt
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void projectDef() throws SyntaxErorr {
		projectHeading();
		declarations();
		compound_stmt();
	}

	/*
	 * Rule: project-heading -> project "name" ";"
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void projectHeading() throws SyntaxErorr {
		match("project");
		invalidName();
		match(";");
	}

	/*
	 * Rule: declarations -> const-decl var-decl subroutine-decl
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void declarations() throws SyntaxErorr {
		const_decl();
		var_decl();
		subroutine_decl();
	}

	/*
	 * Rule: const-decl -> const ( const-item ";" )+ | lambda
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void const_decl() throws SyntaxErorr {
		if (match("const")) {
			/*
			 * because the loop type is +
			 * there is at least one (const-item ";")
			 */
			const_item();
	        match(";");

	        while (match("const")) {
	            const_item();
	            match(";");
	        }
	    } else {
	    	/*
	    	 * When there is no cont delc
	    	 * 
	    	 * Lamda case
	    	 * 
	    	 * Just no action 
	    	 */
	    }
	}

	/*
	 * Rule: const-item -> "name" = "integer-value"
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void const_item() throws SyntaxErorr {
		invalidName();
		match("=");
		invalidINT();
	}

	/*
	 * Rule: var-decl -> var (var-item ";" )+ | lambda
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void var_decl() throws SyntaxErorr {
		if (match("var")) {
			 
			/*
			 * because the loop type is +
			 * there is at least one (var-item ";")
			 */
			var_item();
			match(";");

			while (match("var")) {
				/*
				 * If there is additional var declartions
				 */
				var_item();
				match(";");
			}
		} else {
	    	/*
	    	 * When there is no var delc
	    	 * Lamda case
	    	 * just no action
	    	 */
			nextToken();
	    }
	}

	/*
	 * Rule: var-item -> name-list ":" int
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void var_item() throws SyntaxErorr {
		name_list();
		match(":");
		match("int");
	}

	/*
	 * Rule: name-list -> "name" ( "," "name" )*
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void name_list() throws SyntaxErorr {
		invalidName();
		/*
		 * * loop means at least one name?
		 */
		while (match(",")) {
			invalidName();
		}
	}

	/*
	 * Rule: subroutine-decl -> subroutine-heading declarations compound-stmt “;” |
	 * lambda
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void subroutine_decl() throws SyntaxErorr {
		if (subroutine_heading()) {
			declarations();
			compound_stmt();
			match(";");
		}else {
	    	/*
	    	 * When there is no subroutine delc
	    	 * Lamda case
	    	 * juut do no action
	    	 */
			nextToken();

		}
	}

	/*
	 * Rule: subroutine-heading -> routine "name" ";"
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private boolean subroutine_heading() throws SyntaxErorr {
		if(match("routine")) {
			invalidName();
			match(";");
			return true;
		}else {
			return false;
		}
	}

	/*
	 * Rule: compund-stmt -> start stmt-list end
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void compound_stmt() throws SyntaxErorr {
		match("start");
		stmt_list();
		match("end");
	}

	/*
	 * Rule: stmt-list -> ( statement ";" )*
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void stmt_list() throws SyntaxErorr {
		while (statement()) {
			match(";");
		}
	}

	/*
	 * Rule: statement -> ass-stmt | inout-stmt | if-stmt | loop-stmt |
	 * compound-stmt | Lambda
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private boolean statement() throws SyntaxErorr {
		if (currentToken != null && !invalidName()) {
			ass_stmt();
			return true;
		} else if (match("if")) {
			if_stmt();
			return true;
		} else if (match("loop")) {
			loop_stmt();
			return true;
		} else if (match("start")) {
			compound_stmt();
			return true;
		} else if(inout_stmt()){
			return true;
		}else {
			/*
			 * Lamda expression 
			 * Do nothing
			 *  there is no statment 
			 */
			nextToken();

		}
		return false;
	}

	/*
	 * Rule: loop-stmt -> loop “(“ bool-exp “)” do statement
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void loop_stmt() throws SyntaxErorr {
		match("(");
		bool_exp();
		match(")");
		match("do");
		statement();
	}

	/*
	 * Rule: bool-exp -> name-value relational-oper name-value
	 * 
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void bool_exp() throws SyntaxErorr {
		name_value();
		relational_oper();
		name_value();
	}

	/*
	 * Rule: relational-oper -> "=" | "<>" | "<" | "<=" | ">" | ">="
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void relational_oper() throws SyntaxErorr {
		if (currentToken.equals("=")) {
			match("=");
		} else if (currentToken.equals("<>")) {
			match("<>");
		} else if (currentToken.equals("<")) {
			match("<");
		} else if (currentToken.equals("<=")) {
			match("<=");
		} else if (currentToken.equals(">")) {
			match(">");
		} else if (currentToken.equals(">=")) {
			match(">=");
		}
	}

	/*
	 * Rule: name-value -> "name" | "integer-value"
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void name_value() throws SyntaxErorr {
		if(nextToken()) {
			if (Character.isDigit(currentToken.charAt(0))) {
				invalidINT();
			} else {
				invalidName();
			}
		}
	}

	/*
	 * Rule: else-part -> else statement | lambda
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void else_part() throws SyntaxErorr {
		if(match("else")) {
			statement();
		}else {
			/*
			 * do nothing 
			 * Lamda expression 
			 * there is no else part
			 */
			nextToken();

		}
	}

	/*
	 * Rule: if-stmt -> if “(“ bool-exp “)” then statement else-part endif
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void if_stmt() throws SyntaxErorr {
		match("(");
		bool_exp();
		match(")");
		match("then");
		statement();
		else_part();
		match("endif");
	}

	/*
	 * Rule: input-stmt ->input "(" "name" ")" | output "(" name-value ")"
	 */
	private boolean inout_stmt() throws SyntaxErorr {
		if (match("input")) {
			match("(");
			invalidName();
			match(")");
			return true;
		} else if (match("output")) {
			match("(");
			name_value();
			match(")");
			return true;
		}
		return false;
	}

	/*
	 * Rule: ass-stmt -> ”name” ":=" arith-exp
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void ass_stmt() throws SyntaxErorr {
		match(":=");
		arith_exp();
	}

	/*
	 * Rule: arith-exp -> term ( add-sign term )*
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void arith_exp() throws SyntaxErorr {
		term();
		while (add_sign()) {
			term();
		}
	}

	/*
	 * Rule: add-sign -> "+" | "-"
	 */
	/**
	 * @return boolean
	 * @throws SyntaxErorr
	 */
	private boolean add_sign() throws SyntaxErorr {
		if(currentToken.equals(null)) {
			return false;
		}
		else if (currentToken.equals("+")) {
			match("+");
			return true;
		} else if (currentToken.equals("-")) {
			match("-");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Rule: mul-sign -> "*" | "/" | “%”
	 */
	/**
	 * @return boolean
	 * @throws SyntaxErorr
	 */
	private boolean mul_sign() throws SyntaxErorr {
		if(nextToken()) {
			return false;
		}
		else if (currentToken.equals("*")) {
			match("*");
			return true;
		} else if (currentToken.equals("/")) {
			match("/");
			return true;
		} else if (currentToken.equals("%")) {
			match("%");
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Rule: term -> factor ( mul-sign factor )*
	 * 
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void term() throws SyntaxErorr {
		factor();
		while (mul_sign()) {
			factor();
		}
	}

	/*
	 * Rule: factor -> "(" arith-exp ")" | name-value
	 */
	/**
	 * @throws SyntaxErorr
	 */
	private void factor() throws SyntaxErorr {
		if (match("(")) {
			arith_exp();
			match(")");
		} else {
			name_value();
		}
	}

	/*
	 ******* 8 Getters and setters section
	 */
	/**
	 * @return the currentToken
	 */
	public String getCurrentToken() {
		return currentToken;
	}

	/**
	 * @param currentToken the currentToken to set
	 */
	public void setCurrentToken(String currentToken) {
		this.currentToken = currentToken;
	}

	/**
	 * @return the answer
	 */
	public boolean isAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(boolean answer) {
		this.answer = answer;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
}