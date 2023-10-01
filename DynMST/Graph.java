package DynMST;

import java.io.PrintWriter;
import java.util.*;

import DynMST.LCRSTree.MSTNode;

class Graph {
    // node of adjacency list 
    static class Node {
        int value, weight, id;
        Node(int value, int weight, int id)  {
        	this.value= value; 
            this.weight = weight;
            this.id= id;
        }
    };
 

    LCRSTree resultTree[];
    MSTNode[][] mstNodeList= new MSTNode[20][20];
    List<List<Node>> adj_list = new ArrayList<>();
 
    //Constructor
    public Graph(List<Edge> edges)
    {
        // list allocation
        for (int i = 0; i < 20; i++)
            adj_list.add(i, new ArrayList<>());
 
        // add all edges to adj. List
        for (Edge e : edges)
        {
            // allocate new node in adjacency List from src to dest
            adj_list.get(e.src).add(new Node(e.dest, e.cost, e.src+1));
        }
        this.resultTree= new LCRSTree[20];
    }
    void prim(int v, PrintWriter pw) {
    	int nT=0;
    	int nRoot=0;
    	
    	
    	
    	boolean selectedSubTree[]= new boolean[v];
    	
    	boolean selectedAll[]= new boolean[v];
    	Arrays.fill(selectedAll, false);
    	
    	while(!primCompleted(selectedAll, v)) {
    		
    		this.resultTree[nT]=new LCRSTree();
    		
    		Arrays.fill(selectedSubTree, false);
    		nRoot= getSubTreeRoot(selectedAll);
	    	selectedSubTree[nRoot]=true;
	    	int edge=0;
	    	int totalVal=0;
	    	MSTNode root;
	    	int vSubTree=getNinTree(v, getSubTreeRoot(selectedAll));
	    	
	    	if(!this.adj_list.get(nRoot).isEmpty()) {
	    		root= new MSTNode(nRoot, this.adj_list.get(nRoot).get(0).id);
	    	}
	    	else {
	    		root= new MSTNode(nRoot, nRoot);
	    	}
	    	
			mstNodeList[nT][0]= root;	
	    	
	    	
		    	while (edge < vSubTree-1 && vSubTree > 1) {
		    		MinHeap heap= new MinHeap();
		    		
		    		
		    		for(int i=nRoot; i<v;i++) {
		    			if(selectedSubTree[i]==true) {
		    				for(int j=0; j < this.adj_list.get(i).size(); j++ ) {
		    					if(!selectedAll[this.adj_list.get(i).get(j).value] && !selectedSubTree[this.adj_list.get(i).get(j).value]) {
		    						heap.add(new Edge(i, this.adj_list.get(i).get(j).value, this.adj_list.get(i).get(j).weight));
		    					}
		    				}
		    			}
		    		}
		    	
		    		heap.printHeap(pw);
		    		
		    	      selectedSubTree[heap.getRoot().dest] = true;
		    	      mstNodeList[nT][edge+1]= resultTree[nT].addChild(mstNodeList[nT][findIndexMSTNode(heap.getRoot().src,nT, edge)], heap.getRoot().dest, this.adj_list.get(heap.getRoot().dest).get(0).id);
		    	   
		    	      edge++;
		    	      totalVal+= heap.getRoot().cost;
		    	}
		    	System.out.println("");
		    	resultTree[nT].printMST(mstNodeList[nT][0], 0, pw);
		    	System.out.println("");
		    	System.out.println("MST-TotalCost: "+totalVal);
		    	pw.println("");
		    	pw.println("MST-TotalCost: "+totalVal);
		    	nT++;
		    	selectedAll=Arrays.copyOf(calcSelectedAll(selectedAll, selectedSubTree), selectedAll.length); 
		    }
    }    	
    
    private boolean[] calcSelectedAll(boolean[] selectedAll, boolean[] selectedSubTree) {
		for(int i=0; i < selectedSubTree.length; i++ ) {
			if(selectedSubTree[i]== true) {
				selectedAll[i]= true;
			}
		}
		return selectedAll;
	}
	private int getSubTreeRoot(boolean[] selectedAll) {
		for(int i=0; i < selectedAll.length; i++) {
			if(selectedAll[i]==false) { return i;}
		}
		return 0;
	}
	private boolean primCompleted(boolean[] selectedAll, int v) {
		for(int i=0; i < v; i++) {
			if(selectedAll[i]== false) {return false;}
		}
		return true;
	}
	
	private int getNinTree(int v, int r) {
		Boolean[]inTree= new Boolean[v];
		Arrays.fill(inTree, false);
		inTree[r]= true;
		int ret=0;
		
		for(int i= r; i < v; i++) {
			for(int j=0; j < this.adj_list.get(i).size();j++) {
				if(inTree[i]==true) {
					inTree[this.adj_list.get(i).get(j).value]=true;
				}
			}
		}
		
		for(int k= 0; k < inTree.length; k++) {
			if(inTree[k]==true) {
				ret++;
			}
		}
		
		return ret;
	}
	private int findIndexMSTNode(int val,int nT, int nE) {
    	int ret=42;
    	for(int i= 0; i <= nE; i++) {
    		ret= this.mstNodeList[nT][i].value==val?i:ret;
    	}
    	return ret;
    }
    
    public void printGraph(int v, PrintWriter pw) {
    	System.out.println("");
    	System.out.println("");
    	String printLine="VxV||";
    	if(!this.adj_list.get(0).isEmpty()) {
	    	for(int i=0; i< v; i++) {
	    		printLine= printLine+(this.adj_list.get(i).get(0).id)+"   |";
	    	}
    	}
	    	
    	System.out.println(printLine);
    	pw.println(printLine);
    	
    	int[]line= new int[v];
    	Arrays.fill(line, 0);
    	
    	
    	
    	
    	
    	printLine="===||";
    	for(int i=0; i < v; i++) {
    		printLine=printLine+"=====" ;
    	}
    	
    	
    	System.out.println(printLine);
    	pw.println(printLine);
    	
    	
    	for(int i=0; i < v; i++) {
    		Arrays.fill(line, 0);
    		printLine="";
    		for(int j=0; j <adj_list.get(i).size();j++) {
    			line[this.adj_list.get(i).get(j).value]=this.adj_list.get(i).get(j).weight;
    		}
    		if(this.adj_list.get(i).isEmpty()) {
    			printLine=("E"+(i+1)+"  ||")+printLine;
    		}
    		else {
    		printLine=((this.adj_list.get(i).get(0).id)+"  ||")+printLine;
    		}
    		
    		String inline="";
    		for( int c: line) {
    			inline=inline+Integer.toString(c);
    			while(inline.length()%5!=4) {inline=inline+(" ");}
    			inline=inline+"|";
    			
    		}
    		
    		System.out.println(printLine+inline);
    		pw.println(printLine+inline);
    		printLine="---||";
    		for(int k= 0; k < v; k++) {
    			printLine+= "-----";
    		}
    		System.out.println(printLine);
    		pw.println(printLine);
    		
    		
    		
    		
    	}
    	
    }
	public void addVertex(int i, int v) {
		this.adj_list.get(v).add(new Node(v ,0, i));
		return;
	}
	
	public void removeVertex(int i, int v) {
		int vPos= getVertexPos(i,v);
		
		this.adj_list.remove(vPos);
		
		for(int k=0; k < v-1; k++ ) {
			
			for(int j=0; j < this.adj_list.get(k).size(); j++) {
				
				if(this.adj_list.get(k).get(j).value > vPos){
					this.adj_list.get(k).get(j).value--;
				}
				
				else if(this.adj_list.get(k).get(j).value==vPos) {
					this.adj_list.get(k).remove(j);
					
					j--;
				}
				
			}
		}
	}
	
	private int getVertexPos(int i, int v) {
		int ret= 0;
		
		for(int k = 0; k < v; k++ ) {
			if(!this.adj_list.get(k).isEmpty()&&this.adj_list.get(k).get(0).id==i) {
				return k;
			}
		}
			
		return ret;
	}
	public void addEdge(int v1, int v2, int weight, int nV) {
		this.adj_list.get(getVertexPos(v1, nV)).add(new Node(getVertexPos(v2, nV), weight, v1));
		this.adj_list.get(getVertexPos(v2, nV)).add(new Node(getVertexPos(v1, nV), weight, v2));
	}
	
	public void removeEdge(int v1, int v2, int nV) {
		int iV1= getVertexPos(v1, nV);
		int iV2= getVertexPos(v2, nV);
		for(int i= 0; i < this.adj_list.get(iV1).size(); i++) {
			if(this.adj_list.get(iV1).get(i).value == iV2) {
				this.adj_list.get(iV1).remove(i);
				i--;
			}
		}
		
		for(int i= 0; i < this.adj_list.get(iV2).size(); i++) {
			if(this.adj_list.get(iV2).get(i).value == iV1) {
				this.adj_list.get(iV2).remove(i);
				i--;
			}
		}
		
		if(this.adj_list.get(iV1).isEmpty()) {
			this.adj_list.get(iV1).add(new Node(iV1,0,v1));
		}
		if(this.adj_list.get(iV2).isEmpty()) {
			this.adj_list.get(iV2).add(new Node(iV2,0,v2));
		}
	}
	
	public void incWeight(int v1, int v2, int inc, int nV) {
		int iDest1= 0;
		int iV1= getVertexPos(v1, nV);
		int iV2= getVertexPos(v2, nV);
		for(int i=0; i < this.adj_list.get(iV1).size(); i++) {
			if(this.adj_list.get(iV1).get(i).value == iV2) {
				iDest1=i;
			}
		}
		this.adj_list.get(iV1).get(iDest1).weight+= inc;
		
		int iDest2= 0;
		for(int i=0; i < this.adj_list.get(getIndexV(v2,nV)).size(); i++) {
			if(this.adj_list.get(iV2).get(i).value == iV1) {
				iDest2=i;
			}
		}
		this.adj_list.get(iV2).get(iDest2).weight+= inc;
		
		
	}
	
	private int getIndexV(int v1, int nV) {
		for(int i=0; i < nV; i++) {
			if(this.adj_list.get(i).get(0).id== v1) {
				return i;
			}
		}
		return 0;
	}
	public void decWeight(int v1, int v2, int dec) {
		this.adj_list.get(v1).get(v2).weight-= dec; 
	}
	public int getNTrees(int v) {
		int count=0;
		boolean[] selectedAll= new boolean[v];
		Arrays.fill(selectedAll, false);
		int r=0;
		boolean[] selectedSubtree= new boolean[v];
		if(v==0) {System.out.println("There is no MST --> No Vertices"); return 0;}
		while(primCompleted(selectedAll, v)) {
			r=getSubTreeRoot(selectedAll);
			for(int i= r; i < v; i++) {
				for(int j=0; j < this.adj_list.get(i).size();j++) {
					if(selectedSubtree[i]==true) {
						selectedSubtree[this.adj_list.get(i).get(j).value]=true;
					}
				}
				
			}
			selectedAll=Arrays.copyOf(calcSelectedAll(selectedAll, selectedSubtree), selectedAll.length);
			count++;
		}	
		
		return count;
	}
	
}