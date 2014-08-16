package mirrorFTP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;


public class Function_FTP {
	
	private Socket controle;
	private InputStream iscontr;
	private OutputStream oscontr;
	private Socket dados;
	private OutputStream osDados;
	private InputStream isDados;
	
	// ARRAYS
	ArrayList<String> aObjNoFtp = new ArrayList<>();
	ArrayList<String> aRemovidoFtp = new ArrayList<>();
	
	// VARIAVEIS LOCAIS
	String host;
	int port;
	String user;
	String pass;
	
	// GET E SET
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = Integer.parseInt(port);
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}

	// CAPTURA DE RESPOSTA
	public String getControlResp() throws IOException{
		BufferedReader br = new BufferedReader(
				new InputStreamReader(this.iscontr));
		String resp = br.readLine();
		System.out.println(resp);
		return resp;
	}	
	// ABRE CONEX�O
	public void conect() throws IOException{
		this.controle = new Socket(host, port);
		this.iscontr = controle.getInputStream();
		this.oscontr = controle.getOutputStream();		
		this.getControlResp();
	}	
	// LOGIN E SENHA
	public void login() throws IOException{
		String comand = "USER " + user + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
		comand = "PASS " + pass + "s\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
	}
	// CRIA DIRET�RIO
	public void newFolder (String nome) throws IOException{
		String comand = "MKD " + nome + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
	}
	// ENTRA NO DIRETORIO XXXX
	public void chargeWorkingDir (String path) throws IOException{
		String comand = "CWD " + path + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
	}
	// POE O SERVIDOR EM ESCUTA
	public void pasvMOD () throws IOException {
		String comand = "PASV\r\n";
		this.oscontr.write(comand.getBytes());
		String resp = getControlResp();
		StringTokenizer st = new StringTokenizer(resp);
		st.nextToken("(");
		String ip = st.nextToken(",").substring(1) + "." +
			 st.nextToken(",") + "." +
			 st.nextToken(",") + "." +
			 st.nextToken(",");
		int value1 = Integer.parseInt(st.nextToken(","));
		int value2 = Integer.parseInt(st.nextToken(")").substring(1));
		int port = value1*256 + value2;

		this.dados = new Socket(ip, port);
		this.isDados = dados.getInputStream();
		this.osDados = dados.getOutputStream();
	}
	// COMANDO PARA TIPAR CANAL DE ARQUIVOS
	public void chargeType (String type) throws IOException {
		String comand = "TYPE " + type + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
	}
	// LISTA DIRETORIO
	public String list (String path) throws IOException{
		this.pasvMOD();
		String comand = "LIST " + path + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
		BufferedReader br = new BufferedReader(new InputStreamReader(isDados));
		String resp = "";
		String line;
		while ((line = br.readLine())!= null){
			resp = resp + "\n" + line;
		}
		this.getControlResp();
		return resp;
	}
	// CRIA LISTA DIRETORIO
	public void criaListaArqFTP () throws IOException{
		if (aObjNoFtp.size() == 0) {
			this.pasvMOD();
			String comand = "NLST\r\n";
			this.oscontr.write(comand.getBytes());
			this.getControlResp();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(isDados));
			String resp = "";
			String line;
			while ((line = br.readLine())!= null){
				aObjNoFtp.add(line);
				resp = resp + "\n" + line;
			}
			this.getControlResp();
			aRemovidoFtp.clear();
		}
		else {
			if (aObjNoFtp.size() != 0) {
				aRemovidoFtp.addAll(aObjNoFtp);
				aObjNoFtp.clear();
				this.pasvMOD();
				String comand = "NLST\r\n";
				this.oscontr.write(comand.getBytes());
				this.getControlResp();
				BufferedReader br = new BufferedReader(
						new InputStreamReader(isDados));
				String resp = "";
				String line;
				while ((line = br.readLine())!= null){
					aObjNoFtp.add(line);
					resp = resp + "\n" + line;
				}
				this.getControlResp();
			}
		}
	}
	// BAIXAR ARQUIVOS DO FTP
	public void downloadFile (String nome, String diretorio) throws IOException{
		this.pasvMOD();
		this.chargeType("I");
		String comand = "RETR " + nome + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
		File file = new File (diretorio + nome);
		FileOutputStream fos = new FileOutputStream(file);
		byte[] buf = new byte[1000];
		int len;
		while ((len = this.isDados.read(buf)) != -1) {
			fos.write(buf, 0 , len);
		}
		fos.flush();
		fos.close();
		dados.close();
		this.getControlResp();
	}
	// UPAR ARQUIVOS DO FTP
	public void uploadFile (String nome, String diretorio) throws IOException{
		this.pasvMOD();
		this.chargeType("I");
		String comand = "STOR " + nome + "\r\n";
		this.oscontr.write(comand.getBytes());
		this.getControlResp();
		File file = new File (diretorio + nome);
		FileInputStream fis = new FileInputStream(file);
		byte[] buf = new byte[1000];
		int len;
		while ((len = fis.read(buf)) != -1) {
			this.osDados.write(buf, 0, len);
		}
		fis.close();
		dados.close();
		this.getControlResp();
	}
	// DATA MODIFICA��O ARQUIVO FTP XXXX
	public Date dataModArqFTP(String nome) throws IOException{
		String comand = "MDTM " + nome + "\r\n";
		this.oscontr.write(comand.getBytes());
		String resp = getControlResp().replaceAll(" ","");
		char[] aux = resp.toCharArray();
		for (int i = 0; i < 3; i++) {
			aux[i] = '0';
		}
		resp = new String(aux);
		long data = 0;
		data = Long.parseLong(resp);
		Date myDate = new Date(data);
		return myDate;
	}

}
