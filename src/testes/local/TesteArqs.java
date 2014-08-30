package testes.local;

import heap.Heap;
import heap.NoDir;
import base.No;
import local.ArqEntrada;
import local.ArqsLocais;

public class TesteArqs {
	private ArqsLocais arqsLocal;
	private ArqEntrada entrada;
	private static Heap heapLocal;

	public TesteArqs() {
		entrada = new ArqEntrada();
		arqsLocal = new ArqsLocais();
		heapLocal = new Heap();
		iniciarHeap();
		imprimirHeap();
	}

	private void iniciarHeap() {
		String nome = entrada.getDirLocal().substring(17);
		NoDir no = new NoDir(nome, entrada.getDirLocal(), 0);
		heapLocal.inserirNo(no);
		no = (NoDir) heapLocal.getNo(0);
		no.setQtd(insContDir(entrada.getDirLocal()));
	}

	private int insContDir(String diretorio) {
		int tamAnt = heapLocal.getTam();
		int tamIns = arqsLocal.construirHeap(diretorio);
		heapLocal.inserirDoHeap(arqsLocal.getHeap());
		for (int i = tamAnt; i < tamAnt+tamIns; i++) {
			No no = heapLocal.getNo(i);
			if (no.getNome().endsWith("/")) {
				int qtd = insContDir(diretorio + no.getNome());
				((NoDir) no).setQtd(qtd);
			}
		}
		return tamIns;
	}

	public void imprimirHeap() {
		System.out.println(heapLocal);
	}

	public static void main(String[] args) {
		new TesteArqs();
	}
}
