package servidor;

import heap.NoDir;
import base.Diretorio;

public class Servidor extends Diretorio {

	private ArqsFTP arqsFTP;

	public Servidor() {
		super();
		arqsFTP = new ArqsFTP();
		iniciarHeap();
	}

	protected void iniciarHeap() {		
		NoDir no = new NoDir(entrada.getDirRemoto(), entrada.getDirRemoto(), 0);
		heap.inserirNo(no);
		no = ((NoDir) heap.getNo(0));
		no.setQtd(insContDir(entrada.getDirRemoto()));
	}

	protected int insContDir(String diretorio) {
		int tamAnt = heap.getTam();
		int tamIns = arqsFTP.construirHeap(diretorio);
		heap.inserirDoHeap(arqsFTP.getHeap());
		for (int i = tamAnt; i < tamAnt + tamIns; i++) {
			if (heap.getNo(i).getNome().endsWith("/")) {
				((NoDir) heap.getNo(i)).setQtd(insContDir(diretorio
						+ heap.getNo(i).getNome()));
			}
		}
		return tamIns;
	}

}
