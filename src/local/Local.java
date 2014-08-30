package local;

import heap.Heap;
import heap.NoDir;
import base.Diretorio;
import base.No;

public class Local extends Diretorio {

	private ArqsLocais arqsLocal;
	protected static Heap heapLocal;
		
	public Local() {
		super();
		heapLocal = new Heap();
		arqsLocal = new ArqsLocais();
		iniciarHeap();
	}
	
	public Heap getHeap() {
		return heapLocal;
	}

	public void reiniciarHeap() {
		heapLocal.limparHeap();
		iniciarHeap();
	}
	
	protected void iniciarHeap() {
		String nome = entrada.getDirLocal().substring(17);
		NoDir no = new NoDir(nome, entrada.getDirLocal(), 0);
		heapLocal.inserirNo(no);
		no = (NoDir) heapLocal.getNo(0);
		no.setQtd(insContDir(entrada.getDirLocal()));
	}
	
	protected int insContDir(String diretorio) {
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

}
