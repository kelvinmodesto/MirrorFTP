package heap;

import java.sql.Date;

public class NoDir extends No {
	
	private int qtd = 0;
	
	public NoDir(String nome, Date data) {
		super(nome, data);		
	}
	
	public NoDir(String nome, Date data, int qtd) {
		super(nome, data);
		this.qtd = qtd;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

}
