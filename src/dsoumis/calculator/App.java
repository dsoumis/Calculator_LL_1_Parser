package dsoumis.calculator;

import java.io.IOException;

public class App {

	public static void main(final String[] args) throws IOException, ParseError {
		final CalculationsEvaluator calculationsEvaluator = new CalculationsEvaluator(System.in);
		System.out.println(calculationsEvaluator.eval());
	}
}
