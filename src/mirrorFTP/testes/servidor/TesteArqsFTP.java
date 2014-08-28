package mirrorFTP.testes.servidor;

import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoDir;
import mirrorFTP.local.ArqEntrada;
import mirrorFTP.servidor.ArqsFTP;

public class TesteArqsFTP {

	private ArqsFTP arqsFTP;
	private ArqEntrada entrada;
	private Heap heapRemoto;

	public TesteArqsFTP() {
		entrada = new ArqEntrada();
		arqsFTP = new ArqsFTP();
		heapRemoto = new Heap();
		heapRemoto.inserirNo(new NoDir(entrada.getDirRemoto(), 0));
		heapRemoto.inserirDoHeap(gerarHeap(entrada.getDirRemoto()));
		imprimirHeap();
	}
	
	//Nao seta a qtd das pastas internas
	public Heap gerarHeap(String pasta) {
		Heap aux = arqsFTP.construirHeapRemoto(pasta);
		String[] partes = pasta.split("/");
		NoDir no = (NoDir) heapRemoto.getNo(partes[partes.length-1]+"/");
		if (no != null)
			no.setQtd(aux.getTam());
		for (int i = 0; i < aux.getTam(); i++) {
			if (aux.getNo(i).getNome().endsWith("/"))
				aux.inserirDoHeap(gerarHeap(pasta + aux.getNo(i).getNome()));
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
