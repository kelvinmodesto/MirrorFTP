package br.com.ufs.mirrorFTP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Arquivo {
	//ÁREA DE VARIÁVEIS GLOBAIS
	String host,usuario,senha,dirLocal,dirRemoto;
	int porta,intervalo;
	
	
	void read() throws IOException{
	File f = new File("entradas.txt");
	InputStream is = new FileInputStream(f);
	BufferedReader br = new BufferedReader(new InputStreamReader(is));
	String host = br.readLine();
	int porta = new Integer(br.readLine());
	int intervalo = new Integer(br.readLine());
	String usuario = br.readLine();
	String senha = br.readLine();
	String dirLocal = br.readLine();
	String dirRemoto = br.readLine();
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
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


	public void setPorta(int porta) {
		this.porta = porta;
	}


	public int getIntervalo() {
		return intervalo;
	}


	public void setIntervalo(int intervalo) {
		this.intervalo = intervalo;
	}
}
