package principal;

import heap.Heap;
import heap.NoArq;
import heap.NoDir;
import local.ArqEntrada;
import local.Local;
import base.No;

public class Comparar {

	private Local local;
	//private Servidor servidor;
	private Heap heapLocal, heapRemoto;
	private Acao acao;
	private ArqEntrada entrada;

	public Comparar() {
		local = new Local();
		// servidor = new Servidor();
		heapLocal = local.getHeap();
		// heapRemoto = servidor.getHeap();
		heapRemoto = new Heap();
		inserirHeapRemoto();
		acao = new Acao();
		entrada = new ArqEntrada();
		iniciar();
	}

	private void inserirHeapRemoto() {
		heapRemoto.inserirNo(new NoDir("/", "/", 0, 2));
		heapRemoto.inserirNo(new NoArq("teste.txt", "/teste.txt",
				20140824181009L, 0.0F));
		heapRemoto
				.inserirNo(new NoDir("pasta/", "/pasta/", 20140830150204L, 2));
		heapRemoto.inserirNo(new NoArq("teste4.input", "/pasta/teste4.input",
				20140828180735L, 0.0F));
		heapRemoto.inserirNo(new NoDir("pasta1/", "/pasta/pasta1/",
				20140829142104L, 1));
		heapRemoto.inserirNo(new NoArq("teste6.jpg",
				"/pasta/pasta1/teste6.jpg", 20140829141938L, 0.0F));
	}

	private void iniciar() {
		while (true) {
			comparar();
			try {
				Thread.sleep(entrada.getIntervalo() * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			local.reiniciarHeap();
			//servidor.reiniciarHeap();
			heapLocal = local.getHeap();
			//heapRemoto = servidor.getHeap();
			heapRemoto.limparHeap();
			inserirHeapRemoto();
		}
	}

	private void comparar() {
		System.out.println();
		if (heapRemoto.equals(heapLocal))
			System.out.println("Sincronizado");
		else {
			while (!compararRemoto())
				;
			while (!compararLocal())
				;
		}
	}

	private No sincRemoto(No no) {
		String diretorio;
		diretorio = no.getCaminho().replaceFirst(entrada.getDirRemoto(),
				entrada.getDirLocal());
		no.setCaminho(diretorio);
		if (no.getNome().endsWith("/"))
			acao.criarDirLocal(diretorio);
		else {
			diretorio = diretorio.replaceFirst(no.getNome(), "");
			acao.baixarArq(diretorio, no.getNome(),no.getData());
		}
		return no;
	}

	private boolean compararRemoto() {
		No no;
		for (int i = 1; i < heapRemoto.getTam(); i++) {
			no = heapRemoto.getNo(i);
			if (!(i < heapLocal.getTam())) {
				System.out.println("Não achou " + no + " no dir Local");
				heapLocal.inserirNo(i, sincRemoto(no));
				return false;
			} else if (!heapLocal.getNo(i).equals(no)) {
				System.out.println("Não achou " + no + " no dir Local");
				heapLocal.inserirNo(i, sincRemoto(no));
				return false;
			}
		}
		System.out.println("Sincronizado no dir Local");
		return true;
	}

	private No sincLocal(No no) {
		String diretorio;
		diretorio = no.getCaminho().replaceFirst(entrada.getDirLocal(),
				entrada.getDirRemoto());
		no.setCaminho(diretorio);
		if (no.getNome().endsWith("/"))
			acao.criarDirRemoto(diretorio);
		else {
			diretorio = diretorio.replaceFirst(no.getNome(), "");
			acao.baixarArq(diretorio, no.getNome(), no.getData());
		}
		return no;
	}

	private boolean compararLocal() {
		No no;
		for (int i = 1; i < heapLocal.getTam(); i++) {
			no = heapLocal.getNo(i);
			if (!(i < heapRemoto.getTam())) {
				System.out.println("Não achou " + no + " no dir Remoto");
				heapRemoto.inserirNo(i, sincLocal(no));
				return false;
			} else if (!heapLocal.getNo(i).equals(no)) {
				System.out.println("Não achou " + no + " no dir Remoto");
				heapRemoto.inserirNo(i, sincLocal(no));
				return false;
			}
		}
		System.out.println("Sincronizado no dir Remoto");
		return true;
	}

	public static void main(String[] args) {
		new Comparar();
	}

}
