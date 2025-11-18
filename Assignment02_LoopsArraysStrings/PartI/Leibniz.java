
public class  Leibniz {

	public static double estimatePi() {
		double sum = 0.0;
		int n = 0;
		
		while(Math.abs(Math.PI - sum) > 0.00001) {
			if (n % 2 == 0) {
				sum += 4.0 / (2 * n +1);
				
			}
			else {
				sum -= 4.0 / (2*n+1);
			}
			n++;
		}
		
		System.out.println("pi is estimated as " + sum + " after " + n + " iterations");
		return sum;
	}
	
	public static void main(String[] args) {
		double pi = estimatePi();
	}
}
