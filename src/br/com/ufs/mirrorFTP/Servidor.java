package br.com.ufs.mirrorFTP;
import br.com.ufs.mirrorFTP.arquivos.*;

public class Servidor {
	// Area de Variaveis Globais
	private FTP FTPremoto;
	public boolean comparaLocal() {
		boolean ok = false;
		return ok;
	}
	public FTP getFTPremoto() {
		return FTPremoto;
	}
	public void setFTPremoto(FTP fTPremoto) {
		FTPremoto = fTPremoto;
	}
}
