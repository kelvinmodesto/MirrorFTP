package principal;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import local.ArqEntrada;
import servidor.FTP;

public class Acao {

	private FTP ftp;
	private ArqEntrada entrada;
	private File arq;

	public Acao() {
		ftp = new FTP();
		entrada = new ArqEntrada();
	}

	private void iniciar() {
		ftp.conectar();
		ftp.logar();
	}

	private void finalizar() {
		ftp.deslogar();
	}

	private void setDataArq(String diretorio, long data) {
		arq = new File(diretorio);
		SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddHHmmss");
		formato.setTimeZone(TimeZone.getTimeZone("UTC-3"));
		try {
			Date dataMod = formato.parse(Long.toString(data));
			arq.setLastModified(dataMod.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void setDataArq(String diretorio) {
		iniciar();
		long data = ftp.getDataModArq(diretorio.replaceFirst(
				entrada.getDirLocal(), entrada.getDirRemoto()));
		finalizar();
		setDataArq(diretorio, data);
	}

	public void criarDirLocal(String diretorio,long data) {
		arq = new File(diretorio);
		arq.mkdir();
	}

	public void criarDirRemoto(String diretorio) {
		iniciar();
		ftp.criarPasta(diretorio);
		finalizar();
	}

	public void baixarArq(String dirLocal, String dirServidor) {
		iniciar();
		ftp.baixarArquivo(dirLocal, dirServidor);
		finalizar();
		setDataArq(dirLocal);
	}

	public void enviarArq(String dirLocal, String dirServidor) {
		iniciar();
		ftp.enviarArquivo(dirLocal, dirServidor);
		finalizar();
		setDataArq(dirLocal);
	}

}
