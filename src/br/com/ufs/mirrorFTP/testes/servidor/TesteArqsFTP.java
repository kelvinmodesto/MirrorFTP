package br.com.ufs.mirrorFTP.testes.servidor;

import java.util.List;

import br.com.ufs.mirrorFTP.local.ArqEntrada;
import br.com.ufs.mirrorFTP.servidor.ArqsFTP;

public class TesteArqsFTP {

	private ArqsFTP arqsFTP;
	private ArqEntrada entrada;

	public TesteArqsFTP() {
		entrada = new ArqEntrada();
		arqsFTP = new ArqsFTP();
		varrerPasta(entrada.getDirRemoto());
	}
	
	public void varrerPasta(String pasta) {
		List<String> lista = arqsFTP.getConteudo(pasta);
		System.out.println("Listando o conteudo de " + pasta);
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).startsWith("*"))
				varrerPasta(pasta + lista.get(i).substring(1) + "/");
			else
				System.out.println(lista.get(i));
		}
		System.out.println("Fim da listagem de " + pasta);
	}
	
	public static void main(String[] args) {
		new TesteArqsFTP();
	}
}
