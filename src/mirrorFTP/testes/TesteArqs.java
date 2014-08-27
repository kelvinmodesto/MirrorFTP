package mirrorFTP.testes;

import java.util.List;

import mirrorFTP.heap.Heap;
import mirrorFTP.local.ArqEntrada;
import mirrorFTP.local.ArqsLocais;

public class TesteArqs {
	private ArqEntrada entrada;
	private ArqsLocais arq;
	Heap heapLocal;

	public TesteArqs() {
		entrada = new ArqEntrada();
		arq = new ArqsLocais();
		heapLocal = gerarHeap(entrada.getDirLocal());
		imprimirHeap();
		// varrerPasta(entrada.getDirLocal());
	}

	public void varrerPasta(String pasta) {
		List<String> lista = arq.getConteudo(pasta);
		System.out.println("Listando o conteudo da pasta " + pasta);
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).startsWith("*"))
				varrerPasta(pasta + lista.get(i).substring(1) + "/");
			else
				System.out.println(lista.get(i));
		}
		System.out.println("Fim da listagem da pasta " + pasta);
	}

	public Heap gerarHeap(String pasta) {
		Heap aux = arq.construirHeapLocal(pasta);
		for (int i = 0; i < aux.getTam(); i++) {
			if (aux.getNo(i).getNome().startsWith("*"))
				aux.inserirDoHeap(gerarHeap(pasta
						+ aux.getNo(i).getNome().substring(1) + "/"));
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
