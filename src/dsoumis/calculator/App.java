package dsoumis.calculator;

import java.io.IOException;

public class App {
	private static void v(final StringBuilder s) {
		s.append("a");
	}

	public static void main(final String[] args) throws IOException, ParseError {
		final CalculationsEvaluator calculationsEvaluator = new CalculationsEvaluator(System.in);
		System.out.println(calculationsEvaluator.eval());
//		final int one = '1';
//		final int two = '2';
//		final String string;
//		string = String.valueOf(one - '0') + String.valueOf(two - '0');
//		System.out.println(string);
//		final int newint = Integer.valueOf(string);
//		System.out.println(newint);
//
//		final StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("b");
//		v(stringBuilder);
//		System.out.println(stringBuilder.toString());
	}
}
