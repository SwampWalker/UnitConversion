package rationalNumbers;

import java.io.Serializable;

public class RationalNumber implements Serializable {
	/**
	 * The auto-generated serial version ID.
	 */
	private static final long serialVersionUID = -72742641139939878L;
	private int numerator, denominator;
	// Constructors.
	/**
	 * The standard constructor to initialize a rational number.
	 */
	public RationalNumber(int num, int den) {
		setNumerator(num);
		if (den == 0) {
			throw new IllegalArgumentException("The denominator must be nonzero in a rational number.");
		} else {
			setDenominator(den);
		}
		reduce();
	}
	/**
	 * Constructor to make an integer rational number.
	 * @param integer
	 */
	public RationalNumber(int integer) {
		setNumerator(integer);
		setDenominator(1);
	}
	/**
	 * Copy constructor.
	 * @param q, a <code>RationalNumber</code> to copy.
	 */
	public RationalNumber(RationalNumber q) {
		this(q.getNumerator(),q.getDenominator());
	}
	
	/**
	 * Returns the numerator of the rational number.
	 * 
	 * @return the <code>int</code> numerator.
	 */
	public int getNumerator() {
		return numerator;
	}
	
	/**
	 * Set the numerator to num.
	 * 
	 * @param num an <code>int</code> argument
	 * @return true.
	 */
	public boolean setNumerator(int num) {
		numerator = num;
		return true;
	}
	
	/**
	 * Returns the denominator of the rational number.
	 * 
	 * @return the <code>int</code> numerator.
	 */
	public int getDenominator() {
		return denominator;
	}
	
	/**
	 * Sets the denominator to den.
	 * 
	 * @param den, an <code>int</code> argument
	 * @return true if not equal to 0
	 */
	public boolean setDenominator(int den) {
		if (den != 0) {
			denominator = den;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isInteger() {
		if (getDenominator() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	// Some math methods.
	
	/**
	 * Inverts this rational number.
	 * 
	 * @return the inverse rational number
	 */
	public RationalNumber invert() {
		RationalNumber result = new RationalNumber(getDenominator(),getNumerator());
		result.reduce();
		return result;
	}
	
	/**
	 * Adds the rational number q to this rational number.
	 * @param q, a <code>RationalNumber</code>
	 * @return a rational number.
	 */
	public RationalNumber add(RationalNumber q) {
		RationalNumber result;
		int n = getNumerator()*q.getDenominator() + getDenominator()*q.getNumerator();
		int d = getDenominator()*q.getDenominator();
		result = new RationalNumber(n,d);
		result.reduce();
		return result;
	}
	/**
	 * Adds an integer to this rational number.
	 * @param i, the <code>int</code> to add to this rational number.
	 * @return RationalNumber
	 */
	public RationalNumber add(int i) {
		RationalNumber result = new RationalNumber(getNumerator() + i*getDenominator(),getDenominator());
		result.reduce();
		return result;
	}
	/**
	 * Multiply this rational number by the integer argument.
	 * @param i, an <code>int</code>
	 * @return a rational number which is the result.
	 */
	public RationalNumber multiply(int i) {
		RationalNumber result;
		int n = getNumerator()*i;
		int d = getDenominator();
		result = new RationalNumber(n,d);
		result.reduce();
		return result;
	}
	
	/**
	 * Returns this rational number multiplied by its argument.
	 * @param q, a rational number.
	 * @return a rational number.
	 */
	public RationalNumber multiply(RationalNumber q) {
		RationalNumber result;
		int n = getNumerator()*q.getNumerator();
		int d = getDenominator()*q.getDenominator();
		result = new RationalNumber(n,d);
		result.reduce();
		return result;
		}
	
	/**
	 * Subtract the rational number argument from this rational number. 
	 * @param q, a <code>RationalNumber</code>
	 * @return this rational minus the argument q.
	 */
	public RationalNumber subtract(RationalNumber q) {
		RationalNumber result = add(q.multiply(-1));
		return result;
	}
	
	/**
	 * Subtracts the integer argument from this rational number.
	 * 
	 * @param i, an <code>int </code>
	 * @return this rational minus i
	 */
	public RationalNumber subtract(int i) {
		RationalNumber result = add(-i);
		return result;
	}
	
	/**
	 * Divides this rational number by an integer.
	 * @param i, an <code>int</code>
	 * @return a rational number
	 */
	public RationalNumber divide(int i) {
		int n = getNumerator();
		int d = getDenominator()*i;
		RationalNumber result = new RationalNumber(n,d);
		result.reduce();
		return result;
	}
	
	/**
	 * Divides this rational number by the rational number argument.
	 * @param q, a rational number
	 * @return a rational number
	 */
	public RationalNumber divide(RationalNumber q) {
		RationalNumber result = multiply(q.invert());
		return result;
	}
	
	/**
	 * Returns the rational number raised to the power of the int argument
	 * @param i, an <code>int</code>
	 * @return a rational number
	 */
	public RationalNumber power(int i) {
		RationalNumber result;
		int n, d;
		if (i < 0) {
			result = this.invert();
			n = result.getNumerator();
			d = result.getDenominator();
			i = Math.abs(i);
		} else {
			n = getNumerator();
			d = getDenominator();
		}
		int numer = 1;
		int denom = 1;
		for (int j = 0; j < i; j++) {
			numer = numer*n;
			denom = denom*d;
		}
		result = new RationalNumber(numer,denom);
		return result;
	}
	
	/**
	 * Returns a double representation of the rational number.
	 * @return a <code>double</code>
	 */
	public double toDouble() {
		return (double)getNumerator()/(double)getDenominator();
	}
	
	/**
	 * Reduces the fraction so that the numerator and denominator are co-prime.
	 */
	public void reduce() {
		// Maybe this number is an integer...
		if (getNumerator() % getDenominator() == 0) {
			setNumerator(getNumerator()/getDenominator());
			setDenominator(1);
		// Move negatives to the numerator.
		} else if (getDenominator() < 0) {
			setDenominator(getDenominator()*(-1));
			setNumerator(getNumerator()*(-1));
		}
		int a = Math.abs(getNumerator());
		if ( a != 0) {
			int sign = getNumerator()/a;
			int b = Math.abs(getDenominator());
			int n = Math.min(a,b);
			boolean reduced = false;
			for (int i = 2; i <= n && !reduced ; i++) {
				if (a%i == 0 && b%i == 0) {
					setNumerator(sign*a/i);
					setDenominator(b/i);
					reduced=true;
					reduce();
				}
			}
		}
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (this.isInteger()) {
			sb.append(String.valueOf(getNumerator()));
		} else {
			sb.append(String.valueOf(getNumerator()));
			sb.append("/");
			sb.append(String.valueOf(getDenominator()));	
		}
		return (new String(sb));
	}
	
	public boolean isOne() {
		if (this.isInteger() && (this.getNumerator() == 1) ) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isZero() {
		if (this.isInteger() && (this.getNumerator() == 0) ) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if( o instanceof RationalNumber) {
			RationalNumber q = (RationalNumber) o;
			q.reduce();
			this.reduce();
			if (this.getDenominator() == q.getDenominator()) {
				if (this.getNumerator() == q.getNumerator()) {
					return true;
				}
			}
		}
		return false;
	}
}
