import java.util.*;
import java.lang.Math;


public class Basic {
	protected char operators[] = {'+', '-', '/', '*', '^', '(', ')'};
	protected Stack<Double> operands = new Stack<>();
	protected Stack<Character> eval = new Stack<>();
    protected Queue<String> q = new LinkedList<>();
    
	protected String expr;  // EXPRESSION; user input
	
	Map<Character, Integer> weights = new HashMap<Character, Integer>();
	Map<Character, String> assoc = new HashMap<Character, String>();
	
	/** Constructor for Basic
	 * initializes weights and associativity
	 */
	public Basic() {
		weights.put('+', 1);  // set weights for order of operation
		weights.put('-', 1);
		weights.put('*', 2);
		weights.put('/', 2);
		weights.put('^', 3);

		assoc.put('+', "left");  // set associativity
		assoc.put('-', "left");
		assoc.put('*', "left");
		assoc.put('/', "left");
		assoc.put('^', "right");
		expr = "";
	}
	
	/** Parses expression and returns true if success; false if fail
	 * @param none
	 * @return true/false
	 */
	public boolean parsing() {
		
		int i = 0;  // to go through the input

		// @@@@ TESTING @@@@
		if(!testsFixes()) {
			return false;
		}

		// @@@@ PARSING @@@@
		for(; i < expr.length(); ) {
			int j;
			
			if(isOperand(expr.charAt(i)) || (expr.charAt(i) == '-' && i == 0) || (expr.charAt(i) == '-' && isNegative(i))) {
				String temp;
				
				j = 0;
				
				int tempI = i;
				
				// includes negative number checking
				while((tempI+j) < expr.length()) {
					if(isOperand(expr.charAt(tempI+j))|| (expr.charAt(tempI+j) == '-' && isOperand(expr.charAt(tempI+j+1)))){
						j++;
					}
					
					else
						break;
					
				}

				temp = expr.substring(i, i+j);
				q.add(temp);
				i += j;
			}

			// CASE 2 TOKEN is OPERATOR
			else if(isOperator(expr.charAt(i))) {
							
				while(!eval.empty()) {
					if(eval.peek() == '(')
						break;
					else if(((assoc.get(expr.charAt(i)) == "left") && weights.get(expr.charAt(i)) <= weights.get(eval.peek())) 
								|| ( assoc.get(expr.charAt(i)) == "right" && weights.get(expr.charAt(i)) < weights.get(eval.peek()) )) {
							char tmp = eval.pop();
							q.add(String.valueOf(tmp));
					
						}
					break;
				}

	            eval.push(expr.charAt(i));
	            i++;
			}
			
			// CASE 3 TOKEN is a PARENTHESIS
			else if(expr.charAt(i) == '('){
				eval.push(expr.charAt(i));
				i++;
			}
			
   
			
	        // CASE 4 TOKEN IS CLOSING PARENTHESIS
	        else if(expr.charAt(i) == ')') {
	        	while(eval.peek() != null && eval.peek() != '(') {
	        			char temp = eval.pop();
	        			q.add(String.valueOf(temp));
	        		}
	        	
	        	eval.pop();   // throw '('
	        	i++;
	        }
		}
		
	    
	 // POP REMAINING OPERATORS IN STACK
	    while(!eval.empty()) {
	    	char temp = eval.pop();
	    	q.add(String.valueOf(temp));
	    }
	    
		return true; // parsing succeeded
	}
	
	/**
	 * Checks if input is valid; 
	 * @param: user input
	 * @return: true/false
	 */
	public boolean testsFixes() {
		
		/* @@@@@@@@PARENTHESES Tests@@@@@@@
		 * Checks for parentheses and also adds * for multiplication
		 */
		
		int count = 0;
		
		if(expr.charAt(0) == ')' || expr.charAt(expr.length() - 1) == '(') {
			System.out.println("Invalid expression");
			return false;
		}
		
//		// inserts * for multiplication if needed
		
		// if "digit("
		for(int i = 0; i < expr.length(); i++) {
			if((i+1) < expr.length()) 
				if(Character.isDigit(expr.charAt(i)) && (expr.charAt(i+1) == '(')) 
					expr = expr.substring(0, i+1) + '*' + expr.substring(i+1);
				
//			if(Character.isDigit(data.charAt(i)) && (data.charAt(i+1) == '(' && (i+1) < data.length()))					
		}
		
	    for(int i = 0; i < expr.length(); i++){
	        if(expr.charAt(i) == '(')
	            count++;
	        if(expr.charAt(i) == ')'){
	            count--;
	            
	            // case: ")("  -> ")*("
	            if(i+1 < expr.length()) 
	            	if (expr.charAt(i+1) == '(')
	            		expr = expr.substring(0, i+1) + "*" + expr.substring(i+1);	
	            
	            // case: ")[any digit]"
	            if(i+1 < expr.length())
	            	if (Character.isDigit(expr.charAt(i+1))){
	            		expr = expr.substring(0, i+1) + "*" + expr.substring(i+1);	
	            }
	            

	        }
	        
            if(expr.charAt(i) == '-')
            	if (i+1 < expr.length())
            		if(expr.charAt(i+1) == '(') {
            			expr = expr.substring(0, i+1) + "1" + expr.substring(i+1);
            		}
	    
	    }
	    
	    // mismatched # of parentheses; invalid expression
	    if(count != 0) {
	    	System.out.println("Mismatched parentheses");
	    	return false;
	    }

	    
	    /* @@@@@@@@@OPERATOR TESTS@@@@@@@@@@@
	     * checks if expression has any invalid operator
	     */
	    
	    // checks if expression ends with an operator
	    if(isOperator(expr.charAt(expr.length() - 1))) {
	    	System.out.println("Invalid expression");
	    	return false;
	    }
	    
	    // checks for invalid operator order; such as +* */ -+, etc.
	    for(int i = 0; i < expr.length(); i++){
	        if(isOperator(expr.charAt(i))){
	        	// ignore '-' for negative property
	            if(expr.charAt(i+1) == '*' || expr.charAt(i+1) == '+' || expr.charAt(i+1) == '/' || expr.charAt(i+1) == '^') {
	            	System.out.println("Invalid expression");
	            	return false;
	            }
	        }
	    }
	    
	    // checks if there are any unknown operators / characters
	    for(int i = 0; i < expr.length(); i++)
	    	if(!isOperator(expr.charAt(i)) && !isOperand(expr.charAt(i)) && expr.charAt(i) != '(' && expr.charAt(i) != ')')
	    			return false;
	    	
	    for(int i = 0; i < expr.length()-1; i++) {
	    	if(isOperand(expr.charAt(i)) && expr.charAt(i+1) == '-') {
	    		expr = expr.substring(0, i+1) + "+" + expr.substring(i+1);
	    	}
	    }
	    return true;  // input is valid
	}
	
	/** Checks if parsed operator is valid
	 * @param character
	 * @return true/false
	 */
	public boolean isOperator(char c) {
	    if(c == '*' || c == '+' || c == '-' || c == '/' || c == '^')
	        return true;
	    return false;
	}
	
	/** Checks if parsed char is an operand (digit / decimal point)
	 * @param char
	 * @return true/false
	 */
	public boolean isOperand(char c) {
		if(Character.isDigit(c) || c == '.' )
			return true;
		return false;
	}
	
	/** Checks if string is numeric / int or double
	 * @param strNum
	 * @return true/false
	 */
	public boolean checkIfNum(String strNum) {
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/** computes RPN
	 * @param string that contains original infix 
	 * @return answer or 0 if evaluation fails
	 */
	double compRPN(String x) {
		expr = x;  // initializes expression to parameter
		
		if(!parsing()) {
			System.out.println("Input invalid!");
			return 0;
		}
		
		
		while(!q.isEmpty()) {
			String hold = q.remove();
			
			// if num push to operand stack
			if(checkIfNum(hold)) {
				operands.push(Double.parseDouble(hold));
			}
			// perform operation
			else if(operands.peek() != null){
				char c = hold.charAt(0);
				
				Double num1 = operands.pop();
				Double num2 = operands.pop();

				switch(c) { 
				case '+':
					operands.push(num1 + num2);
					break;
                case '-':
                    operands.push(num2-num1);
                    break;
                case '/':
                    Double div = num2/num1;
                    if(div.isNaN() || div == Double.NEGATIVE_INFINITY || div == Double.POSITIVE_INFINITY) {
                    	System.out.println("Error: Division by 0");
                    	return 0.0;
                    }
                    else operands.push(div);
                    break;
                case '*':
                    operands.push(num1*num2);
                    break;
                case '^':
                	Double test = Math.pow(num2, num1);
                	if(test.isNaN()) {
                		System.out.println("Error: Invalid power");
                		return 0;
                	}
                	else
                		operands.push(test);
                	
                	break;
                default:
                	System.out.println("Error: Invalid operation");
                	break;
				}
			}
			
			
		}
		
		return operands.peek();
	}
	
	/** Checks if '-' is a unary operator
	 * @param index
	 * @return true2/false
	 */
	public boolean isNegative(int i) {
		boolean status = false;
		if(i > 0) {
			if(expr.charAt(i-1) == '+' || expr.charAt(i-1) == '*' || expr.charAt(i-1) == '/' 
					|| expr.charAt(i-1) == '-' || expr.charAt(i-1) == '^' || expr.charAt(i-1) =='(' || expr.charAt(i-1) == '(')
				status = true;
		}
		return status;
	}
	

}
