package DynMST;

import java.io.PrintWriter;
import java.util.Arrays;

public class MinHeap {
	 
	private int cap=10;
	private int size=0;
	
	Edge[] items= new Edge[cap];
	
	
	//Data operations
	
	private void swap(int i1, int i2) {
		Edge cache= items[i2];
		items[i2]= items[i1];
		items[i1] = cache;
		return;
	}
	
	private void checkCapacity() {
		if(cap == size) {
			items= Arrays.copyOf(items, cap*2);
			cap*=2;
		}
	}
	
	public Edge getRoot(){
		if (size==0) throw new IllegalStateException("No Edges available");
		return items[0];
	}
	
	public Edge pullRoot() {
		if (size==0) throw new IllegalStateException("No Edges available");
		Edge ret= items[0];
		items[0]= items[size-1];
		size--;
		heapDown();
		return ret;
	}
	
	public void add(Edge item) {
		checkCapacity();
		items[size]= item;
		size++;
		heapUp();
		
	}
	
	
	private void heapUp() {
		int i= size-1;
		
		while (hasParent(i) && parent(i).cost > items[i].cost) {
			swap(getParentIndex(i), i);
			i= getParentIndex(i);
		}
	}

	private void heapDown() {
		int i = 0;
		
		while(hasLChild(i)) {
			int indexSmallestChild= getLeftChildIndex(i);
			indexSmallestChild= (hasRChild(i)&& rChild(i).cost < lChild(i).cost)?getRightChildIndex(i):indexSmallestChild;
			
			if(items[i].cost < items[indexSmallestChild].cost) {
				return;
			}
			else {
				swap(i,indexSmallestChild);
			}
			i= indexSmallestChild;
		}
	}
	
	public void printHeap(PrintWriter pw) {
		/*
		int depth= 0;
		String depthString="";
		System.out.println("Heap structure for current step:");
		System.out.println();
		pw.println("Heap structure for current step:");
			pw.println();
		for(int i= 0; i < this.size; i++) {
			
			
			depthString="";
			
			if( i== 0) {
				pw.println("V"+(this.items[0].src+1) +"-V"+ (this.items[0].dest+1)+" |"+this.items[0].cost);
				System.out.println("V"+(this.items[0].src+1) +"-V"+ (this.items[0].dest+1)+" |"+this.items[0].cost);
			}
			else {
				depth= (int) (Math.log(i+1)/Math.log(2));

				for(int j= 0; j < depth; j++) {
					depthString+= ".";
				}
				pw.println( depthString+"V"+this.items[i].src +"-V"+ this.items[i].dest+" |"+this.items[i].cost);
				System.out.println(depthString+"V"+this.items[i].src +"-V"+ this.items[i].dest+" |"+this.items[i].cost);
				
				
			}
		}
		pw.println("");
		System.out.println("");
	
		*/
	}

	private int getLeftChildIndex(int pIndex) {return 2*pIndex + 1;}
	private int getRightChildIndex(int pIndex) {return 2*pIndex + 2;}
	private int getParentIndex(int cIndex) {return (cIndex-1)/2;}
	
	private boolean hasLChild(int index) {return getLeftChildIndex(index) < size;}
	private boolean hasRChild(int index) {return getRightChildIndex(index) < size;}
	private boolean hasParent(int index) {return getParentIndex(index) >= 0;}
	
	private Edge lChild(int index) {return items[getLeftChildIndex(index)];}
	private Edge rChild(int index) {return items[getRightChildIndex(index)];}
	private Edge parent(int index) {return items[getParentIndex(index)];}
	
}
