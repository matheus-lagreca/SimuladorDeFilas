import java.util.ArrayList;
import java.util.Scanner;

public class simulador {
	static Scanner entrada = new Scanner(System.in);
	static GeradorNumerosAleatorios ger = new GeradorNumerosAleatorios();
	static Fila fil;
	static ArrayList<Double> estadoFila = new ArrayList<>();
	static ArrayList<Evento> listaEvento = new ArrayList<>();
	static ArrayList<Double> numerosAleatorios = new ArrayList<>();
	static double tempoTotal = 0;
	static int perda = 0; // qnts chegadas cairam

	/*
	 * validar G/G/1/5, chegadas entre 3..5, atendimento entre 4..6 G/G/2/5,
	 * chegadas entre 3..5, atendimento entre 4..6
	 */

	public static void main(String[] args) {
		// setup da fila
		System.out.println("Digite a quantidade de servidores da fila: ");
		int serv = entrada.nextInt();
		System.out.println("Digite a capacidade maxima da fila: ");
		int capa = entrada.nextInt();
		System.out.println("Digite o tempo de chegada min: ");
		int cheMin = entrada.nextInt();
		System.out.println("Digite o tempo de chegada max: ");
		int cheMax = entrada.nextInt();
		System.out.println("Digite o tempo de atendimento min: ");
		int ateMin = entrada.nextInt();
		System.out.println("Digite o tempo de atendimento max: ");
		int ateMax = entrada.nextInt();

		fil = new Fila(serv, capa, cheMin, cheMax, ateMin, ateMax);

		System.out.println(fil.toString());

		// setup das demais configs
		System.out.println("Digite o estado inicial de chegada: ");
		double estInic = entrada.nextDouble();

		// esse for controla quantos nros aleatorios existem
		for (int i = 0; i < 10; i++) {
			numerosAleatorios.add(ger.recebeRandomEntre(0, 1));
		}

		// seta o array para ter o tamanho da fila
		for (int i = 0; i < capa; i++) {
			estadoFila.add(0.0);
		}

		filaSimples(fil, estInic);

	}
	// metodos utilizados na simulação

	// executa algoritmo fila simples
	private static void filaSimples(Fila fi, double inicio) {
		// double tempoAtual = 0;
		chegada(inicio);

		// enquanto ainda existirem numeros aleatorios na lista...
		while (!numerosAleatorios.isEmpty()) {
			// verifica qual evento vem primeiro e executa
			double menorTempo = 0;
			int posMenor = 0;
			for (int i = 0; i < listaEvento.size(); i++) {
				if (listaEvento.get(posMenor).getTempo() > listaEvento.get(i).getTempo()) {
					menorTempo = listaEvento.get(i).getTempo();
					posMenor = i;
				}

			}
			// chegada
			if (listaEvento.get(posMenor).getTipo() == 1) {
				if (fil.getAgora() == fil.getCap()) {
					perda++;

				} else {
					chegada(menorTempo);
					listaEvento.remove(posMenor);

				}

			}
			// saida
			if (listaEvento.get(posMenor).getTipo() == 0) {
				saida(menorTempo);
				listaEvento.remove(posMenor);

			}
		}

	}

	// chegada
	private static void chegada(double tempo) {
		int posFila = fil.getAgora();
		contabilizaTempo(tempo);

		if (fil.getAgora() < fil.getCap()) {
			fil.setAgora(posFila + 1);
			if (fil.getAgora() <= fil.getServidores()) {
				agendaSaida();
			}
		}
		agendaChegada();
	}

	// saida
	private static void saida(double tempo) {
		contabilizaTempo(tempo);
		int posFila = fil.getAgora();
		fil.setAgora(posFila - 1);
		if (fil.getAgora() >= fil.getServidores()) {
			agendaSaida();
		}
	}

	// agenda chegada
	private static void agendaChegada() {
		double aux = numerosAleatorios.remove(0);
		double result = (fil.getTempoChegadaMax() - fil.getTempoChegadaMin() * aux) + fil.getTempoChegadaMin();
		Evento e = new Evento(1, result);
		listaEvento.add(e);
	}

	// agenda saida
	private static void agendaSaida() {
		double aux = numerosAleatorios.remove(0);
		double result = (fil.getTempoAtendimentoMax() - fil.getTempoAtendimentoMin() * aux)
				+ fil.getTempoAtendimentoMin();

		Evento e = new Evento(0, result);
		listaEvento.add(e);

	}

	// contabilizatempo
	private static void contabilizaTempo(double tempo) {
		// usa uma array que guarda quanto tempo cada posicao da fila teve pessoas
		// pos 0 = vazia, pos 1 =1 pessoas
		// o array deve guardar apenas o tempo

		int aux = fil.getAgora();
		tempoTotal = tempoTotal + tempo;
		estadoFila.set(aux, tempo - aux);

	}

}
