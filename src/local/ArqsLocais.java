package local;

import heap.Heap;
import heap.NoArq;
import heap.NoDir;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Date;

import base.Arquivos;

public class ArqsLocais extends Arquivos {
	private File arq;

	public ArqsLocais() {
		super();
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
	
	private long adaptarData(long dataMod) {
		Date data = new Date(dataMod);
		String dataForm = new SimpleDateFormat("yyyyMMddHHmmss").format(data);
		return Long.parseLong(dataForm);
	}
	
	private Heap construirHeapArqs(String diretorio, File[] aux) {
		Heap heap = new Heap();
		if (aux != null) {
			NoArq no;
			for (int i = 0; i < aux.length; i++) {
				no = new NoArq(aux[i].getName(), diretorio + aux[i].getName(),
						adaptarData(aux[i].lastModified()));
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
						+ aux[i].getName() + "/", adaptarData(aux[i].lastModified()));
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
