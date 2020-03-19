package dsoumis.calculator;

public class ParseError extends Exception {

	private static final long serialVersionUID = 8288334792938155440L;

	@Override
	public String getMessage() {
		return "parse errror";
	}
}