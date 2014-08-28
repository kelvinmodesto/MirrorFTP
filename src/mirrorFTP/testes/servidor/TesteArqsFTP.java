package mirrorFTP.testes.servidor;

import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoDir;
import mirrorFTP.local.ArqEntrada;
import mirrorFTP.servidor.ArqsFTP;

public class TesteArqsFTP {

	private ArqsFTP arqsFTP;
	private ArqEntrada entrada;
	private static Heap heapRemoto;

	public TesteArqsFTP() {
		entrada = new ArqEntrada();
		arqsFTP = new ArqsFTP();
		heapRemoto = new Heap();
		heapRemoto.inserirNo(new NoDir(entrada.getDirRemoto(), 0));
		heapRemoto.inserirDoHeap(gerarHeap(entrada.getDirRemoto()));
		imprimirHeap();
	}

	private Heap setDirQTD(Heap heap, String pasta, int qtd) {
		String[] partes = pasta.split("/");
		NoDir no;
		if (partes.length > 0)
			no = (NoDir) heap.getNo(partes[partes.length - 1] + "/");
		else
			no = (NoDir) heap.getNo(pasta);
		if (no != null)
			no.setQtd(qtd);
		return heap;
	}

	// Nao seta a qtd das pastas internas
	public Heap gerarHeap(String pasta) {
		Heap aux = arqsFTP.construirHeapRemoto(pasta), aux0 = null;
		if (pasta.equals(entrada.getDirRemoto()))
			heapRemoto = setDirQTD(heapRemoto, pasta, aux.getTam());
		for (int i = 0; i < aux.getTam(); i++) {
			if (aux.getNo(i).getNome().endsWith("/")) {
				aux0 = gerarHeap(pasta + aux.getNo(i).getNome());
				aux0 = setDirQTD(aux0, aux.getNo(i).getNome(), aux0.getTam());
				aux.inserirDoHeap(aux0);
			}
		}
		return aux;
	}

	public void imprimirHeap() {
		System.out.println(heapRemoto);
	}

	public static void main(String[] args) {
		new TesteArqsFTP();
	}
}
