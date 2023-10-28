package matrixMultiplying;
import com.sun.tools.javac.Main;
import mpi.MPI;
import mpi.MPIException;
import mpi.ProdInt;

public class CannonMultiplyingMPI implements BaseMultiplyingMPI
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
        int rootOfUniverse = (int) Math.sqrt(size);
        int row = rank / rootOfUniverse;
        int col = rank % rootOfUniverse;
        int matrixSize = A.length;
        int subMatrixSize = A[0].length;
        int blockSize = matrixSize / rootOfUniverse;
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
