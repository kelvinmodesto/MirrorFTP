package heap;

import java.util.ArrayList;
import java.util.List;

import base.No;

public class Heap {
	List<No> lista;

	public Heap() {
		lista = new ArrayList<No>();
	}

	public int getTam() {
		return lista.size();
	}

	public No getNo(int index) {
		return lista.get(index);
	}

	public No getNo(String nome) {
		for (int i = 0; i < getTam(); i++) {
			if (getNo(i).getNome().equals(nome))
				return getNo(i);
		}
		return null;
	}

	public void inserirNo(No no) {
		lista.add(no);
	}

	public void inserirNo(int ind, No no) {
		lista.add(ind, no);
	}

	public void inserirDoHeap(Heap heap) {
		for (int i = 0; i < heap.getTam(); i++) {
			inserirNo(heap.getNo(i));
		}
	}

	public void removerNo(int ind) {
		lista.remove(ind);
	}

	public void substituirNo(int ind, No no) {
		removerNo(ind);
		inserirNo(ind, no);
	}

	public void limparHeap() {
		lista.clear();
	}

	public String toString() {
		String aux = "";
		for (int i = 0; i < getTam(); i++) {
			aux += getNo(i) + "\n";
		}
		return aux;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Heap other = (Heap) obj;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else {
			if (other.lista == null)
				return false;
			if (getTam() != other.getTam())
				return false;
			else {
				boolean resultado = true;
				for (int i = 0; i < getTam(); i++) {
					resultado &= getNo(i).equals(other.getNo(i));
					resultado &= getNo(i).getData() != other.getNo(i).getData();
				}
				return resultado;
			}
		}
		return true;
	}
}
