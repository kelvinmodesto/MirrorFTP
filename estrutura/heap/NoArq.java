package heap;


public class NoArq extends No {

	private float tam = 0;
	
	public NoArq(String nome, long data) {
		super(nome, data);		
	}

	public NoArq(String nome, long data, float tam) {
		super(nome, data);
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

}
