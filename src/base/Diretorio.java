package base;

import local.ArqEntrada;

public abstract class Diretorio {

	protected ArqEntrada entrada;

	public Diretorio() {
		entrada = new ArqEntrada();
	}

	protected abstract void iniciarHeap();

	protected abstract int insContDir(String diretorio);

}
