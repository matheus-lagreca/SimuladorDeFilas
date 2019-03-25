import java.util.ArrayList;
import java.util.Scanner;
//Matheus lagreca
public class simulador {
	static Scanner entrada = new Scanner(System.in);
	static GeradorNumerosAleatorios ger = new GeradorNumerosAleatorios();
	static Fila fil;
	static ArrayList<Double> estadoFila = new ArrayList<>();
	static ArrayList<Evento> listaEvento = new ArrayList<>();
	static ArrayList<Double> numerosAleatorios = new ArrayList<>();
	static double tempoTotal = 0; // diferenca entre tempos
	static double tempoDecorrido = 0; // tempo real
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
		for (int i = 0; i < 100000; i++) {
			numerosAleatorios.add(ger.recebeRandomEntre(0, 1));
		}

		// seta o array para ter o tamanho da fila
		for (int i = 0; i < capa + 1; i++) {
			estadoFila.add(0.0);
		}

		filaSimples(fil, estInic);

		for (int i = 0; i <= fil.getCap(); i++) {

			System.out.println("Tempo total que " + i + " ficaram na fila: " + estadoFila.get(i));
		}
		System.out.println("perdas: " + perda);
		System.out.println("tempo Total: " + tempoDecorrido);
	}
	// metodos utilizados na simulação

	// executa algoritmo fila simples
	private static void filaSimples(Fila fi, double inicio) {
		tempoDecorrido = 0;
		chegada(inicio);
		double menorTempo = 0;
		int posMenor = 0;

		// enquanto ainda existirem numeros aleatorios na lista...
		while (!numerosAleatorios.isEmpty()) {
			// verifica qual evento vem primeiro e executa
			// verifica se fila esta cheia e o proximo evento n for chegada
			// consume evento e agenda chegada
			if (fil.getAgora() == fil.getCap()) {

				menorTempo = listaEvento.get(0).getTempo();
				posMenor = 0;
				for (int i = 0; i < listaEvento.size(); i++) {
					// pega a saida com menor tempo
					if (listaEvento.get(posMenor).getTempo() > listaEvento.get(i).getTempo()) {
						menorTempo = listaEvento.get(i).getTempo();
						posMenor = i;
					}
				}
				// se evento n for saida
				if (listaEvento.get(posMenor).getTipo() == 1) {
					perda++;

					listaEvento.remove(posMenor);
					chegada(menorTempo);
				} else {
					saida(menorTempo);
					listaEvento.remove(posMenor);
				}

			} else {
				// caso fila nao cheia
				menorTempo = listaEvento.get(0).getTempo();
				posMenor = 0;
				for (int i = 0; i < listaEvento.size(); i++) {
					if (listaEvento.get(posMenor).getTempo() > listaEvento.get(i).getTempo()) {
						menorTempo = listaEvento.get(i).getTempo();
						posMenor = i;
					}

				}

				// chegada
				if (listaEvento.get(posMenor).getTipo() == 1) {

					chegada(menorTempo);
					listaEvento.remove(posMenor);

				}
				// saida
				else if (listaEvento.get(posMenor).getTipo() == 0) {
					saida(menorTempo);
					listaEvento.remove(posMenor);
				}
				menorTempo = 0;
				posMenor = 0;
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
		double result = tempoDecorrido
				+ (((fil.getTempoChegadaMax() - fil.getTempoChegadaMin()) * aux) + fil.getTempoChegadaMin());
		Evento e = new Evento(1, result);
		listaEvento.add(e);
	}

	// agenda saida
	private static void agendaSaida() {
		double aux = numerosAleatorios.remove(0);
		double result = tempoDecorrido + (((fil.getTempoAtendimentoMax() - fil.getTempoAtendimentoMin()) * aux)
				+ fil.getTempoAtendimentoMin());

		Evento e = new Evento(0, result);
		listaEvento.add(e);

	}

	// contabilizatempo
	private static void contabilizaTempo(double tempo) {
		// usa uma array que guarda quanto tempo cada posicao da fila teve pessoas
		// pos 0 = vazia, pos 1 =1 pessoas
		// o array deve guardar apenas o tempo
		int aux = fil.getAgora();

		// atualiza tempo decorrido
		// calcula dif decorrido
		// posicao += tempo da posicao anterior + dif do tempo anterior e o tempo
		// decorrido

		// guarda tempoDecorrido anterior -->0
		// verifica tempo novo --> 3
		// e coloca que a posicaoAgora --> diferenca entra o tempo novo e o decorrido

		double tempoAnterior = tempoDecorrido;
		tempoDecorrido = tempo;
		double posTemAux = tempoDecorrido - tempoAnterior;
		double tempoAux = estadoFila.get(aux) + posTemAux;
		estadoFila.set(aux, tempoAux);

		/*
		 * double tempoAnterior = tempoDecorrido; double tempoAux = Math.abs(tempo -
		 * tempoDecorrido); //dif tempoDecorrido = tempo;
		 * 
		 * double tempoPosAux = estadoFila.get(aux); tempoPosAux = tempoPosAux +
		 * tempoAux; //posicao da fila recebe a diferenca do tempo total
		 * 
		 * estadoFila.set(aux, tempoPosAux);
		 * 
		 * 
		 */

		/*
		 * System.out.println(" "); System.out.println("a fila ficou com  " + aux +
		 * " pessoas por " + tempoPosAux + " e o tempo decorrido eh  " +
		 * tempoDecorrido);
		 */

		// atualizar corretamente o array
		// ele ta resetando o tempo da posicao em vez de incrementar******

	}

}
