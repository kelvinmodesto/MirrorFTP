package mirrorFTP.local;

import java.io.File;
import java.io.FileFilter;

import mirrorFTP.base.Arquivos;
import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class ArqsLocais extends Arquivos {
	private File arq;
	private Heap heap;

	public ArqsLocais() {
		heap = new Heap();
	}

	public Heap getHeap() {
		return heap;
	}

	public int construirHeap(String diretorio) {
		arq = new File(diretorio);
		heap.limparHeap();
		heap.inserirDoHeap(construirHeapArqs(diretorio,
				arq.listFiles(new filtroArq())));
		heap.inserirDoHeap(construirHeapDirs(diretorio,
				arq.listFiles(new filtroDir())));
		return heap.getTam();
	}

	private Heap construirHeapArqs(String diretorio, File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			NoArq no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoArq(aux[i].getName(), diretorio + aux[i].getName(),
						aux[i].lastModified());
				if (aux[i].length() > 0)
					no.setTam(aux[i].length());
				heap.inserirNo(no);
			}
		}
		return heap;
	}

	private Heap construirHeapDirs(String diretorio, File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			NoDir no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoDir(aux[i].getName() + "/", diretorio
						+ aux[i].getName() + "/", aux[i].lastModified());
				heap.inserirNo(no);
			}
		}
		return heap;
	}

	public class filtroArq implements FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isFile())
				return true;
			return false;
		}
	}

	public class filtroDir implements FileFilter {
		@Override
		public boolean accept(File f) {
			if (f.isDirectory())
				return true;
			return false;
		}
	}
}
