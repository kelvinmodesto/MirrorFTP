package base;

import heap.Heap;
import local.ArqEntrada;

public abstract class Diretorio {

	protected ArqEntrada entrada;
	protected static Heap heap;

	public Diretorio() {
		entrada = new ArqEntrada();
		heap = new Heap();
	}

	public Heap getHeap() {
		return heap;
	}

	public void reiniciarHeap() {
		heap.limparHeap();
		iniciarHeap();
	}

	protected abstract void iniciarHeap();

	protected abstract int insContDir(String diretorio);

}
