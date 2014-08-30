package testes.servidor;

import servidor.ArqsFTP;
import heap.Heap;
import heap.NoDir;
import local.ArqEntrada;

public class TesteArqsFTP {

	private ArqsFTP arqsFTP;
	private ArqEntrada entrada;
	private static Heap heapRemoto;

	public TesteArqsFTP() {
		entrada = new ArqEntrada();
		arqsFTP = new ArqsFTP();
		heapRemoto = new Heap();
		iniciarHeap();
		imprimirHeap();
	}

	private void iniciarHeap() {
		NoDir no = new NoDir(entrada.getDirRemoto(), entrada.getDirRemoto(), 0);
		heapRemoto.inserirNo(no);
		no = ((NoDir) heapRemoto.getNo(0));
		no.setQtd(insContDir(entrada.getDirRemoto()));
	}

	private int insContDir(String diretorio) {
		int tamAnt = heapRemoto.getTam();
		int tamIns = arqsFTP.construirHeap(diretorio);
		heapRemoto.inserirDoHeap(arqsFTP.getHeap());
		for (int i = tamAnt; i < tamAnt+tamIns; i++) {
			if (heapRemoto.getNo(i).getNome().endsWith("/")) {
				((NoDir) heapRemoto.getNo(i)).setQtd(insContDir(diretorio
						+ heapRemoto.getNo(i).getNome()));
			}
		}
		return tamIns;
	}

	private void imprimirHeap() {
		System.out.println(heapRemoto);
	}

	public static void main(String[] args) {
		new TesteArqsFTP();
	}
}
