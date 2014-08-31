package heap;

import base.No;

public class NoArq extends No {

	private float tam = 0;

	public NoArq(String nome, String caminho, long data) {
		super(nome, caminho, data);
	}

	public NoArq(String nome, String caminho, long data, float tam) {
		super(nome, caminho, data);
		this.tam = tam;
	}

	public float getTam() {
		return tam;
	}

	public void setTam(float tam) {
		this.tam = tam;
	}

	public String toString() {
		return super.toString() + " - " + tam + "]";
	}
	
	public boolean isEqualsData(No no) {
		return data == no.getData();
	}

	public boolean isMaiorData(No no) {
		return data > no.getData();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(tam);
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
		NoArq other = (NoArq) obj;
		if (Float.floatToIntBits(tam) != Float.floatToIntBits(other.tam))
			return false;
		return true;
	}

}
