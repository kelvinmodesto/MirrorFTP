package mirrorFTP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;


@SuppressWarnings("unused")
public class Drop_FTP {
	
	// VARIAVÉIS DO APP
	private int intervalo;		
	private Function_Disco pasta;
	private Function_FTP ftp;
	private String dirLocal;
	private String dirRemoto;
	
	// ARRAYS
	private ArrayList<String> aObjRemovidos = new ArrayList<>();
	private ArrayList<String> aObjAmbosDir = new ArrayList<>();
	ArrayList<String> aObjEnvFtp = new ArrayList<>();
	ArrayList<String> aObjRecFtp = new ArrayList<>();
	
	// CONSTRUTOR DA CLASSE
	public Drop_FTP () throws IOException {
		pasta = new Function_Disco();
		ftp = new Function_FTP();
	}
	
	// INICIA A CAPTURA DOS DADOS
	@SuppressWarnings("resource")
	public void iniciaDados () throws IOException {
		File f = new File("entradas.txt");
		InputStream isFile = new FileInputStream(f);
		BufferedReader brFile = new BufferedReader(
				new InputStreamReader(isFile));
		ftp.setHost(brFile.readLine());
		ftp.setPort(brFile.readLine());
		this.intervalo = new Integer(brFile.readLine());
		ftp.setUser(brFile.readLine());
		ftp.setPass(brFile.readLine());
		this.dirLocal = brFile.readLine();
		this.dirRemoto = brFile.readLine();
	}
	// AQUI OS COMANDO PARA A APLICAÇÃO
	// INICIA APP
	public void iniciaAPP () throws IOException {
		this.iniciaDados();
		ftp.conect();
		ftp.login();
		ftp.criaListaArqFTP();
		pasta.criaListaArqLocal(dirLocal);
		this.processaDados();
		System.out.println("--------------------FTP--------------------------");
		imprimirStatus(ftp.aObjNoFtp);
		System.out.println("--------------------DISC--------------------------");
		imprimirStatus(pasta.aObjNaPasta);
		System.out.println("--------------------AMBOS-------------------------");
		imprimirStatus(aObjAmbosDir);
		System.out.println("----------------------ENV FTP------------------------");
		imprimirStatus(aObjEnvFtp);
		System.out.println("---------------------REC FTP--------------------------");
		imprimirStatus(aObjRecFtp);
		sinc();
	}
	
	// MÉTODOS PARA O APPBOX
	// COMPARA ARQUIVOS DE AMBOS OS DIRETÓRIOS COM BASE NA DATA
	private void comparaArquivosPorData () throws IOException {
		for (int i = 0; i < aObjAmbosDir.size(); i++) {
			String nome = aObjAmbosDir.get(i);
			Date data1 = ftp.dataModArqFTP(nome);
			Date data2 = pasta.dataModArqLocal(nome, dirLocal);
				if (data1.after(data2)) {
					if (aObjRecFtp.contains(nome)){
						aObjAmbosDir.remove(aObjAmbosDir.get(i));
						i--;
					}
					else {
						aObjRecFtp.add(nome);
					}
				}
				if (data1.before(data2)) {
					if (this.aObjEnvFtp.contains(nome)) {
						aObjAmbosDir.remove(aObjAmbosDir.get(i));
						i--;
					}
					else {
						this.aObjEnvFtp.add(nome);
					}
				}
		}
	}
	// COMPARA ARQUIVOS COM BASE NO NOME
	private void comparaArquivos () throws IOException {
		this.aObjEnvFtp.clear();
		this.aObjRecFtp.clear();
		ArrayList<String> aux = new ArrayList<>();
		aux.addAll(ftp.aObjNoFtp);
		aux.addAll(pasta.aObjNaPasta);
		for (int i = 0; i < aux.size(); i++) {
			if ((ftp.aObjNoFtp.contains(aux.get(i))) && (pasta.aObjNaPasta.contains(aux.get(i)))) {
				this.aObjAmbosDir.add(aux.get(i));
				aux.remove(aux.get(i));
				i--;
			}
		}
		for (int i = 0; i < aux.size(); i++) {
			if (ftp.aObjNoFtp.contains(aux.get(i))) {
				this.aObjRecFtp.add(aux.get(i));
			}
			if (pasta.aObjNaPasta.contains(aux.get(i))) {
				this.aObjEnvFtp.add(aux.get(i));
			}
		}
	}
	// METODO PARA A SINCRONIZAÇÃO DOS DIRETÓRIOS
	public void sinc () throws IOException {
		if (this.aObjEnvFtp.size() > 0){
			for (int i = 0; i < this.aObjEnvFtp.size(); i++) {
				String nome = this.aObjEnvFtp.get(i);
				System.out.println("Fazendo Upload dos arquivos... Aguarde...");
				ftp.uploadFile(nome, this.dirLocal);
				System.out.println("Upload dos arquivos terminado...");
			}		
		}
		if (this.aObjRecFtp.size() > 0){
			for (int i = 0; i < this.aObjRecFtp.size(); i++) {
				String nome = this.aObjRecFtp.get(i);
				System.out.println("Fazendo download dos arquivos... Aguarde...");
				ftp.uploadFile(nome, this.dirLocal);
				System.out.println("Download dos arquivos terminado...");
			}
		}
		aObjEnvFtp.clear();
		aObjRecFtp.clear();
		System.out.println("Sincronização Concluída...");
	}
	// PROCESSA OS DADOS OBTIDOS E FORMA A LISTA DE AQUIVOS DO DISCO, DO FTP E POR ULTIMO GERA ARRAYS
	// PARA A SINCRONIA DE DADOS
	public void processaDados () throws IOException{
		this.comparaArquivos();
		this.comparaArquivosPorData();
	}
	// MÉTODO PARA IMPRIMIR O CONTEÚDO DOS ARRAYSLIST
	@SuppressWarnings("rawtypes")
	public void imprimirStatus(ArrayList array) {
		for (int i = 0; i < array.size(); i++) {
			String nome = (String) array.get(i);
			System.out.println("Arquivo: " + nome);
		}
	}
	
}
