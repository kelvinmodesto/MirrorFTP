package testes;

import heap.Heap;
import heap.NoArq;
import heap.NoDir;

public class TesteHeap {

	protected Heap heapRemoto;
	protected Heap heapLocal;

	public TesteHeap() {
		heapRemoto = new Heap();
		heapLocal = new Heap();
		iniciar();
	}

	protected void iniciar() {
		inserirRemoto();
		inserirLocal();
		comparar();
	}

	protected void inserirRemoto() {
		heapRemoto.inserirNo(new NoDir("/", "/", 1482336800231L, 1));
		heapRemoto.inserirNo(new NoDir("pasta0/", "/pasta0/", 1482336800231L, 3));
		heapRemoto.inserirNo(new NoArq("arquivo0/", "/pasta0/arquivo0/",1221436800231L, 35.4F));
		heapRemoto.inserirNo(new NoArq("arquivo1/","/pasta0/arquivo1/", 1221436000231L, 21.5F));
		heapRemoto.inserirNo(new NoArq("arquivo2/", "/pasta0/arquivo2/",1231436800231L, 341.3F));
	}

	protected void inserirLocal() {
		heapLocal.inserirNo(new NoDir("dirLocal/", "D:/dirLocal/", 1482336800231L, 1));
		heapLocal.inserirNo(new NoDir("pasta0/", "D:/dirLocal/pasta0/", 1482336800231L, 3));
		heapLocal.inserirNo(new NoArq("arquivo0/", "D:/dirLocal/arquivo0/", 1221436800231L, 35.4F));
		heapLocal.inserirNo(new NoArq("arquivo1/", "D:/dirLocal/arquivo1/", 1221436000231L, 21.5F));
		heapLocal.inserirNo(new NoArq("arquivo2/", "D:/dirLocal/arquivo2/", 1231436800231L, 341.3F));
	}

	private void comparar() {
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

	public static void main(String[] args) {
		new TesteHeap();
	}

}
