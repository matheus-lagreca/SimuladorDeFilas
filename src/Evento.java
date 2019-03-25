
public class Evento {
	private int tipo;// chegada=1  saida=0
	private double tempo;
	
	public Evento(int tipo, double tempo) {
		super();
		this.tipo = tipo;
		this.tempo = tempo;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public double getTempo() {
		return tempo;
	}
	public void setTempo(double tempo) {
		this.tempo = tempo;
	}

	

}
