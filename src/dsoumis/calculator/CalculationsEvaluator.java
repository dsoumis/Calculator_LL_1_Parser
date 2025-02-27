package dsoumis.calculator;

import java.io.IOException;
import java.io.InputStream;

public class CalculationsEvaluator {
	private int lookaheadToken;

	private final InputStream in;

	public CalculationsEvaluator(final InputStream in) throws IOException {
		this.in = in;
		this.lookaheadToken = in.read();
	}

	private void consume(final int symbol) throws IOException, ParseError {
		if (this.lookaheadToken != symbol) {
			throw new ParseError();
		}
		this.lookaheadToken = this.in.read();
	}

	private int evalDigit(final int digit) {
		return digit - '0';
	}

	private boolean isLookaheadTokenEof() {
		if (this.lookaheadToken == '\n' || this.lookaheadToken == -1 || this.lookaheadToken == 13) {
			return true;
		} else {
			return false;
		}
	}

	private int exp() throws IOException, ParseError {
		/* Match expr −−> term exprTail */
		if (this.lookaheadToken >= '0' && this.lookaheadToken <= '9' || this.lookaheadToken == '(') {
			final int termValue = this.term();
			return this.expTail(termValue);
		}
		throw new ParseError();
	}

	private int expTail(final int termValueParam) throws IOException, ParseError {
		/* Match expTail −−> + term expTail */
		if (this.lookaheadToken == '+') {
			this.consume('+');
			final int termValue = this.term();
			return termValueParam + this.expTail(termValue);

			/* Match expTail −−> - term expTail */
		} else if (this.lookaheadToken == '-') {
			this.consume('-');
			final int termValue = this.term();
			return termValueParam - this.expTail(termValue);

			/* Match exprTail --> empty */
		} else if (this.lookaheadToken == ')' || this.isLookaheadTokenEof()) {

			return termValueParam;
		}
		throw new ParseError();
	}

	private int term() throws IOException, ParseError {
		if (this.lookaheadToken >= '0' && this.lookaheadToken <= '9' || this.lookaheadToken == '(') {
			/* Match term −−> num termTail */
			final int numValue = this.num();
			return this.termTail(numValue);
		}
		throw new ParseError();
	}

	private int termTail(final int numValueParam) throws IOException, ParseError {
		/* Match termTail −−> * num termTail */
		if (this.lookaheadToken == '*') {
			this.consume('*');
			final int numValue = this.num();
			return numValueParam * this.termTail(numValue);

			/* Match termTail −−> / num termTail */
		} else if (this.lookaheadToken == '/') {
			this.consume('/');
			final int numValue = this.num();
			return numValueParam / this.termTail(numValue);

			/* Match termTail --> empty */
		} else if (this.lookaheadToken == ')' || this.isLookaheadTokenEof() || this.lookaheadToken == '+'
				|| this.lookaheadToken == '-') {
			return numValueParam;
		}
		throw new ParseError();
	}

	private int num() throws IOException, ParseError {
		/* Match num --> digit */
		if (this.lookaheadToken >= '0' && this.lookaheadToken <= '9') {
			final int digit = this.evalDigit(this.lookaheadToken);
			this.consume(this.lookaheadToken);
			final StringBuilder concatenatedDigits = new StringBuilder();
			concatenatedDigits.append(String.valueOf(digit));
			return this.numTail(concatenatedDigits);
			/* Match num --> (exp) */
		} else if (this.lookaheadToken == '(') {
			this.consume('(');
			final int expValue = this.exp();
			this.consume(')');
			return expValue;
		}
		throw new ParseError();
	}

	private int numTail(final StringBuilder concatenatedDigits) throws IOException, ParseError {
		/* Match numTail --> digit numTail */
		if (this.lookaheadToken >= '0' && this.lookaheadToken <= '9') {
			final int digit = this.evalDigit(this.lookaheadToken);
			this.consume(this.lookaheadToken);
			concatenatedDigits.append(String.valueOf(digit));
			return this.numTail(concatenatedDigits);
			/* Match numTail --> empty */
		} else if (this.lookaheadToken == ')' || this.isLookaheadTokenEof() || this.lookaheadToken == '+'
				|| this.lookaheadToken == '-' || this.lookaheadToken == '*' || this.lookaheadToken == '/') {
			return Integer.valueOf(concatenatedDigits.toString());
		}
		throw new ParseError();
	}

	public int eval() throws IOException, ParseError {
		final int rv = this.exp();
		if (!this.isLookaheadTokenEof()) {
			throw new ParseError();
		}
		return rv;
	}
}
