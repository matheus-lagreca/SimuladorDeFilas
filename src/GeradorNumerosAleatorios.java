import java.util.Random;

public class GeradorNumerosAleatorios {
	//
	static Random ran = new Random();
	public GeradorNumerosAleatorios() {
		
	}
	public double recebeRandomEntre(double low, double high) {

		double result = ran.nextDouble() * (high - low) + low;

		return result;
	}
}
