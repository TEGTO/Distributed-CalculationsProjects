package matrixMultiplying;
import mpi.MPI;

public class CannonMultiplyingMPI implements IMultiplyingMPI
{
    @Override
    public String toString()
    {
        return "\n=======Cannon=======";
    }
    @Override
    public int[][] multiply(int[][] A, int[][] B) throws Exception
    {
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        // Cannon's Algorithm
        int n = (int) Math.sqrt(size);
        int row = rank / n;
        int col = rank % n;
        int matrixSize = A.length;
        if (n * n != size || matrixSize % n != 0)
        {
            if (rank == 0)
                throw new Exception("Number of processes must be a perfect square.");
            else
                return null;
        }
        int subMatrixSize = A[0].length;
        int blockSize = matrixSize / n;
        int[][] C = new int[subMatrixSize][subMatrixSize];
        // Initial alignment
        ShiftLeft(A, row, subMatrixSize, matrixSize);
        ShiftUp(B, col, subMatrixSize, matrixSize);
        for (int k = 0; k < blockSize; k++)
        {
            // Multiply locally
            for (int i = 0; i < subMatrixSize; i++)
            {
                for (int j = 0; j < subMatrixSize; j++)
                {
                    for (int m = 0; m < subMatrixSize; m++)
                        C[i][j] += A[i][m] * B[m][j];
                }
            }
            // Rotate A left and B up
            ShiftLeft(A, 1, subMatrixSize, matrixSize);
            ShiftUp(B, 1, subMatrixSize, matrixSize);
        }
        return C;
    }
    public void ShiftLeft(int[][] matrix, int shift, int subMatrixSize, int matrixSize) throws Exception
    {
        int rank = MPI.COMM_WORLD.Rank();
        int row = rank / matrixSize;
        int prevCol = (rank - shift + matrixSize) % matrixSize;
        int nextCol = (rank + shift) % matrixSize;
        int sourceRank = row * matrixSize + prevCol;
        int destRank = row * matrixSize + nextCol;
        int[] buffer = new int[subMatrixSize * subMatrixSize];
        // Flatten the matrix to send
        for (int i = 0; i < subMatrixSize; i++)
        {
            for (int j = 0; j < subMatrixSize; j++)
                buffer[i * subMatrixSize + j] = matrix[i][j];
        }
        int[] recvBuffer = new int[subMatrixSize * subMatrixSize];
        MPI.COMM_WORLD.Sendrecv(buffer, 0, buffer.length, MPI.INT, destRank, 0, recvBuffer, 0, recvBuffer.length, MPI.INT, sourceRank, 0);

        // Unflatten received data into matrix
        for (int i = 0; i < subMatrixSize; i++)
        {
            for (int j = 0; j < subMatrixSize; j++)
                matrix[i][j] = recvBuffer[i * subMatrixSize + j];
        }
    }
    public void ShiftUp(int[][] matrix, int shift, int subMatrixSize, int matrixSize) throws Exception
    {
        int rank = MPI.COMM_WORLD.Rank();
        int col = rank % matrixSize;
        int prevRow = (rank - shift * matrixSize + matrixSize * matrixSize) % (matrixSize * matrixSize) / matrixSize;
        int nextRow = (rank + shift * matrixSize) % (matrixSize * matrixSize) / matrixSize;
        int sourceRank = prevRow * matrixSize + col;
        int destRank = nextRow * matrixSize + col;
        int[] buffer = new int[subMatrixSize * subMatrixSize];
        // Flatten the matrix to send
        for (int i = 0; i < subMatrixSize; i++)
        {
            for (int j = 0; j < subMatrixSize; j++)
                buffer[i * subMatrixSize + j] = matrix[i][j];
        }
        int[] recvBuffer = new int[subMatrixSize * subMatrixSize];
        MPI.COMM_WORLD.Sendrecv(buffer, 0, buffer.length, MPI.INT, destRank, 0, recvBuffer, 0, recvBuffer.length, MPI.INT, sourceRank, 0);

        // Unflatten received data into matrix
        for (int i = 0; i < subMatrixSize; i++)
        {
            for (int j = 0; j < subMatrixSize; j++)
                matrix[i][j] = recvBuffer[i * subMatrixSize + j];
        }
    }
}
