package br.com.ufs.mirrorFTP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class FTP {
	private Socket controle;
	private InputStream isContr;
	private OutputStream osContr;
	private Socket dados;
	private InputStream isData;

	private String enviarCmd() {
		String resp = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(this.isContr));
			resp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(resp);
		return resp;
	}

	public void conectar(String end, int porta) {
		try {
			this.controle = new Socket(end, porta);
			this.isContr = controle.getInputStream();
			this.osContr = controle.getOutputStream();
			this.enviarCmd();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String enviarCmd(String command) {
		BufferedReader br;
		String resp = null;
		try {
			this.osContr.write(command.getBytes());
			br = new BufferedReader(new InputStreamReader(this.isContr));
			resp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(resp);
		return resp;
	}

	public void logar(String usuario, String senha) {
		this.enviarCmd("USER " + usuario + "\r\n");
		this.enviarCmd("PASS " + senha + "\r\n");
	}

	public void deslogar() {
		this.enviarCmd("QUIT \r\n");
	}

	public void criarPasta(String pasta) {
		this.enviarCmd("MKD " + pasta + "\r\n");
	}

	public void deletarPasta(String pasta) {
		this.enviarCmd("RMD " + pasta + "\r\n");
	}

	public void mudarDir(String caminho) {
		this.enviarCmd("CWD " + caminho + "\r\n");
	}

	public void mostrarDirAtual() {
		this.enviarCmd("PWD \r\n");
	}

	private void entrarNoModoPASV() {
		String resp = this.enviarCmd("PASV \r\n");
		StringTokenizer st = new StringTokenizer(resp);
		st.nextToken("(");
		String ip = st.nextToken(",").substring(1) + "." + st.nextToken(",")
				+ "." + st.nextToken(",") + "." + st.nextToken(",");
		int value1 = Integer.parseInt(st.nextToken(","));
		int value2 = Integer.parseInt(st.nextToken(")").substring(1));
		int port = value1 * 256 + value2;
		try {
			this.dados = new Socket(ip, port);
			this.isData = dados.getInputStream();
			dados.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String list(String caminho) {
		this.entrarNoModoPASV();
		this.enviarCmd("LIST " + caminho + "\r\n");
		BufferedReader br;
		String resp = "";
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(isData));
			while ((line = br.readLine()) != null) {
				resp = resp + "\n" + line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.enviarCmd();
		return resp;
	}

	public void baixarArquivo(String caminho,String arquivo) {
		this.entrarNoModoPASV();
		this.enviarCmd("RETR " + arquivo + "\r\n");
		File file = new File(caminho + arquivo);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buf = new byte[1000];
			int len;
			while ((len = this.isData.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.enviarCmd();
	}

	@SuppressWarnings("unused")
	private void mudarTipo(String type) throws IOException {
		String msg = "TYPE " + type + "\r\n";
		this.osContr.write(msg.getBytes());
		this.enviarCmd();
	}

}
