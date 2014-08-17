package br.com.ufs.mirrorFTP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import br.com.ufs.mirrorFTP.arquivos.ArqEntrada;

public class FTP {

	// Area de Variaveis Globais
	
	private ArqEntrada inFile;
	private Socket controle;
	private InputStream iscontr;
	private OutputStream oucontr;
	private Socket dados;
	private InputStream isData;
	private OutputStream osData;
	//2 flags que se tornam true quando o repositorio eh alterado
	private boolean LocalModificado=false, RemotoModificado=false;
	private String getControlResp() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				this.iscontr));
		String resp = br.readLine();
		System.out.println(resp);
		return resp;
	}

	// COMANDOS DO FTP
	public void connect(String host, int port) throws UnknownHostException,
			IOException {
		this.controle = new Socket(host, port);
		this.iscontr = controle.getInputStream();
		this.oucontr = controle.getOutputStream();
		this.getControlResp();
	}

	public void logout() throws IOException {
		String command = "QUIT \r\n";
		this.oucontr.write(command.getBytes());
		this.getControlResp();
	}

	public void newFolder(String pasta) throws IOException {
		String comand = "MKD " + pasta + "\r\n";
		this.oucontr.write(comand.getBytes());
		this.getControlResp();
	}

	public void changeWorkingDir(String path) throws IOException {
		String comand = "CWD " + path + "\r\n";
		this.oucontr.write(comand.getBytes());
		this.getControlResp();
	}

	public void currentWorkingDir() throws IOException {
		String comand = "PWD \r\n";
		this.oucontr.write(comand.getBytes());
		this.getControlResp();
	}

	private void pasv() throws IOException {
		String comand = "PASV \r\n";
		this.oucontr.write(comand.getBytes());
		String resp = this.getControlResp();
		StringTokenizer st = new StringTokenizer(resp);
		st.nextToken("(");
		String ip = st.nextToken(",").substring(1) + "." + st.nextToken(",")
				+ "." + st.nextToken(",") + "." + st.nextToken(",");
		int value1 = Integer.parseInt(st.nextToken(","));
		int value2 = Integer.parseInt(st.nextToken(")").substring(1));
		int port = value1 * 256 + value2;

		this.dados = new Socket(ip, port);
		this.isData = dados.getInputStream();
		this.osData = dados.getOutputStream();
	}

	public String list(String path) throws IOException {
		this.pasv();
		String comand = "LIST " + path + "\r\n";
		this.oucontr.write(comand.getBytes());
		this.getControlResp();
		BufferedReader br = new BufferedReader(new InputStreamReader(isData));
		String resp = "";
		String line;
		while ((line = br.readLine()) != null) {
			resp = resp + "\n" + line;
		}
		this.getControlResp();
		return resp;
	}

	public void downloadFile(String nome) throws IOException {
		this.pasv();
		String comand = "RETR " + nome + "\r\n";
		this.oucontr.write(comand.getBytes());
		this.getControlResp();
		File file = new File("C:/Users/Kelvin/Downloads/" + nome);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buf = new byte[1000];
		int len;
		while ((len = this.isData.read(buf)) != -1) {
			fos.write(buf, 0, len);
		}
		fos.flush();
		fos.close();
		this.getControlResp();
	}

	private void changeType(String type) throws IOException {
		String msg = "TYPE " + type + "\r\n";
		this.oucontr.write(msg.getBytes());
		this.getControlResp();
	}

	public boolean isLocalModificado() {
		return LocalModificado;
	}

	public void setLocalModificado(boolean localModificado) {
		LocalModificado = localModificado;
	}

	public boolean isRemotoModificado() {
		return RemotoModificado;
	}

	public void setRemotoModificado(boolean remotoModificado) {
		RemotoModificado = remotoModificado;
	}

}
