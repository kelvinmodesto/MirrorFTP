package principal;

import heap.Heap;
import heap.NoArq;
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
		System.out.println("======Comparacao dos diretorios locais e do servidor======");
		if (heapRemoto.equals(heapLocal))
			System.out.println("Sincronizado");
		else {
			while (!compararRemoto())
				;
			while (!compararLocal())
				;
		}
		System.out.println("===================Comparacao finalizada==================");
	}

	private String mudarDoDirRemoto(No no) {
		return no.getCaminho().replaceFirst(entrada.getDirRemoto(),
				entrada.getDirLocal());
	}

	private No criarDoRemoto(No no) {
		if (no.getNome().endsWith("/"))
			acao.criarDirLocal(mudarDoDirRemoto(no));
		else
			acao.baixarArq(mudarDoDirRemoto(no), no.getCaminho());
		no.setCaminho(mudarDoDirRemoto(no));
		return no;
	}

	private No atualizarDoRemoto(No no) {
		acao.removerArqLocal(mudarDoDirRemoto(no));
		acao.baixarArq(no.getCaminho(), mudarDoDirRemoto(no));
		no.setCaminho(mudarDoDirRemoto(no));
		return no;
	}

	private boolean compararRemoto() {
		No noRemoto, noLocal;
		for (int i = 1; i < heapRemoto.getTam(); i++) {
			noRemoto = heapRemoto.getNo(i);
			if (!(i < heapLocal.getTam())) {
				System.out.println("Nao achou " + noRemoto + " no dir Local");
				heapLocal.inserirNo(i, criarDoRemoto(noRemoto));
				return false;
			}
			noLocal = heapLocal.getNo(i);
			if (!noLocal.equals(noRemoto)) {
				System.out.println("Nao achou " + noRemoto + " no dir Local");
				heapLocal.inserirNo(i, criarDoRemoto(noRemoto));
				return false;
			} else if (!noRemoto.getNome().endsWith("/")) {
				NoArq aux = (NoArq) noRemoto;
				if (!aux.isEqualsData(noLocal) && aux.isMaiorData(noLocal)) {
					System.out.println(noLocal + " esta desatualizado");
					heapLocal.substituirNo(i, atualizarDoRemoto(noRemoto));
					return false;
				}
			}
		}
		System.out.println("Sincronizado no dir Local");
		return true;
	}

	private String mudarDoDirLocal(No no) {
		return no.getCaminho().replaceFirst(entrada.getDirLocal(),
				entrada.getDirRemoto());
	}

	private No criarDoLocal(No no) {
		if (no.getNome().endsWith("/"))
			acao.criarDirRemoto(mudarDoDirLocal(no));
		else
			acao.enviarArq(no.getCaminho(), mudarDoDirLocal(no));
		no.setCaminho(mudarDoDirLocal(no));
		return no;
	}

	private No atualizarDoLocal(No no) {
		acao.removerArqRemoto(mudarDoDirLocal(no));
		acao.enviarArq(no.getCaminho(), mudarDoDirLocal(no));
		no.setCaminho(mudarDoDirLocal(no));
		return no;
	}

	private boolean compararLocal() {
		No noLocal, noRemoto;
		for (int i = 1; i < heapLocal.getTam(); i++) {
			noLocal = heapLocal.getNo(i);
			if (!(i < heapRemoto.getTam())) {
				System.out.println("Nao achou " + noLocal + " no dir Remoto");
				heapRemoto.inserirNo(i, criarDoLocal(noLocal));
				return false;
			}
			noRemoto = heapRemoto.getNo(i);
			if (!noRemoto.equals(noLocal)) {
				System.out.println("Nao achou " + noLocal + " no dir Remoto");
				heapRemoto.inserirNo(i, criarDoLocal(noLocal));
				return false;
			} else if (!noLocal.getNome().endsWith("/")) {
				NoArq aux = (NoArq) noLocal;
				if (!aux.isEqualsData(noRemoto) && aux.isMaiorData(noRemoto)) {
					System.out.println(noRemoto + " esta desatualizado");
					heapRemoto.substituirNo(i, atualizarDoLocal(noLocal));
					return false;
				}

			}
		}
		System.out.println("Sincronizado no dir Remoto");
		return true;
	}

	public static void main(String[] args) {
		new Comparar();
	}

}
