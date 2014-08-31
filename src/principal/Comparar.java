package principal;

import heap.Heap;
import local.ArqEntrada;
import local.Local;
import servidor.Servidor;
import base.No;

public class Comparar {

	private Local local;
	private Servidor servidor;
	private Heap heapLocal, heapRemoto;
	private Acao acao;
	private ArqEntrada entrada;

	public Comparar() {
		local = new Local();
		servidor = new Servidor();
		heapLocal = local.getHeap();
		heapRemoto = servidor.getHeap();
		acao = new Acao();
		entrada = new ArqEntrada();
		iniciar();
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
			servidor.reiniciarHeap();
			heapLocal = local.getHeap();
			heapRemoto = servidor.getHeap();
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
		System.out.println();
	}

	private No sincRemoto(No no) {
		String diretorio;
		diretorio = no.getCaminho().replaceFirst(entrada.getDirRemoto(),
				entrada.getDirLocal());
		if (no.getNome().endsWith("/"))
			acao.criarDirLocal(diretorio,no.getData());
		else
			acao.baixarArq(diretorio, no.getCaminho());
		no.setCaminho(diretorio);
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
		if (no.getNome().endsWith("/"))
			acao.criarDirRemoto(diretorio);
		else
			acao.enviarArq(no.getCaminho(), diretorio);
		no.setCaminho(diretorio);
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
