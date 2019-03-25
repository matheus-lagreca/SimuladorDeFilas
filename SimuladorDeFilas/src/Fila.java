//Matheus lagreca
public class Fila {
// Filas g/g/x/y
// x = servidores
// y = capacidade de clientes
// z = qnt de clientes na fila
	private int servidores;// x
	private int cap;// y
	private int tempoChegadaMin;
	private int tempoChegadaMax;
	private int tempoAtendimentoMin;
	private int tempoAtendimentoMax;
	private int agora;//qnt de pessoas na fila atualmente

	public Fila(int servidores, int cap, int tempoChegadaMin, int tempoChegadaMax, int tempoAtendimentoMin,	int tempoAtendimentoMax) {
		this.servidores = servidores;
		this.cap = cap;
		this.tempoChegadaMin = tempoChegadaMin;
		this.tempoChegadaMax = tempoChegadaMax;
		this.tempoAtendimentoMin = tempoAtendimentoMin;
		this.tempoAtendimentoMax = tempoAtendimentoMax;
		this.agora = 0;//fila comeca em zero

	}

	public int getServidores() {
		return servidores;
	}

	public void setServidores(int servidores) {
		this.servidores = servidores;
	}

	public int getCap() {
		return cap;
	}

	public void setCap(int cap) {
		this.cap = cap;
	}

	public int getTempoChegadaMin() {
		return tempoChegadaMin;
	}

	public void setTempoChegadaMin(int tempoChegadaMin) {
		this.tempoChegadaMin = tempoChegadaMin;
	}

	public int getTempoChegadaMax() {
		return tempoChegadaMax;
	}

	public void setTempoChegadaMax(int tempoChegadaMax) {
		this.tempoChegadaMax = tempoChegadaMax;
	}

	public int getTempoAtendimentoMin() {
		return tempoAtendimentoMin;
	}

	public void setTempoAtendimentoMin(int tempoAtendimentoMin) {
		this.tempoAtendimentoMin = tempoAtendimentoMin;
	}

	public int getTempoAtendimentoMax() {
		return tempoAtendimentoMax;
	}

	public void setTempoAtendimentoMax(int tempoAtendimentoMax) {
		this.tempoAtendimentoMax = tempoAtendimentoMax;
	}

	public int getAgora() {
		return agora;
	}

	public void setAgora(int agora) {
		this.agora = agora;
	}

	@Override
	public String toString() {
		return "Fila [servidores=" + servidores + ", cap=" + cap + ", tempoChegadaMin=" + tempoChegadaMin
				+ ", tempoChegadaMax=" + tempoChegadaMax + ", tempoAtendimentoMin=" + tempoAtendimentoMin
				+ ", tempoAtendimentoMax=" + tempoAtendimentoMax + ", agora=" + agora + "]";
	}
	
	

}
