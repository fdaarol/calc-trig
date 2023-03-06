/* 
 * CS003B Term Project
 * Copyright (2019), Frodese Daarol
 */
import java.util.*;

public class main {

	public static void main(String[] args) {
		
		Scanner read = new Scanner(System.in);
		Basic b = new Basic();
		Scientific s = new Scientific();
		Double ans = 0.0;
		String ex = null;
		
		while(true) {
			System.out.println("[1] BASIC Calculator\n[2] SCIENTIFIC Calculator\n[3] y = mx+b\n[0] to quit");
			int in = read.nextInt();
			String ignore = read.nextLine();
			
			// exit
			if(in == 0) {
				System.out.println("Thank you!");
				break;
			}
			
			// invalid option
			else if(in != 1 && in != 2 && in != 3)
				System.out.println("Invalid menu option");
			
			// graph
			else if(in == 3) {
				String slope;
				String yInt;
				
				System.out.println("Input slope(m): ");
				slope = read.nextLine();
				System.out.println("Input y-intercept(b): ");
				yInt = read.nextLine();
				
				
				LinearGraph lg = new LinearGraph(b.compRPN(slope), b.compRPN(yInt));

			}
			else {
				System.out.println("Input expression or (q) to go back to menu");
				ex = read.nextLine();
			}

			// basic
			if(in == 1) {				
				while(!ex.equals("q")){	
					ans = b.compRPN(ex);
					System.out.println(" = " + ans);
					ex = read.nextLine();
					if(b.isOperator(ex.charAt(0))) 
						ex = Double.toString(ans) + ex;	
				}
			}
			
			// scientific
			else if(in == 2) {
				while(!ex.equals("q")){				
					ans = s.comp(ex);
					System.out.println(" = " + ans);
					ex = read.nextLine();
					if(b.isOperator(ex.charAt(0))) 
						ex = Double.toString(ans) + ex;				
				};
				
				System.out.println();
			}
			

		
			
		}
		
		read.close();
		
	}
	

	
}

