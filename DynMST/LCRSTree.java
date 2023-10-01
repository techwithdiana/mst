package DynMST;

import java.io.PrintWriter;

public class LCRSTree {

	 static class MSTNode 
	    { 
	        int value, name; 
	        MSTNode next, child; 
	        public MSTNode(int value, int name) 
	        { 
	            this.value = value; 
	            this.name = name;
	            next  = null; 
	            child = null; 
	        } 
	    } 
	      
	    // Adds a sibling to a list with starting with n 
	     public MSTNode addSibling(MSTNode node, int value, int name) 
	    { 
	        if(node == null) 
	            return null; 
	        while(node.next != null) 
	            node = node.next; 
	        return(node.next = new MSTNode(value, name)); 
	    } 
	          
	    // Add child Node to a Node 
	     public MSTNode addChild(MSTNode node,int value, int name) 
	    { 
	        if(node == null) 
	            return null; 
	      
	        // Check if child is not empty. 
	        if(node.child != null) 
	            return(addSibling(node.child,value, name)); 	
	        else
	            return(node.child = new MSTNode(value, name)); 
	    } 
	  
	    
	     public void printMST(MSTNode root, int d, PrintWriter pw){
	    	 String depth= "";
	    	 for(int i=0; i < d; i++) {
	    		 depth+=".";
	    	 }
	    	 System.out.println(depth+"V"+(root.name));
	    	 pw.println(depth+"V"+(root.name));
	    	 
	    	 if(root.child!=null) {
	    		 printMST(root.child, d+1, pw);
	    	 }
	    	 if(root.next!=null) {
	    		 printMST(root.next, d, pw);
	    	 }
	    	 return;
	     } 
	     
	     	
}

	
