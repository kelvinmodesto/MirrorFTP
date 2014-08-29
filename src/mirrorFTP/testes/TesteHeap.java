package mirrorFTP.testes;

import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class TesteHeap {

	private Heap hRemoto;
	private Heap hLocal;

	public TesteHeap() {
		hRemoto = new Heap();
		hLocal = new Heap();
		iniciar();
	}

	public void iniciar() {
		inserirRemoto();
		inserirLocal();
		comparar();
	}

	private void inserirRemoto() {
		hRemoto.inserirNo(new NoDir("/", "/", 1482336800231L, 3));
		hRemoto.inserirNo(new NoDir("pasta0/", "/pasta0/", 1482336800231L, 3));
		hRemoto.inserirNo(new NoArq("arquivo0/", "/pasta0/arquivo0/",1221436800231L, 35.4F));
		hRemoto.inserirNo(new NoArq("arquivo1/","/pasta0/arquivo1/", 1221436000231L, 21.5F));
		hRemoto.inserirNo(new NoArq("arquivo2/", "/pasta0/arquivo2/",1231436800231L, 341.3F));
	}

	private void inserirLocal() {
		hLocal.inserirNo(new NoDir("dirLocal/", "D:/dirLocal/", 1482336800231L, 3));
		hLocal.inserirNo(new NoDir("pasta0/", "D:/dirLocal/pasta0/", 1482336800231L, 3));
		hLocal.inserirNo(new NoArq("arquivo0/", "D:/dirLocal/arquivo0/", 1221436800231L, 35.4F));
		hLocal.inserirNo(new NoArq("arquivo1/", "D:/dirLocal/arquivo1/", 1221436000231L, 21.5F));
		hLocal.inserirNo(new NoArq("arquivo2/", "D:/dirLocal/arquivo2/", 1231436800231L, 341.3F));
	}

	private void comparar() {
		if (hRemoto.equals(hLocal))
			System.out.println("Sincronizado");
		else {
			while (!compararRemoto())
				;
			while (!compararLocal())
				;
		}
	}

	private boolean compararRemoto() {
		for (int i = 1; i < hRemoto.getTam(); i++) {
			if (!(i < hLocal.getTam())) {
				System.out.println("Não achou " + hRemoto.getNo(i)
						+ " no dir Local");
				hLocal.inserirNo(i, hRemoto.getNo(i));
				return false;
			} else if (!hRemoto.getNo(i).equals(hLocal.getNo(i))) {
				System.out.println("Não achou " + hRemoto.getNo(i)
						+ " no dir Local");
				hLocal.inserirNo(i, hRemoto.getNo(i));
				return false;
			}
		}
		System.out.println("Sincronizado no dir Local");
		return true;
	}

	private boolean compararLocal() {
		for (int i = 1; i < hLocal.getTam(); i++) {
			if (!(i < hRemoto.getTam())) {
				System.out.println("Não achou " + hLocal.getNo(i)
						+ " no dir Remoto");
				hRemoto.inserirNo(i, hLocal.getNo(i));
				return false;
			} else if (!hLocal.getNo(i).equals(hRemoto.getNo(i))) {
				System.out.println("Não achou " + hLocal.getNo(i)
						+ " no dir Remoto");
				hRemoto.inserirNo(i, hLocal.getNo(i));
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
