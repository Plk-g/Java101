//Referenced GeekForGeeks for code help

package PartI;

public class ComplexNumber {
	
	private double r; //Using r to rep real nos.
	private double i; //using i to rep imaginary nos.
	
	//constructor creation
	
	public ComplexNumber(double r, double i){
		this.r = r;
		this.i = i;
				
	}
	
	public double real() {
		return r;
	}
	
	public double imag() {
		return i;
	}
	public double mod() {
        return Math.hypot(r, i);
    }
	public ComplexNumber plus(ComplexNumber c) {
        return new ComplexNumber(r + c.r, i+ c.i);
    }
	public ComplexNumber over(ComplexNumber c) {
        double denom = c.r * c.r + c.i * c.i;
        if (denom == 0) {
            throw new ArithmeticException("Cannot divide by zero complex number");
        }
        double newRe = (r * c.r + i * c.i) / denom;
        double newIm = (i * c.r - r * c.i) / denom;
        return new ComplexNumber(newRe, newIm);
    }
	public ComplexNumber minus(ComplexNumber c) {
        return new ComplexNumber(r - c.r, i - c.i);
    }
	public ComplexNumber times(ComplexNumber c) {
        double newRe = r * c.r - i * c.i;
        double newIm = r * c.i + i * c.r;
        return new ComplexNumber(newRe, newIm);
    }
	
	@Override
    public String toString() {
        if (i >= 0) {
            return r + " + i" + i;
        } else {
            return r + " - i" + (-i); 
        }
    }
	public static void main(String[] args) {
        ComplexNumber first = new ComplexNumber(7.5, 4.2);
        ComplexNumber second = new ComplexNumber(8.2, 9.4);

        System.out.println("First: " + first);
        System.out.println("Second: " + second);

        System.out.println("Sum: " + first.plus(second));
        System.out.println("Difference: " + first.minus(second));
        System.out.println("Product: " + first.times(second));
        System.out.println("Quotient: " + first.over(second));
        System.out.println("|First| = " + first.mod());
    }



	
}
