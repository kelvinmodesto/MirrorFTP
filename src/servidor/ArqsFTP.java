package servidor;

import heap.Heap;
import heap.NoArq;
import heap.NoDir;
import base.Arquivos;

public class ArqsFTP extends Arquivos {
	private FTP ftp;

	public ArqsFTP() {
		super();
		ftp = new FTP();
	}

	public int construirHeap(String diretorio) {
		ftp.conectar();
		ftp.logar();
		String[] lista = ftp.listarConteudo(diretorio).split("\n");
		heap.limparHeap();
		heap.inserirDoHeap(construirHeapArqs(diretorio, lista));
		heap.inserirDoHeap(construirHeapDirs(diretorio, lista));
		ftp.deslogar();
		return heap.getTam();
	}

	private Heap construirHeapArqs(String diretorio, String[] lista) {
		Heap heap = new Heap();
		if (lista != null) {
			String[] aux;
			NoArq no;
			for (int i = 0; i < lista.length; i++) {
				aux = getDadosDirArq(lista[i]);
				if (aux[0] != "" && !aux[0].endsWith("/")) {
					long data = Long.parseLong(aux[1]) - 30000;
					no = new NoArq(aux[0], diretorio + aux[0], data);
					float tam;
					if ((tam = Float.parseFloat(aux[2])) > 0)
						no.setTam(tam);
					heap.inserirNo(no);
				}
			}
		}
		return heap;
	}

	private Heap construirHeapDirs(String diretorio, String[] lista) {
		Heap heap = new Heap();
		if (lista != null) {
			String[] aux;
			NoDir no;
			for (int i = 0; i < lista.length; i++) {
				aux = getDadosDirArq(lista[i]);
				if (aux[0] != "" && aux[0].endsWith("/")) {
					long data = Long.parseLong(aux[1]) - 30000;
					no = new NoDir(aux[0], diretorio + aux[0], data);
					heap.inserirNo(no);
				}

			}
		}
		return heap;
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
}
