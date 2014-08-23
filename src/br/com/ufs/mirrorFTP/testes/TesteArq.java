package br.com.ufs.mirrorFTP.testes;

import java.util.List;

import br.com.ufs.mirrorFTP.local.ArqEntrada;
import br.com.ufs.mirrorFTP.local.Arquivo;

public class TesteArq {
	private ArqEntrada entrada;
	private Arquivo arq;

	public TesteArq() {
		entrada = new ArqEntrada();
		arq = new Arquivo();
		varrerPasta(entrada.getDirLocal());
	}
	
	public void varrerPasta(String pasta) {
		List<String> lista = arq.getConteudo(pasta);
		System.out.println("Varrendo pasta "+ pasta);
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}
	}

	public static void main(String[] args) {
		new TesteArq();
	}
	
}
