package br.com.ufs.mirrorFTP.servidor;

import java.util.ArrayList;
import java.util.List;

import br.com.ufs.mirrorFTP.Arquivos;

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

	private String getNomeDirArq(String line) {		
		String aux[] = line.split(";");
		if (aux.length > 1)
			if (aux[2].startsWith("size"))
				return aux[8].substring(1);
			else if (aux[2].split("=")[1].matches("dir"))
				return aux[7].replaceFirst(" ", "*");
		return "";
	}

	public List<String> getConteudo(String diretorio) {
		String[] lista = ftp.listarConteudo(diretorio).split("\n");
		List<String> conteudo = new ArrayList<String>();
		if (lista != null) {
			String aux;
			for (int i = 0; i < lista.length; i++) {
				aux = getNomeDirArq(lista[i]);
				if (aux != "")
					conteudo.add(aux);
			}
		}
		return conteudo;
	}
}
