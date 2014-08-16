package mirrorFTP;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class Function_Disco {
	
	// ARRAYS
	ArrayList<String> aObjNaPasta = new ArrayList<>();
	ArrayList<String> aObjRemovidos = new ArrayList<>();
	
	// DATA DE MODIFICAÇÃO ARQUIVO LOCAL
	public Date dataModArqLocal (String nome, String diretorio) {
		File arquivo = new File (diretorio + nome);
		Date myDate = null;
		if(arquivo.isFile()){
			 myDate = new Date(arquivo.lastModified());
		}
		return myDate;
	}
	// CRIA LISTA ARQUIVOS LOCAL
	public void criaListaArqLocal (String diretorio) {
		File arquivo = new File (diretorio);
		File[] aux = null;
		aux = arquivo.listFiles();
		if (this.aObjNaPasta.size() == 0) {
			for (int i = 0; i < aux.length; i++) {
				this.aObjNaPasta.add(aux[i].getName());
				this.aObjRemovidos.clear();
			}
		}
		else {
			aObjNaPasta.addAll(aObjRemovidos);
			aObjNaPasta.clear();
			for (int i = 0; i < aux.length; i++) {
				this.aObjNaPasta.add(aux[i].getName());
			}
		}
	}
}
