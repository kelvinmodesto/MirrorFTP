package br.com.ufs.mirrorFTP.testes;

import java.util.Scanner;

import br.com.ufs.mirrorFTP.local.ArqEntrada;
import br.com.ufs.mirrorFTP.servidor.FTP;

public class TesteFTP {

	private ArqEntrada entrada;
	private FTP ftp;
	private Scanner scan;

	public TesteFTP() {
		scan = new Scanner(System.in);
		entrada = new ArqEntrada();
		ftp = new FTP();
		iniciar();
	}

	private void iniciar() {
		ftp.conectar(entrada.getHost(), entrada.getPorta());
		ftp.logar(entrada.getUsuario(), entrada.getSenha());
	}

	protected void finalize() {
		ftp.deslogar();
		scan.close();
	}

	private String ler() {
		System.out.print("Digite: ");
		return scan.nextLine();
	}

	private void criarPasta() {
		System.out.print("[Criar Pasta]");
		ftp.criarPasta(ler());
	}

	private void deletarPasta() {
		System.out.print("[Deletar Pasta] ");
		ftp.deletarPasta(ler());
	}

	private void mudarPasta() {
		System.out.print("[Mudar Diretorio] ");
		ftp.mudarDir(ler());
	}

	private void mostrarPasta() {
		System.out.println("[Mostrar Diretorio]");
		ftp.mostrarDirAtual();
	}

	private void listar() {
		System.out.println("[Listar Diretorio]");
		System.out.println(ftp.listarNome(entrada.getDirRemoto()));
	}

	private void baixarArq() {
		System.out.print("[Baixar Arquivo] ");
		ftp.baixarArquivo(entrada.getDirLocal(), ler());
	}

	private void enviarArq() {
		System.out.println("[Enviar Arquivo]");
		ftp.enviarArquivo(entrada.getDirLocal(), ler());
	}

	private void mostrarDataModArq() {
		System.out.println("[Mostrar Data de Modificacao do Arquivo]");
		System.out.println(ftp.getDataModArq(ler()));
	}

	private int lerInt() {
		return Integer.parseInt(ler());
	}

	public void menu() {
		System.out.println("----------------------------------------");
		System.out.println("---Bem vindo ao Teste dos metodos FTP---");
		while (true) {
			System.out.println("----------------------------------------");
			System.out.println("Escolha um dos numeros das opcoes abaixo");
			System.out.println("1 - Criar Pasta");
			System.out.println("2 - Remover Pasta");
			System.out.println("3 - Mudar Pasta");
			System.out.println("4 - Mostrar Pasta");
			System.out.println("5 - Listar Pasta");
			System.out.println("6 - Baixar Arquivo");
			System.out.println("7 - Enviar Arquivo");
			System.out.println("8 - Mostrar Data de Modificacao");
			System.out.println("0 - Sair");
			int i = lerInt();
			switch (i) {
			case 0:
				System.out.println("Saindo...");
				return;
			case 1:
				criarPasta();
				break;
			case 2:
				deletarPasta();
				break;
			case 3:
				mudarPasta();
				break;
			case 4:
				mostrarPasta();
				break;
			case 5:
				listar();
				break;
			case 6:
				baixarArq();
				break;
			case 7:
				enviarArq();
				break;
			case 8:
				mostrarDataModArq();
				break;
			default:
				System.out.println("Escolha uma opcao valida");
			}
		}
	}

	public static void main(String[] args) {
		TesteFTP t = new TesteFTP();
		t.menu();
	}

}
