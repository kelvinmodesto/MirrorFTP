package testes.local;

import testes.TesteHeap;
import local.ArqLog;

public class TesteArqLog extends TesteHeap {

	private ArqLog arqLog;

	public TesteArqLog() {
		super();
		arqLog = new ArqLog();
		escreverLog();
	}

	protected void iniciar() {
		inserirRemoto();
		inserirLocal();
	}

	private void escreverLog() {
		arqLog.escreverHeap(true, heapLocal);
		arqLog.escreverHeap(false, heapRemoto);
	}

	public static void main(String[] args) {
		new TesteArqLog();
	}

}
