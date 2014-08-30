package principal;

import java.io.File;

import servidor.FTP;

public class Acao {

	private FTP ftp;
	private File arq;
	
	public Acao() {
		ftp = new FTP();
	}
	
	private void iniciar() {
		ftp.conectar();
		ftp.logar();
	}
	
	private void finalizar() {
		ftp.deslogar();
	}
	
	public void criarDirRemoto(String diretorio) {
		iniciar();
		ftp.criarPasta(diretorio);
		finalizar();
	}
	
	public void criarDirLocal(String diretorio) {
		iniciar();
		arq = new File(diretorio);
		arq.mkdir();
		finalizar();
	}
	
	public void baixarArq(String diretorio,String arquivo) {
		iniciar();
		ftp.baixarArquivo(diretorio, arquivo);
		finalizar();
	}
	
	public void enviarArq(String diretorio,String arquivo) {
		iniciar();
		ftp.enviarArquivo(diretorio, arquivo);
		finalizar();
	}

}
