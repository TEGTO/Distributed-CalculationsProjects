package matrixMultiplying;// MatrixMultiplierMPI.java
import mpi.*;

public class TapeMultiplyingMPI implements IMultiplyingMPI
{
    public int[][] multiply(int[][] a, int[][] b) throws Exception
    {
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int rowsPerProcess = a.length / size;
        int remainder = a.length % size;
        int[] sendcounts = new int[size];
        int[] displs = new int[size];
        int count = 0;
        for (int i = 0; i < size; i++)
        {
            if (i < remainder)
                sendcounts[i] = rowsPerProcess + 1;
            else
                sendcounts[i] = rowsPerProcess;
            displs[i] = count;
            count += sendcounts[i];
        }
        int[][] localA = new int[sendcounts[rank]][a[0].length];
        if (rank == 0)
        {
            for (int i = 1; i < size; i++)
                MPI.COMM_WORLD.Send(a, displs[i], sendcounts[i], MPI.OBJECT, i, 0);
            System.arraycopy(a, 0, localA, 0, sendcounts[0]);
        }
        else
            MPI.COMM_WORLD.Recv(localA, 0, sendcounts[rank], MPI.OBJECT, 0, 0);
        // Matrix multiplication for the local segment
        int[][] localResult = new int[localA.length][b[0].length];
        for (int row = 0; row < localA.length; row++)
        {
            for (int col = 0; col < b[0].length; col++)
            {
                for (int k = 0; k < b.length; k++)
                    localResult[row][col] += localA[row][k] * b[k][col];
            }
        }
        int[][] res = null;
        if (rank == 0)
        {
            res = new int[a.length][b[0].length];
            System.arraycopy(localResult, 0, res, 0, sendcounts[0]);
            for (int i = 1; i < size; i++)
                MPI.COMM_WORLD.Recv(res, displs[i], sendcounts[i], MPI.OBJECT, i, 1);
        }
        else
            MPI.COMM_WORLD.Send(localResult, 0, localResult.length, MPI.OBJECT, 0, 1);
        return res;
    }
    @Override
    public String toString()
    {
        return "\n=======Tape=======";
    }
}

