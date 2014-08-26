package heap;

public class NoDir extends No {
	
	private int qtd = 0;
	
	public NoDir(String nome, long data) {
		super(nome, data);		
	}
	
	public NoDir(String nome, long data, int qtd) {
		super(nome, data);
		this.qtd = qtd;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public String toString() {
		return super.toString() + " - " + qtd + "]";
	}
	
}
