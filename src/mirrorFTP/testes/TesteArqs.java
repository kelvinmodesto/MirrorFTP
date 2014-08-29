package mirrorFTP.testes;

import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoDir;
import mirrorFTP.local.ArqEntrada;
import mirrorFTP.local.ArqsLocais;

public class TesteArqs {
	private ArqsLocais arqs;
	private ArqEntrada entrada;
	private Heap heapLocal;

	public TesteArqs() {
		entrada = new ArqEntrada();
		arqs = new ArqsLocais();
		heapLocal = new Heap();
		heapLocal.inserirNo(new NoDir(entrada.getDirLocal(), entrada
				.getDirLocal().substring(17), 0));
		heapLocal.inserirDoHeap(gerarHeap(entrada.getDirLocal()));
		imprimirHeap();
	}

	public Heap gerarHeap(String pasta) {
		Heap aux = arqs.construirHeapLocal(pasta);
		String[] partes = pasta.split("/");
		NoDir no = (NoDir) heapLocal.getNo(partes[partes.length - 1] + "/");
		if (no != null)
			no.setQtd(aux.getTam());
		for (int i = 0; i < aux.getTam(); i++) {
			if (aux.getNo(i).getNome().endsWith("/"))
				aux.inserirDoHeap(gerarHeap(pasta + aux.getNo(i).getNome()));
		}
		return aux;
	}

	public void imprimirHeap() {
		System.out.println(heapLocal);
	}

	public static void main(String[] args) {
		new TesteArqs();
	}
}
