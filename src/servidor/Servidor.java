package servidor;

import heap.Heap;
import heap.NoDir;
import base.Diretorio;

public class Servidor extends Diretorio {

	private ArqsFTP arqsFTP;
	protected static Heap heapRemoto;

	public Servidor() {
		super();
		heapRemoto = new Heap();
		arqsFTP = new ArqsFTP();
		iniciarHeap();
	}

	public Heap getHeap() {
		return heapRemoto;
	}

	public void reiniciarHeap() {
		heapRemoto.limparHeap();
		iniciarHeap();
	}

	protected void iniciarHeap() {		
		NoDir no = new NoDir(entrada.getDirRemoto(), entrada.getDirRemoto(), 0);
		heapRemoto.inserirNo(no);
		no = ((NoDir) heapRemoto.getNo(0));
		no.setQtd(insContDir(entrada.getDirRemoto()));
	}

	protected int insContDir(String diretorio) {
		int tamAnt = heapRemoto.getTam();
		int tamIns = arqsFTP.construirHeap(diretorio);
		heapRemoto.inserirDoHeap(arqsFTP.getHeap());
		for (int i = tamAnt; i < tamAnt + tamIns; i++) {
			if (heapRemoto.getNo(i).getNome().endsWith("/")) {
				((NoDir) heapRemoto.getNo(i)).setQtd(insContDir(diretorio
						+ heapRemoto.getNo(i).getNome()));
			}
		}
		return tamIns;
	}

}
