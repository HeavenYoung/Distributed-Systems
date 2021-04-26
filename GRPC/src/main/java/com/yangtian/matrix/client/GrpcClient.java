package com.yangtian.matrix.client;

import com.yangtian.matrix.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Yang Tian
 * @date 2021/2/8 17:50
 */
public class GrpcClient {

    public static int MAX = 4;

    public static void main(String[] args) {

        int[][] A = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        int[][] B = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        multiplyMatrixBlock(A,B);
    }

    // multiplyBlock
    private static int[][] multiplyCalculate(int[][] a, int[][] b) {

        // convert data
        Matrix matrixA = DataConvertHelper.convertArrayToMatrix(a);
        Matrix matrixB = DataConvertHelper.convertArrayToMatrix(b);

        // create stub and calculate
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
        MatrixResponse multiplyResponse = stub.multiplyBlock(MatrixRequest.newBuilder().setA(matrixA).setB(matrixB).build());

        // convert data
        int[][] c = DataConvertHelper.convertMatrixToArray(multiplyResponse.getC());

        // close connection
        channel.shutdown();

        return c;
    }

    // addBlock
    private static int[][] addCalculate(int[][] a, int[][] b) {

        // convert data
        Matrix matrixA = DataConvertHelper.convertArrayToMatrix(a);
        Matrix matrixB = DataConvertHelper.convertArrayToMatrix(b);

        // create stub and calculate
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
        MatrixResponse addResponse = stub.addBlock(MatrixRequest.newBuilder().setA(matrixA).setB(matrixB).build());

        // convert data
        int[][] c = DataConvertHelper.convertMatrixToArray(addResponse.getC());

        // close connection
        channel.shutdown();

        return c;
    }

    public static int[][] multiplyMatrixBlock(int A[][], int B[][]) {
        int bSize = 2;
        int[][] A1 = new int[MAX][MAX];
        int[][] A2 = new int[MAX][MAX];
        int[][] A3 = new int[MAX][MAX];
        int[][] B1 = new int[MAX][MAX];
        int[][] B2 = new int[MAX][MAX];
        int[][] B3 = new int[MAX][MAX];
        int[][] C1 = new int[MAX][MAX];
        int[][] C2 = new int[MAX][MAX];
        int[][] C3 = new int[MAX][MAX];
        int[][] D1 = new int[MAX][MAX];
        int[][] D2 = new int[MAX][MAX];
        int[][] D3 = new int[MAX][MAX];
        int[][] res = new int[MAX][MAX];
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                A1[i][j] = A[i][j];
                A2[i][j] = B[i][j];
            }
        }
        for (int i = 0; i < bSize; i++) {
            for (int j = bSize; j < MAX; j++) {
                B1[i][j - bSize] = A[i][j];
                B2[i][j - bSize] = B[i][j];
            }
        }
        for (int i = bSize; i < MAX; i++) {
            for (int j = 0; j < bSize; j++) {
                C1[i - bSize][j] = A[i][j];
                C2[i - bSize][j] = B[i][j];
            }
        }
        for (int i = bSize; i < MAX; i++) {
            for (int j = bSize; j < MAX; j++) {
                D1[i - bSize][j - bSize] = A[i][j];
                D2[i - bSize][j - bSize] = B[i][j];
            }
        }
        A3 = addCalculate(multiplyCalculate(A1, A2), multiplyCalculate(B1, C2));
        B3 = addCalculate(multiplyCalculate(A1, B2), multiplyCalculate(B1, D2));
        C3 = addCalculate(multiplyCalculate(C1, A2), multiplyCalculate(D1, C2));
        D3 = addCalculate(multiplyCalculate(C1, B2), multiplyCalculate(D1, D2));
        for (int i = 0; i < bSize; i++) {
            for (int j = 0; j < bSize; j++) {
                res[i][j] = A3[i][j];
            }
        }
        for (int i = 0; i < bSize; i++) {
            for (int j = bSize; j < MAX; j++) {
                res[i][j] = B3[i][j - bSize];
            }
        }
        for (int i = bSize; i < MAX; i++) {
            for (int j = 0; j < bSize; j++) {
                res[i][j] = C3[i - bSize][j];
            }
        }
        for (int i = bSize; i < MAX; i++) {
            for (int j = bSize; j < MAX; j++) {
                res[i][j] = D3[i - bSize][j - bSize];
            }
        }
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println("");
        }
        return res;
    }
}
