package heap;

import java.util.ArrayList;
import java.util.List;

public class Heap {
	List<No> heap;

	public Heap() {
		heap = new ArrayList<No>();
	}

	public List<No> getHeap() {
		return heap;
	}

	public void setHeap(List<No> heap) {
		this.heap = heap;
	}
}
