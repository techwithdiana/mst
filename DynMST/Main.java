package DynMST;

import java.util.*;



import java.io.*;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		
		
	
		
		
		
		// Initiate Scanner
		Scanner inputFile = new Scanner(System.in);
		
		// Get input file
		System.out.println("Enter name of input file: ");
		String inputFileName= inputFile.nextLine();
		
		System.out.println("Enter name of output file: ");
		String outputFileName= inputFile.nextLine();
		
		System.out.println("Enter name of directive file:");
		String opFileName= inputFile.nextLine();
		Scanner scanFile = new Scanner(new File("src/"+ inputFileName+".txt"));
		
		inputFile.close();
		
		// Read n(vertices)
		int nV= Integer.parseInt(scanFile.nextLine().trim());
		
		//skip vertices
		for(int i= 0; i < nV; i++) {
			scanFile.nextLine();
		}
		
		// Read n(edges)
		int nE= Integer.parseInt(scanFile.nextLine().trim());
		
		int e1=0;
		int e2=0;
		int weight=0;
		int parCount= 0;
		int edgeCount= 0;
		Edge initEdges[] = new Edge[nE*2];
		
		if(nV!=0 && nE!=0) {
		// Fetch first edge
		String[] array = scanFile.nextLine().split("\\ ");
		
			for(int j=0; j < nE; j++) {
				
				for(int i=0; i < array.length; i++) {
					
					if(!array[i].matches("")) {
						
						switch (parCount) {
						
						case 0: {
							e1= Integer.parseInt(array[i].substring(1).trim())-1;
							parCount++;
							break;
						}
						case 1: {
							e2= Integer.parseInt(array[i].substring(1).trim())-1;
							parCount++;
							break;
						}
						case 2: {
							weight= Integer.parseInt(array[i].trim());
							parCount++;
							break;
						}
						
						default:
							break;
							
						}
					}
		
				
				}
				array= scanFile.hasNextLine()? scanFile.nextLine().split("\\ "): null;
				initEdges[edgeCount]= new Edge(e1, e2, weight);
				initEdges[edgeCount+1]= new Edge(e2, e1, weight);
				edgeCount+=2;
				parCount= 0;
				
				
		 
		
		}	
	}		 
       
			File outputFile= new File(outputFileName+".txt");
			PrintWriter pw= new PrintWriter(outputFile);
			
			
			Graph graph = new Graph(createList(initEdges));
			graph.printGraph(nV, pw);
			
	       
	        pw.println(nV +"x"+nV+" Matrix for the inputted graph:");
	        pw.println("");
	        graph.printGraph(nV, pw);
	        graph.prim(nV,pw);
	        
	        int stop=0;
	       
	       if(nV!=0  ) { 
	    	   while(graph.resultTree[stop]!=null) {
	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
	    		   stop++;
	    	   }
	       }
	        pw.close();
	        
	        Scanner opFile = new Scanner(new File("src/"+opFileName+".txt"));
	        while(opFile.hasNextLine()) {
	        	
	        	String[] op = opFile.nextLine().split("\\ ");
	        	
	        	switch(op[0]) {
	        	
		        	case "PG":{
		        		graph.printGraph(nV, pw);
		        		break;
		        	}
		        	
		        	case "PM":{
		        		if(nV!=0  ) { 
		     	    	  for(int i=0; i < graph.getNTrees(nV); i++) {
		     	    		   graph.resultTree[i].printMST(graph.mstNodeList[i][0], 0, pw);
		     	    		   
		     	    		  
		     	    	   }
		        		}
		        		break;
		        	}
		        	
		        	case "IV":{
		        		graph.addVertex(Integer.parseInt(op[1].substring(1)),nV);
		        		nV++;
		        		
		        		System.out.println("New Vertex has been added");
		        		
		        		graph.printGraph(nV, pw);
		        		
		        		graph.prim(nV, pw);
		        		
		        		/*while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		     	    	   }*/
		        		break;
		        	}
		        	
		        	case "DV":{
		        		graph.removeVertex(Integer.parseInt(op[1].substring(1)), nV);
		        		nV--;
		        		
		        		System.out.println("Vertex V"+Integer.parseInt(op[1].substring(1))+" has been removed");
		        		
		        		graph.printGraph(nV, pw);
		        		
		        		graph.prim(nV, pw);
		        		
		        	/*	while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		     	    	   }*/
		        		break;
		        		
		        	}
		        	
		        	case "IE":{
		        		graph.addEdge(Integer.parseInt(op[1].substring(1)), Integer.parseInt(op[2].substring(1)), Integer.parseInt(op[3]), nV);
		        		
		        		System.out.println("Edge has been added");
		        		
		        		graph.printGraph(nV, pw);
		        		graph.prim(nV, pw);
		        		
		        		while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		     	    	   }
		        		break;
		        		
		        	}
		        	
		        	case "DE":{
		        		graph.removeEdge(Integer.parseInt(op[1].substring(1)), Integer.parseInt(op[2].substring(1)),nV);
		        	
		        		System.out.println("Edge has been removed");
		        		
		        		graph.printGraph(nV, pw);
		        		graph.prim(nV, pw);
		        		
		        		while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		     	    	   }
		        		
		        		break;
		        		
		        	}
		        	
		        	case "IW":{
		        		graph.incWeight(Integer.parseInt(op[1].substring(1)), Integer.parseInt(op[2].substring(1)),  Integer.parseInt(op[3]), nV);
		        		
		        		System.out.println("Weight has been increased");
		        		
		        		graph.printGraph(nV, pw);
		        		graph.prim(nV, pw);
		        		
		        		while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		     	    	   }
		        		break;
		        	}
		        	
		        	case "DW": {
		        		graph.decWeight(Integer.parseInt(op[1].substring(1)), Integer.parseInt(op[2].substring(1)),  Integer.parseInt(op[3]));
		        		
		        		System.out.println("Weight has been increased");
		        		
		        		graph.printGraph(nV, pw);
		        		graph.prim(nV, pw);
		        		
		        		while(graph.resultTree[stop]!=null) {
		     	    		   graph.resultTree[stop].printMST(graph.mstNodeList[stop][0], 0, pw);
		     	    		   stop++;
		        	}
		        		break;
	        	}
	        	
	        	}
	    } }  
	
	
	
	
	static List<Edge> createList(Edge[] edgesL){
		List <Edge> ret= Arrays.asList(edgesL);
		return ret;
		
	}
}
