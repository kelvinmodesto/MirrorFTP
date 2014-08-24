package br.com.ufs.mirrorFTP.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.ufs.mirrorFTP.Arquivos;

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
}
