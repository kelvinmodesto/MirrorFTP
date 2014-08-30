package local;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.swing.JOptionPane;

public class ArqEntrada {
	private File arq;
	private String host, usuario, senha, dirLocal, dirRemoto;
	private int porta, intervalo;

	public ArqEntrada() {
		arq = new File("entradas.txt");
		gerarArquivo(arq);
		this.lerArq();
	}
	
	private void gerarArquivo(File arq) {
		if(!arq.exists())
			try {
				arq.createNewFile();
				escreverArq();
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}
	
	private void lerArq() {
		InputStream is;
		BufferedReader br;
		try {
			is = new FileInputStream(arq);
			br = new BufferedReader(new InputStreamReader(is));
			host = br.readLine();
			porta = new Integer(br.readLine());
			intervalo = new Integer(br.readLine());
			usuario = br.readLine();
			senha = br.readLine();
			dirLocal = br.readLine();
			dirRemoto = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void escreverArq() {
		OutputStream os;
		BufferedWriter bw;
		try {
			os = new FileOutputStream(arq);
			bw = new BufferedWriter(new OutputStreamWriter(os));
			bw.write("ftp.xpg.com.br\n");
			bw.write("21\n");
			bw.write("60\n");
			usuario = JOptionPane.showInputDialog("Digite o usuario");
			bw.write(usuario+"\n");
			senha = JOptionPane.showInputDialog("Digite a senha");
			bw.write(senha+"\n");
			dirLocal = JOptionPane.showInputDialog("Digite o diretorio local");
			bw.write(dirLocal+"\n");
			bw.write("/");
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHost() {
		return host;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getSenha() {
		return senha;
	}

	public String getDirLocal() {
		return dirLocal;
	}

	public String getDirRemoto() {
		return dirRemoto;
	}

	public int getPorta() {
		return porta;
	}

	public int getIntervalo() {
		return intervalo;
	}
}
