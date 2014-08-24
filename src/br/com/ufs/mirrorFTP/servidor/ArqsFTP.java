package br.com.ufs.mirrorFTP.servidor;

import java.io.File;
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

	public List<String> getConteudo(String diretorio) {
		List<String> conteudo = new ArrayList<String>();
		String nlist = ftp.listarNome(diretorio);

		if (nlist != null) {
			String[] listArq = nlist.split("\n");
			for (int i = 0; i < listArq.length; i++) {
				File f = new File(listArq[i]);
				if (f.isFile()) {
					conteudo.add(listArq[i]);
				} else {
					conteudo.add("*" + listArq[i]);
				}
			}
		}
		return conteudo;
	}
}
