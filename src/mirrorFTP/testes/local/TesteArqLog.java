package mirrorFTP.testes.local;

import mirrorFTP.local.ArqLog;
import mirrorFTP.testes.TesteHeap;

public class TesteArqLog extends TesteHeap {

	private ArqLog arqLog;

	public TesteArqLog() {
		arqLog = new ArqLog();
		iniciar();
	}

	protected void iniciar() {
		inserirRemoto();
		inserirLocal();
		escreverLog();
	}

	private void escreverLog() {
		if (arqLog != null) {
			arqLog.escreverHeap(true, heapLocal);
			arqLog.escreverHeap(false, heapRemoto);
		}
		else
			System.out.println("Nulo");
	}

	public static void main(String[] args) {
		new TesteArqLog();
	}

}
