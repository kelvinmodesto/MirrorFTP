package local;

import heap.NoDir;
import base.Diretorio;
import base.No;

public class Local extends Diretorio {

	private ArqsLocais arqsLocal;
	
	public Local() {
		super();
		arqsLocal = new ArqsLocais();
		iniciarHeap();
	}
	
	protected void iniciarHeap() {
		String nome = entrada.getDirLocal().substring(17);
		NoDir no = new NoDir(nome, entrada.getDirLocal(), 0);
		heap.inserirNo(no);
		no = (NoDir) heap.getNo(0);
		no.setQtd(insContDir(entrada.getDirLocal()));
	}
	
	protected int insContDir(String diretorio) {
		int tamAnt = heap.getTam();
		int tamIns = arqsLocal.construirHeap(diretorio);
		heap.inserirDoHeap(arqsLocal.getHeap());
		for (int i = tamAnt; i < tamAnt+tamIns; i++) {
			No no = heap.getNo(i);
			if (no.getNome().endsWith("/")) {
				int qtd = insContDir(diretorio + no.getNome());
				((NoDir) no).setQtd(qtd);
			}
		}
		return tamIns;
	}

}
