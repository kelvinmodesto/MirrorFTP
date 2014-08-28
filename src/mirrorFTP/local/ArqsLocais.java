package mirrorFTP.local;

import java.io.File;
import java.io.FileFilter;

import mirrorFTP.Arquivos;
import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class ArqsLocais extends Arquivos {
	private File arq;

	public Heap construirHeapLocal(String diretorio) {
		arq = new File(diretorio);
		Heap heap = new Heap();
		heap.inserirDoHeap(construirHeapLocalArqs(arq
				.listFiles(new filtroArq())));
		heap.inserirDoHeap(construirHeapLocalDirs(arq
				.listFiles(new filtroDir())));
		return heap;
	}

	private Heap construirHeapLocalArqs(File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			NoArq no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoArq(aux[i].getName(), aux[i].lastModified());
				if (aux[i].length() > 0)
					no.setTam(aux[i].length());
				heap.inserirNo(no);
			}
		}
		return heap;
	}

	private Heap construirHeapLocalDirs(File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			NoDir no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoDir(aux[i].getName() + "/", aux[i].lastModified());
				if (aux[i].listFiles().length > 0)
					no.setQtd(aux[i].listFiles().length);
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
