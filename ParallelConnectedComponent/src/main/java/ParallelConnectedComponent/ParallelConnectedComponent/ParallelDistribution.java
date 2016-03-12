package ParallelConnectedComponent.ParallelConnectedComponent;

public class ParallelDistribution extends Thread {
	private int[][] submatrix;
	private int vertices;
    private int low;
    private int high;
    private DisjointSet disjoint;

    public ParallelDistribution(int[][] submatrix, int low, int high,int vertices) {
        this.submatrix = submatrix;
        this.low = low;
        this.high = high;
        this.vertices=vertices;
    }
    public DisjointSet getDisjointSet()
    {
    	return disjoint;
    }
    public void run() {
    	
        disjoint=new DisjointSet();
        disjoint.n=vertices;
        disjoint.initialize();
        for(int i=low;i<=high;i++)
        {
        	for(int j=0;j<vertices;j++)
        	{
        		if(submatrix[i][j]==1)
        		{
        			int rooti=disjoint.root(i);
        			int rootj=disjoint.root(j);
        			if(rooti!=rootj)
        				disjoint.union(i,j);
        		}
        	}
        }
        
    }

}
