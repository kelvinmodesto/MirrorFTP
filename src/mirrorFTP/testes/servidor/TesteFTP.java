package mirrorFTP.testes.servidor;

import java.util.Scanner;

import mirrorFTP.local.ArqEntrada;
import mirrorFTP.servidor.FTP;

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
		ftp.conectar();
		ftp.logar();
		ftp.relogar();
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
		System.out.println(ftp.listarConteudo(ler()));
	}

	private void baixarArq() {
		System.out.print("[Baixar Arquivo] ");
		ftp.baixarArquivo(entrada.getDirLocal(), ler());
	}

	private void enviarArq() {
		System.out.println("[Enviar Arquivo]");
		ftp.enviarArquivo(entrada.getDirLocal(), ler());
	}

	private void excluirArq() {
		System.out.println("[Excluir Arquivo]");
		ftp.deletarArquivo(entrada.getDirRemoto(), ler());
	}

	private void detalharArq() {
		System.out.println("[Detalhar Arquivo]");
		ftp.statusArquivo(entrada.getDirRemoto(), ler());
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
			System.out.println("10 - Criar Pasta");
			System.out.println("11 - Remover Pasta");
			System.out.println("12 - Mudar Pasta");
			System.out.println("13 - Mostrar Pasta");
			System.out.println("14 - Listar Pasta");
			System.out.println("20 - Baixar Arquivo");
			System.out.println("21 - Enviar Arquivo");
			System.out.println("22 - Excluir Arquivo");
			System.out.println("23 - Detalhar Arquivo");
			System.out.println("24 - Data do Arquivo");
			System.out.println("0 - Sair");
			int i = lerInt();
			switch (i) {
			case 0:
				System.out.println("Saindo...");
				return;
			case 10:
				criarPasta();
				break;
			case 11:
				deletarPasta();
				break;
			case 12:
				mudarPasta();
				break;
			case 13:
				mostrarPasta();
				break;
			case 14:
				listar();
				break;
			case 20:
				baixarArq();
				break;
			case 21:
				enviarArq();
				break;
			case 22:
				excluirArq();
				break;
			case 23:
				detalharArq();
				break;
			case 24:
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
