package br.com.ufs.mirrorFTP.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
	private File arq;

	public List<String> getConteudo(String diretorio) {
		arq = new File(diretorio);
		File[] aux = arq.listFiles();
		List<String> conteudo = new ArrayList<String>();
		for (int i = 0; i < aux.length; i++) {
			conteudo.add(aux[i].getName());
		}
		return conteudo;
	}
}