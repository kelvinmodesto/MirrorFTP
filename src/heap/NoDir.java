package heap;

import base.No;

public class NoDir extends No {

	private int qtd = 0;

	public NoDir(String nome, String caminho, long data) {
		super(nome, caminho, data);
	}

	public NoDir(String nome, String caminho, long data, int qtd) {
		super(nome, caminho, data);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + qtd;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
}
