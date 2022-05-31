package postech.adms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public static void main(String args[]) {
		String regEx = "\\d{4}-\\d{2}-\\d{2}";
		Matcher matcher = Pattern.compile(regEx).matcher("199-01-01");

		System.out.println(matcher.matches());

		System.out.println("7105151".substring(6,7));
	}
	
}
