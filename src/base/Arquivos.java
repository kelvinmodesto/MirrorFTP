package base;

import heap.Heap;

public abstract class Arquivos {

	protected Heap heap;

	public Arquivos() {
		heap = new Heap();
	}

	public Heap getHeap() {
		return heap;
	}

	public abstract int construirHeap(String diretorio);
	
}
