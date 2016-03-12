package ParallelConnectedComponent.ParallelConnectedComponent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Hello world!
 *
 */
public class App 
{
	//static int adjacency[][]={{0,1,1,1,0,0,0},{1,0,1,0,0,0,0},{1,1,0,1,1,0,0},{1,0,1,0,1,0,0},{0,0,1,1,0,0,0},{0,0,0,0,0,0,1},{0,0,0,0,0,1,0}};
	static int adjacency[][]=new int[4039][4039];
	public static DisjointSet merge(DisjointSet d1,DisjointSet d2)
	{
		for(int i=0;i<d1.n;i++)
		{
			if(d1.array[i]!=i)
			{
				int a=d1.root(i);
				int b=i;
				//System.out.println("Comparing"+a+","+b);
				//d2.printDisjointSet();
				int Baroot=d2.root(a);
				int Bbroot=d2.root(b);
				//System.out.println("with"+Baroot+","+Bbroot);
				if(Baroot!=Bbroot)
				{
					d2.union(Baroot, Bbroot);
					//System.out.println("union done");
				}
			}
		}
		return d2;
	}
	public static DisjointSet parallelize() throws InterruptedException {
        /*RangeSummer r1 = new RangeSummer(data, 0, data.length/2);
        RangeSummer r2 = new RangeSummer(data, data.length/2, data.length);
        r1.start();
        r2.start();
        r1.join();
        r2.join();
        return r1.getValue() + r2.getValue();
        */
		ParallelDistribution p1=new ParallelDistribution(adjacency,0,2019,4039);
		ParallelDistribution p2=new ParallelDistribution(adjacency,2020,4038,4039);
		p1.start();
		p2.start();
		p1.join();
		p2.join();
		//p1.getDisjointSet().printGraphFromDisjointSet();
		//p2.getDisjointSet().printGraphFromDisjointSet();
		return merge(p2.getDisjointSet(),p1.getDisjointSet());
    }
	public static void main( String[] args ) throws IOException, InterruptedException
    {
    	
    	File file = new File("E://BiggestProjectsof4thyear//JGraphTUnionfind//facebookDataset.txt");
    	String edgenames[][]=new String[88234][2];
    	readFile(file,edgenames);
    	System.out.println("Retreival Done");
    	long startTime = System.currentTimeMillis();
    	//int[][] adjacency=new int[4039][4039];
    	for(int i=0;i<4039;i++)
    		for(int j=0;j<4039;j++)
    			adjacency[i][j]=0;
    	
    	for(int i=0;i<88234;i++)
    	{
    		int a=Integer.parseInt(edgenames[i][0]);
    		int b=Integer.parseInt(edgenames[i][1]);
    		
    		   adjacency[a][b]=1;
    		   adjacency[b][a]=1;
    	}
    	/*
    	for(int i=0;i<4039;i++)
    	{
    		for(int j=0;j<4039;j++)
    		{
    			System.out.print(adjacency[i][j]+" ");
    		}
    		System.out.println();
    	}
    	*/
    	/*
    	for(int i=0;i<7;i++)
    	{
    		for(int j=0;j<7;j++)
    		{
    			System.out.print(adjacency[i][j]+" ");
    		}
    		System.out.println();
    	}
    	*/
    	DisjointSet result=parallelize();
    	System.out.println(result.disconnectedComponents());
    	long endTime   = System.currentTimeMillis();
    	long totalTime = endTime - startTime;
    	System.out.println(totalTime);
    }
    private static void readFile(File fin,String edgenames[][]) throws IOException {
		FileInputStream fis = new FileInputStream(fin);

		//Construct BufferedReader from InputStreamReader
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = null;
		int p=0;
		while ((line = br.readLine()) != null){
			//System.out.println(line);
			StringTokenizer st=new StringTokenizer(line);
			int k=0;
			String starr[]=new String[2];
			while(st.hasMoreTokens())
			{
				starr[k++]=st.nextToken();
			}
			edgenames[p][0]=starr[0];
			edgenames[p][1]=starr[1];
			p++;
		}
		br.close();
	}
}