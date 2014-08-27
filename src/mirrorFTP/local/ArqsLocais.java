package mirrorFTP.local;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import mirrorFTP.Arquivos;
import mirrorFTP.heap.Heap;
import mirrorFTP.heap.No;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class ArqsLocais extends Arquivos {
	private File arq;

	public List<String> getConteudo(String diretorio) {
		arq = new File(diretorio);
		File[] aux = arq.listFiles();
		List<String> conteudo = new ArrayList<String>();
		if (aux != null)
			for (int i = 0; i < aux.length; i++) {
				if (aux[i].isFile())
					conteudo.add(aux[i].getName());
				else
					conteudo.add("*" + aux[i].getName());
			}
		return conteudo;
	}

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
			No no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoArq(aux[i].getName(), aux[i].lastModified());
				if (aux[i].length() > 0)
					((NoArq) no).setTam(aux[i].length());
				heap.inserirNo(no);
			}
		}
		return heap;
	}

	private Heap construirHeapLocalDirs(File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			No no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoDir("*" + aux[i].getName(), aux[i].lastModified());
				if (aux[i].listFiles().length > 0)
					((NoDir) no).setQtd(aux[i].listFiles().length);
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
