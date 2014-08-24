package br.com.ufs.mirrorFTP.testes;

import java.util.List;

import br.com.ufs.mirrorFTP.local.ArqEntrada;
import br.com.ufs.mirrorFTP.local.ArqsLocais;

public class TesteArqs {
	private ArqEntrada entrada;
	private ArqsLocais arq;

	public TesteArqs() {
		entrada = new ArqEntrada();
		arq = new ArqsLocais();
		varrerPasta(entrada.getDirLocal());
	}

	public void varrerPasta(String pasta) {
		List<String> lista = arq.getConteudo(pasta);
		System.out.println("Listando o conteudo da pasta " + pasta);
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).startsWith("*"))
				varrerPasta(pasta + lista.get(i).substring(1) + "/");
			else
				System.out.println(lista.get(i));
		}
		System.out.println("Fim da listagem da pasta " + pasta);
	}

	public static void main(String[] args) {
		new TesteArqs();
	}
}
