package br.com.ufs.mirrorFTP.servidor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.ufs.mirrorFTP.Arquivos;
import br.com.ufs.mirrorFTP.local.ArqEntrada;

public class ArqsFTP extends Arquivos {
	private ArqEntrada entrada;
	private FTP ftp;

	public ArqsFTP() {
		entrada = new ArqEntrada();
		ftp = new FTP();
		iniciar();
	}

	private void iniciar() {
		ftp.conectar(entrada.getHost(), entrada.getPorta());
		ftp.logar(entrada.getUsuario(), entrada.getSenha());
	}
	//Incompleto, pode implementar esse método para recomeçar todo o processo
	private void reiniciar(){
		dormir();
		iniciar();
	}
	
	protected void finalize() {
		ftp.deslogar();
	}
	//Falta fazer todos os testes, mas, acho que ta certo
	public List<String> getConteudo(String diretorio) {
		List<String> conteudo = new ArrayList<String>();
		String nlist= ftp.listarNome(diretorio);
		
		if(nlist != null){
			String[] listArq= nlist.split("\n");
			for(int i=0;i< listArq.length;i++){
				File f=new File(listArq[i]);
				if(f.isFile()){
					conteudo.add(listArq[i]);
				}else{
					conteudo.add("*"+listArq[i]);
				}
			}
		}
		return conteudo;
	}
	public void dormir(){
		try {
			Thread.sleep(entrada.getIntervalo());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
