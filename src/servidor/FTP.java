package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import local.ArqEntrada;

public class FTP {
	private Socket controle;
	private InputStream isContr;
	private OutputStream osContr;
	private Socket dados;
	private InputStream isDados;
	private OutputStream osDados;
	private ArqEntrada entrada;

	public FTP() {
		entrada = new ArqEntrada();
	}

	private String enviarCmd() {
		String resp = null;
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(isContr));
			resp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(resp);
		return resp;
	}

	public void conectar() {
		try {
			controle = new Socket(entrada.getHost(), entrada.getPorta());
			isContr = controle.getInputStream();
			osContr = controle.getOutputStream();
			enviarCmd();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String enviarCmd(String comando) {
		BufferedReader br;
		String resp = null;
		try {
			osContr.write(comando.getBytes());
			br = new BufferedReader(new InputStreamReader(isContr));
			resp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(resp);
		return resp;
	}

	public void logar() {
		enviarCmd("USER " + entrada.getUsuario() + "\r\n");
		enviarCmd("PASS " + entrada.getSenha() + "\r\n");
	}

	public void deslogar() {
		enviarCmd("QUIT \r\n");
	}

	public void criarPasta(String pasta) {
		enviarCmd("MKD " + pasta + "\r\n");
	}

	public void deletarPasta(String pasta) {
		enviarCmd("RMD " + pasta + "\r\n");
	}

	public void mudarDir(String diretorio) {
		enviarCmd("CWD " + diretorio + "\r\n");
	}

	public void mostrarDirAtual() {
		enviarCmd("PWD \r\n");
	}

	private void entrarNoModoPASV() {
		String resp = enviarCmd("PASV \r\n");
		StringTokenizer st = new StringTokenizer(resp);
		st.nextToken("(");
		String ip = st.nextToken(",").substring(1) + "." + st.nextToken(",")
				+ "." + st.nextToken(",") + "." + st.nextToken(",");
		int value1 = Integer.parseInt(st.nextToken(","));
		int value2 = Integer.parseInt(st.nextToken(")").substring(1));
		int port = value1 * 256 + value2;
		try {
			dados = new Socket(ip, port);
			isDados = dados.getInputStream();
			dados.getOutputStream();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String listagem(String comando, String diretorio) {
		entrarNoModoPASV();
		enviarCmd(comando);
		BufferedReader br;
		String resp = "";
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(isDados));
			while ((line = br.readLine()) != null) {
				resp = resp + "\n" + line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		enviarCmd();
		return resp;
	}

	public String listar(String diretorio) {
		return listagem("LIST " + diretorio + "\r\n", diretorio);
	}

	public String listarNome(String diretorio) {
		return listagem("NLST " + diretorio + "\r\n", diretorio);
	}

	public String listarConteudo(String diretorio) {
		return listagem("MLSD " + diretorio + "\r\n", diretorio);
	}

	private void mudarTipo(String tipo) {
		enviarCmd("TYPE " + tipo + "\r\n");
	}

	public void baixarArquivo(String diretorio, String arquivo) {
		entrarNoModoPASV();
		mudarTipo("I");
		enviarCmd("RETR " + arquivo + "\r\n");
		File file = new File(diretorio + arquivo);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] buf = new byte[1000];
			int len;
			while ((len = isDados.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();
			dados.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		enviarCmd();
	}

	public void enviarArquivo(String diretorio, String arquivo) {
		entrarNoModoPASV();
		mudarTipo("I");
		enviarCmd("STOR " + arquivo + "\r\n");
		File file = new File(diretorio + arquivo);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] buf = new byte[1000];
			int len;
			while ((len = fis.read(buf)) != -1) {
				osDados.write(buf, 0, len);
			}
			fis.close();
			dados.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		enviarCmd();
	}

	public void deletarArquivo(String diretorio, String arquivo) {
		enviarCmd("DELE " + diretorio + "/" + arquivo + "\r\n");
	}

	public void statusArquivo(String diretorio, String arquivo) {
		enviarCmd("STAT " + diretorio + "/" + arquivo + "\r\n");
	}

	public long getDataModArq(String arquivo) {
		String resp = enviarCmd("MDTM " + arquivo + "\r\n").replaceAll(" ", "");
		return Long.parseLong(resp.substring(3));
	}
	

}
