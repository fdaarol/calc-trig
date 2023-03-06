import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
public class Scientific extends Basic{
	Pattern pattern = Pattern.compile("(sin|cos|tan|log|ln|sqrt|cube)\\[[^\\[]*\\]");
	Pattern ex = Pattern.compile("\\[(.*)\\]");

	String sciExpr; // holds matched scientific expression

	/** Computers expression
	 * @param string expression
	 * @return answer or 0 if evaluation fails
	 */
	public Double comp(String in) {
		sciExpr = in;
		
//		Basic a = new Basic();
		Matcher trigMatch = pattern.matcher(sciExpr);
		String temp;

		while(trigMatch.find()) {
			
			temp = trigMatch.group();
			String encloseTrig = null;

			Matcher evalMatch = ex.matcher(trigMatch.group());

			evalMatch.find();
			String comp = evalMatch.group(1);
			Double x = compRPN(comp);
			
			if(temp.substring(0,2).equals("si")) 
				encloseTrig = enclose(convert(Math.sin(x)));

			else if(temp.charAt(0) == 'c') 
				encloseTrig = enclose(convert(Math.cos(x)));


			else if(temp.charAt(0) == 't') 
				encloseTrig = enclose(convert(Math.sin(x)/Math.cos(x)));

			else if(temp.substring(0,3).equals("log")) {
				if(compRPN(comp) == 0.0) {
					System.out.println("Invalid input!");
					return 0.0;
				}
				
				encloseTrig = enclose(convert(Math.log10(compRPN(comp))));
			}

			else if(temp.substring(0,2).equals("ln")) {
				if(compRPN(evalMatch.group(1)) == 0.0) {
					System.out.println("Invalid input!");
					return 0.0;
				}
				encloseTrig = enclose(convert(Math.log(compRPN(comp))));
			}
			
			else if(temp.substring(0,2).equals("sq")) {
				if(compRPN(evalMatch.group(1)) < 0.0) {
					System.out.println("Invalid input!");
					return 0.0;
				}
				encloseTrig = enclose(convert(Math.sqrt(compRPN(comp))));
			}
			
			sciExpr = sciExpr.replace(trigMatch.group(), encloseTrig);
		}

		
		return compRPN(sciExpr);

	}

	/** Encloses passed string in parentheses
	 * @param String
	 * @return updated string
	 */
	public String enclose(String s) {
		return '(' + s + ')';
	}

	/** Converts double to string
	 * @param double
	 * @return string
	 */
	public String convert(Double x) {
		return Double.toString(x);
	}
	

}
