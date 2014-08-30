package principal;

import heap.Heap;
import local.Local;
import servidor.Servidor;
import base.Diretorio;

public class Comparar {

	private Diretorio local,servidor;
	private Heap heapLocal,heapRemoto;
	
	public Comparar() {
		local = new Local();
		servidor = new Servidor();
		heapLocal = local.getHeap();
		heapRemoto = servidor.getHeap();
	}
	
	public void comparar() {
		if (heapRemoto.equals(heapLocal))
			System.out.println("Sincronizado");
		else {
			while (!compararRemoto())
				;
			while (!compararLocal())
				;
		}
	}
	
	private boolean compararRemoto() {
		for (int i = 1; i < heapRemoto.getTam(); i++) {
			if (!(i < heapLocal.getTam())) {
				System.out.println("Não achou " + heapRemoto.getNo(i)
						+ " no dir Local");
				heapLocal.inserirNo(i, heapRemoto.getNo(i));
				return false;
			} else if (!heapRemoto.getNo(i).equals(heapLocal.getNo(i))) {
				System.out.println("Não achou " + heapRemoto.getNo(i)
						+ " no dir Local");
				heapLocal.inserirNo(i, heapRemoto.getNo(i));
				return false;
			}
		}
		System.out.println("Sincronizado no dir Local");
		return true;
	}
	
	private boolean compararLocal() {
		for (int i = 1; i < heapLocal.getTam(); i++) {
			if (!(i < heapRemoto.getTam())) {
				System.out.println("Não achou " + heapLocal.getNo(i)
						+ " no dir Remoto");
				heapRemoto.inserirNo(i, heapLocal.getNo(i));
				return false;
			} else if (!heapLocal.getNo(i).equals(heapRemoto.getNo(i))) {
				System.out.println("Não achou " + heapLocal.getNo(i)
						+ " no dir Remoto");
				heapRemoto.inserirNo(i, heapLocal.getNo(i));
				return false;
			}
		}
		System.out.println("Sincronizado no dir Remoto");
		return true;
	}

}
