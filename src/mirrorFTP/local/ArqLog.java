package mirrorFTP.local;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import mirrorFTP.base.No;
import mirrorFTP.heap.Heap;
import mirrorFTP.heap.NoArq;
import mirrorFTP.heap.NoDir;

public class ArqLog {

	private File local, servidor;

	public ArqLog() {
		local = new File("local.txt");
		gerarArquivo(local);
		servidor = new File("servidor.txt");
		gerarArquivo(servidor);
	}
	
	private void gerarArquivo(File arq) {
		if(!arq.exists())
			try {
				arq.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}

	public Heap lerHeap(boolean isLocal) {
		Heap heap = new Heap();
		InputStream is;
		BufferedReader br;
		try {
			if (isLocal)
				is = new FileInputStream(local);
			else
				is = new FileInputStream(servidor);
			br = new BufferedReader(new InputStreamReader(is));
			String linha = br.readLine();
			String[] partes = getPartes(linha);
			while (linha != null) {
				No no;
				if (partes[0].endsWith("/")) {
					no = new NoDir(partes[0], partes[1],
							Long.parseLong(partes[2]));
					((NoDir) no).setQtd(Integer.parseInt(partes[3]));

				} else {
					no = new NoArq(partes[0], partes[1],
							Long.parseLong(partes[2]));
					((NoArq) no).setTam(Float.parseFloat(partes[3]));
					heap.inserirNo(no);
				}
				heap.inserirNo(no);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return heap;
	}

	public void escreverHeap(boolean isLocal, Heap heap) {
		OutputStream os;
		BufferedWriter bw;
		try {
			if (isLocal)
				os = new FileOutputStream(local);
			else
				os = new FileOutputStream(servidor);
			bw = new BufferedWriter(new OutputStreamWriter(os));
			for (int i = 0; i < heap.getTam(); i++) {
				bw.write(heap.getNo(i)+"\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] getPartes(String linha) {
		linha.replace("[", "").replace("]", "");
		return linha.split(" - ");
	}

}
