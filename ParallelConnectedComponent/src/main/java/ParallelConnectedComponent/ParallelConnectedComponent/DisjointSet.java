package ParallelConnectedComponent.ParallelConnectedComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class DisjointSet {

	int[] array;
	int[] size;
	int n;
	void initialize()
	{
		array=new int[n];
		size=new int[n];
		
		for(int i=0;i<n;i++)
		{
			array[i]=i;
			size[i]=1;
		}
	}

	int root(int i)
	{
		while(array[i]!=i)
		{
			array[i]=array[array[i]] ; 
			i = array[i]; 
		}
		return i;
	}
	void union(int a,int b)
	{
		int root_A = root(a);
		int root_B = root(b);
		if(size[root_A] < size[root_B ])
		{
			array[ root_A ] = array[root_B];
			size[root_B] += size[root_A];
		}
		else
		{
			array[ root_B ] = array[root_A];
			size[root_A] += size[root_B];
		}
	}
	List<Edge> getEdgeList()
	{
		List<Edge> list=new ArrayList<Edge>();
		for(int i=0;i<n;i++)
		{
			if(array[i]!=i)
			{
				int a=root(i);
				int b=i;
				list.add(new Edge(a,b));
			}
		}	
		return list;
	}
	void printGraphFromDisjointSet()
	{
		List<Edge> list=getEdgeList();
		Iterator<Edge> edgeit=list.iterator();
		Graph graph = new SingleGraph("FacebookGraph");
		for(int i=0;i<n;i++)
		{
			graph.addNode(i+"");
		}
		int j=0;
		while(edgeit.hasNext())
		{
			Edge e=edgeit.next();
			String edgeTitle="e"+j;
    		String nodeA=e.getA()+"";
    		String nodeB=e.getB()+"";
    		graph.addEdge(edgeTitle,nodeB,nodeA);
			j++;
		}
		graph.display();
	}
	boolean find(int a,int b)
	{
		if(root(a)==root(b))       //if A and B have same root,means they are connected.
			return true;
		else
			return false;
	}
	int disconnectedComponents()
	{
		int cnt=0;
		for(int i=0;i<n;i++)
		{
			if(array[i]==i)
			  cnt++;
		}
		return cnt;
	}
	void printDisjointSet()
	{
		for(int i=0;i<n;i++)
		{
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
}
