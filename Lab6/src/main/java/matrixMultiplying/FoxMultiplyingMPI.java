package matrixMultiplying;
import mpi.MPI;
import mpi.MPIException;

public class FoxMultiplyingMPI implements BaseMultiplyingMPI
{
    public int[][] multiply(int[][] A, int[][] B) throws Exception
    {
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int n = (int) Math.sqrt(size);
        int matrixSize = A.length;
        if (n * n != size || matrixSize % n != 0)
        {
            if (rank == 0)
                throw new Exception("Number of processes must be a perfect square.");
            else
                return null;
        }
        int[][] localC = new int[matrixSize][A[0].length];
        int i = rank / n;
        int j = rank % n;
        Object[] sendBuffer = new Object[1];
        Object[] recvBuffer = new Object[1];
        for (int l = 0; l < n - (matrixSize - 1); l++)
        {
            sendBuffer[0] = null;
            recvBuffer[0] = null;
            MPI.COMM_WORLD.Barrier();
            int root = i * n + (j + l) % n;
            //System.out.println("Iteration l = " + l + ", Rank " + rank + " computed root as " + root);
            for (int broadcaster = 0; broadcaster < size; broadcaster++)
            {
                if (rank == broadcaster)
                {
                    // This rank is the broadcaster
                    sendBuffer[0] = A;
                    for (int proc = 0; proc < size; proc++)
                    {
                        if (proc != broadcaster)
                            MPI.COMM_WORLD.Send(sendBuffer, 0, 1, MPI.OBJECT, proc, 99);
                    }
                }
                else
                {
                    MPI.COMM_WORLD.Recv(recvBuffer, 0, 1, MPI.OBJECT, broadcaster, 99);
                    if (broadcaster == root)
                        A = (int[][]) recvBuffer[0];  // Only update A if the broadcaster was the root
                }
            }
            matrixMultiply(A, B, localC, matrixSize);
            int leftNeighbor = i * n + (j - 1 + n) % n;
            int rightNeighbor = i * n + (j + 1) % n;
            sendBuffer[0] = B;
            MPI.COMM_WORLD.Sendrecv(sendBuffer, 0, 1, MPI.OBJECT, leftNeighbor, 0, recvBuffer, 0, 1, MPI.OBJECT, rightNeighbor, 0);
            B = (int[][]) recvBuffer[0];
        }
        return localC;
    }
    public void matrixMultiply(int[][] A, int[][] B, int[][] C, int blockSize)
    {
        for (int i = 0; i < blockSize; i++)
        {
            for (int j = 0; j < blockSize; j++)
            {
                for (int k = 0; k < blockSize; k++)
                    C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    @Override
    public String toString()
    {
        return "\n=======Fox=======";
    }
}