package pirateChest;

import java.util.Random;
import java.util.Scanner;

public class PirateChest {

	public static int MAXDIMENSION = 9;
	public static int MAXDEPTH = 9;
	public static int A;
	public static int B;
	public static int M;
	public static int N;
	public static int[][] pond;
	public static int test = 0;
	public static int bestVolume = 0;
	
	public static void main(String[] args) {
		
			
		if (test == 0)	// User inputs all the parameters of the problem
		{
			// Read in the variables. 
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please enter A:");
			A = scanner.nextInt();
			System.out.println("Please enter B:");
			B = scanner.nextInt();
			System.out.println("Please enter M:");
			M = scanner.nextInt();
			System.out.println("Please enter N:");
			N = scanner.nextInt();
			pond = new int[M][N];
			for (int i = 0; i< M; i++)
			{
				for (int j = 0; j<N;j++)
				{
					System.out.printf("Please enter depth for position (%d,%d)" ,i,j);
					pond[i][j] = scanner.nextInt();
				}
			}
		}
		else if(test ==1)	// Fixed test input with known answer
		{
			A = 3;
			B = 1;
			M = 2;
			N = 3;
			pond = new int [M][N];
			pond[0][0] = 2;
			pond[0][1] = 1;
			pond[0][2] = 1;
			pond[1][0] = 2;
			pond[1][1] = 2;
			pond[1][2] = 1;
		
		}
		else if(test ==2)	// Random data only going up to 10
		{
			A = B = MAXDIMENSION +1;
			Random rnd = new Random();
			M = rnd.nextInt(MAXDIMENSION) + 1;
			N = rnd.nextInt(MAXDIMENSION) + 1;
			while(A > M || B > N || (A == M && B == N))
			{
				A = rnd.nextInt(MAXDIMENSION) +1;
				B = rnd.nextInt(MAXDIMENSION) +1;
			}
			pond = new int[M][N];
			for (int i = 0; i<M; i++)
			{
				for (int j = 0; j<N; j++)
				{
					pond[i][j] = rnd.nextInt(MAXDEPTH) +1;
				}
			}
		}
		
		displayInputs();
		bestVolume = calculateMaxVolume();
		System.out.printf("\nThe maximum volume chest is %d", bestVolume);
		
	}
	
	public static void displayInputs()
	{
		// Display inputs
		System.out.printf("A,B,M,N are %d,%d,%d,%d\n\n",A,B,M,N);
		// Display bottom of the pond
		System.out.println("Here is your pond depth chart");
		for (int i = 0; i<M;i++)
		{
			for (int j=0;j<N;j++)
			{
				System.out.print(pond[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public static int calculateMaxVolume()
	{
		
		int maxDepth = 0;
		int tempVolume = 0;
		for(int i=1; i<=A;i++)
		{
			for(int j=1; j<=B; j++)
			{
				maxDepth = findMaxDepth(i,j);
				tempVolume = adjustForDisplacement(maxDepth,i,j);
				if (tempVolume > bestVolume)
					bestVolume = tempVolume;
			}
		}
		
		return bestVolume;
	}
	
	//	Given a chest of dimensions a,b, search the entire pond and find the deepest point 
	// 	it can be placed
	public static int findMaxDepth(int a, int b)
	{
		int depth = 0;	// begin assuming max depth = 0
		int temp = 0;
		int m = (M-b+1);
		if (m < 1)
			m=1;
		int n = (N-a+1);
		if(n<1)
			n=1;
		for(int i = 0; i<m; i++)
		{
			for (int j = 0; j< n; j++)
			{
				temp = pond[i][j]; // set temp to the maximum, and see if there are smaller in grid
				for (int k = i; k<(i+b); k++)
				{
					for(int l = j; l<(j+a); l++)
					{
						if(pond[k][l] < temp)
							temp = pond[k][l];
					}
				}
				if(temp > depth)
					depth = temp;	// if we have a larger depth, then upgrade the Max.
			}
		}
		
		return depth;
	}
	
	//	Given a deepest point, calculate the amount of displaced water and adjust the volume 
	//	of the chest to reflect this additional volume.
	public static int adjustForDisplacement(int depth,int a, int b)
	{
		int volume = depth*a*b;	// This is the volume without considering displacement
		int extra = (volume - 1)/((M*N) - (a*b));
		volume = (depth + extra)*a*b;
		return volume;
	}
}
