package mirrorFTP.servidor;

import mirrorFTP.Arquivos;
import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class ArqsFTP extends Arquivos {
	private FTP ftp;

	public ArqsFTP() {
		ftp = new FTP();
		iniciar();
	}

	private void iniciar() {
		ftp.conectar();
		ftp.logar();
	}

	protected void finalize() {
		ftp.deslogar();
	}

	private String[] getDadosDirArq(String line) {
		String aux[] = line.split(";");
		String resultado[] = { "", "", "" };
		if (aux.length > 1)
			if (aux[2].startsWith("size")) {
				resultado[0] = aux[8].substring(1);
				resultado[1] = aux[0].split("=")[1];
				resultado[2] = aux[2].split("=")[1];
			} else if (aux[2].split("=")[1].matches("dir")) {
				resultado[0] = aux[7].substring(1) + "/";
				resultado[1] = aux[0].split("=")[1];
			}
		return resultado;
	}

	public Heap construirHeapRemoto(String diretorio) {
		String[] lista = ftp.listarConteudo(diretorio).split("\n");
		Heap heap = new Heap();
		heap.inserirDoHeap(construirHeapRemotoArqs(lista));
		heap.inserirDoHeap(construirHeapRemotoDirs(lista));
		return heap;
	}

	private Heap construirHeapRemotoArqs(String[] lista) {
		Heap heap = new Heap();
		if (lista != null) {
			String[] aux;
			NoArq no;
			for (int i = 0; i < lista.length; i++) {
				aux = getDadosDirArq(lista[i]);
				if (aux[0] != "" && !aux[0].endsWith("/")) {
					no = new NoArq(aux[0], Long.parseLong(aux[1]));
					float tam;
					if ((tam = Float.parseFloat(aux[2])) > 0)
						no.setTam(tam);
					heap.inserirNo(no);
				}
			}
		}
		return heap;
	}

	private Heap construirHeapRemotoDirs(String[] lista) {
		Heap heap = new Heap();
		if (lista != null) {
			String[] aux;
			NoDir no;
			for (int i = 0; i < lista.length; i++) {
				aux = getDadosDirArq(lista[i]);
				if (aux[0] != "" && aux[0].endsWith("/")) {
					no = new NoDir(aux[0], Long.parseLong(aux[1]));
					heap.inserirNo(no);
				}

			}
		}
		return heap;
	}
}
