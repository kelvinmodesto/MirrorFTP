package br.com.ufs.mirrorFTP.local;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ArqEntrada {
	private String host, usuario, senha, dirLocal, dirRemoto;
	private int porta, intervalo;
	
	public ArqEntrada() {
		this.read();
	}
	void read() {
		File f = new File("entradas.txt");
		InputStream is;
		BufferedReader br;
		try {
			is = new FileInputStream(f);
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

	public void setDirLocal(String dirLocal) {
		this.dirLocal = dirLocal;
	}

	public String getDirRemoto() {
		return dirRemoto;
	}

	public void setDirRemoto(String dirRemoto) {
		this.dirRemoto = dirRemoto;
	}

	public int getPorta() {
		return porta;
	}

	public int getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
}
