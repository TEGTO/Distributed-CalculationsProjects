import matrixMultiplying.*;
import mpi.*;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        int[][] matrixA1 = {{1, 2}, {3, 4}};
        int[][] matrixB1 = {{2, 0}, {1, 3}};
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        // Sample matrix multiplication
        if (rank == 0)
            System.out.println("----Matrix multiplication example 1----");
        multiplyMatrix(matrixA1, matrixB1, new TapeMultiplyingMPI());
        multiplyMatrix(matrixA1, matrixB1, new FoxMultiplyingMPI()); //Works only on 4 processes
        multiplyMatrix(matrixA1, matrixB1, new CannonMultiplyingMPI()); //Works only on 4 processes
        if (rank == 0)
            multiplyMatrix(matrixA1, matrixB1, new SimpleMultiplication());
        if (rank == 0)
            System.out.println("----Matrix multiplication example 2----");
        int[][] matrixA2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrixB2 = {{9, 8, 7}, {6, 5, 4}, {3, 2, 1}};
        multiplyMatrix(matrixA2, matrixB2, new TapeMultiplyingMPI());
        multiplyMatrix(matrixA2, matrixB2, new FoxMultiplyingMPI()); //Works only on 9 processes
        multiplyMatrix(matrixA2, matrixB2, new CannonMultiplyingMPI()); //Works only on 9 processes
        if (rank == 0)
            multiplyMatrix(matrixA2, matrixB2, new SimpleMultiplication());
        MPI.Finalize();
    }
    public static void multiplyMatrix(int[][] matrixA, int[][] matrixB, IMultiplyingMPI multiplyingMPI)
    {
        int rank = MPI.COMM_WORLD.Rank();
        try
        {
            int[][] mat = multiplyingMPI.multiply(matrixA, matrixB);
            if (rank == 0)
            {
                System.out.println(multiplyingMPI);
                printMatrix(mat);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public static void printMatrix(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[0].length; j++)
            {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}