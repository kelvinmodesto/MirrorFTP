package br.com.ufs.mirrorFTP.servidor;

import java.util.ArrayList;
import java.util.List;

import br.com.ufs.mirrorFTP.Arquivos;
import br.com.ufs.mirrorFTP.local.ArqEntrada;

public class ArqsFTP extends Arquivos {
	private ArqEntrada entrada;
	private FTP ftp;

	public ArqsFTP() {
		entrada = new ArqEntrada();
		ftp = new FTP();
		iniciar();
	}

	private void iniciar() {
		ftp.conectar(entrada.getHost(), entrada.getPorta());
		ftp.logar(entrada.getUsuario(), entrada.getSenha());
	}
	
	protected void finalize() {
		ftp.deslogar();
	}

	public List<String> getConteudo(String diretorio) {
		List<String> conteudo = new ArrayList<String>();
		
		return conteudo;
	}
}
