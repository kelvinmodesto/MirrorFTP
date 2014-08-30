package local;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import heap.NoDir;
import base.Diretorio;
import base.No;

public class Local extends Diretorio {

	private ArqsLocais arqsLocal;
	
	public Local() {
		super();
		arqsLocal = new ArqsLocais();
		iniciarHeap();
	}
	
	protected void iniciarHeap() {
		String nome = entrada.getDirLocal().substring(17);
		NoDir no = new NoDir(nome, entrada.getDirLocal(), 0);
		heap.inserirNo(no);
		no = (NoDir) heap.getNo(0);
		no.setQtd(insContDir(entrada.getDirLocal()));
	}
	
	protected int insContDir(String diretorio) {
		int tamAnt = heap.getTam();
		int tamIns = arqsLocal.construirHeap(diretorio);
		heap.inserirDoHeap(arqsLocal.getHeap());
		for (int i = tamAnt; i < tamAnt+tamIns; i++) {
			No no = heap.getNo(i);
			if (no.getNome().endsWith("/")) {
				int qtd = insContDir(diretorio + no.getNome());
				((NoDir) no).setQtd(qtd);
			}
		}
		return tamIns;
	}
	//Incompleto, terminar de fazer quando a net ajudar ¬¬
	protected boolean converterDataParaRemoto(String data,String nome,String diretorio) throws ParseException{
		File arquivo= new File(diretorio);
		File[] aux= null;
		aux= arquivo.listFiles();
		for(int i=0;i<aux.length;i++){
				if(nome.equals(aux[i].getName())){
					SimpleDateFormat dateFormat= new SimpleDateFormat("yyyyMMddHHmm");
					dateFormat.setTimeZone(TimeZone.getTimeZone("UTC-3"));
					String newLastModifiedString = data;
					Date newLastModifiedDate= dateFormat.parse(newLastModifiedString);
					aux[i].setLastModified(newLastModifiedDate.getTime());
					return true;
				}
		}
		return false;
	}


}
