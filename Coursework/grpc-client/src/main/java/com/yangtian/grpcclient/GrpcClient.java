package com.yangtian.grpcclient;

import com.yangtian.matrix.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author Yang Tian
 * @date 30/03/2021 03:01
 */
@Service
public class GrpcClient {

    // multiplyBlock
    private static int[][] multiplyCalculate(int[][] a, int[][] b) {

        // convert data
        Matrix matrixA = DataConvertHelper.convertArrayToMatrix(a);
        Matrix matrixB = DataConvertHelper.convertArrayToMatrix(b);

        // create stub and calculate
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
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
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
        MatrixResponse addResponse = stub.addBlock(MatrixRequest.newBuilder().setA(matrixA).setB(matrixB).build());

        // convert data
        int[][] c = DataConvertHelper.convertMatrixToArray(addResponse.getC());

        // close connection
        channel.shutdown();

        return c;
    }

    public static int[][] multiplyMatrixBlock(int A[][], int B[][]) {

        int MAX = A.length;
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


    public static int calculateServerNumbers(double footprint,int multiplyBlockCalls ,int deadline) {

        double numberOfServers = (footprint * multiplyBlockCalls)/deadline;

        int serversNumber = (int) Math.ceil(numberOfServers);

        if (serversNumber > 8) {
            return 8;
        }

        return serversNumber;
    }

    // get footprint value
    public static double calculateFootprint()
    {
        int A[][] = { {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};
        int B[][] = { {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}};

        long startTime = System.nanoTime();

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        MatrixServiceGrpc.MatrixServiceBlockingStub stub = MatrixServiceGrpc.newBlockingStub(channel);
        Matrix matrixA = DataConvertHelper.convertArrayToMatrix(A);
        Matrix matrixB = DataConvertHelper.convertArrayToMatrix(B);
        stub.multiplyBlock(MatrixRequest.newBuilder().setA(matrixA).setB(matrixB).build());

        long endTime = System.nanoTime();
        long footprint = endTime - startTime;

        return (double) footprint / 1_000_000_000;
    }

    public static MatrixRequest generateRequestWith2DArray(int[][]a, int[][]b) {

        Matrix matrixA = DataConvertHelper.convertArrayToMatrix(a);
        Matrix matrixB = DataConvertHelper.convertArrayToMatrix(b);
        return  MatrixRequest.newBuilder().setA(matrixA).setB(matrixB).build();
    }

    public static MatrixRequest fetchRequestWithIndex(int[][] matrixA, int[][] matrixB, int bSize, int reqNum)
    {
        int size = matrixA.length / bSize;
        int rowA = reqNum / (size * size);
        int rowB = (reqNum % (size * size)) / size;
        int colB = (reqNum % (size * size)) % size;
        int[][] a = new int[bSize][bSize];
        int[][] b = new int[bSize][bSize];
        for (int ki = 0; ki < bSize; ki++)
        {
            for (int kj = 0; kj < bSize; kj++)
            {
                a[ki][kj] = matrixA[rowA * bSize + ki][rowB * bSize + kj];
                b[ki][kj] = matrixB[rowB * bSize + ki][colB * bSize + kj];
            }
        }
        return generateRequestWith2DArray(a, b);
    }

    public static int[][] multiplyMatrixBlock(int A[][], int B[][], int deadline) {

        System.out.println("matrix power is --------" + A[0].length);

        int bSize = 2;
        int newSize = A.length / bSize;

        System.out.println("deadline value is --------" + deadline);

        double footprint = calculateFootprint();

        System.out.println("footPrint value is -------" + footprint);

        int multiplyBlockCalls = (newSize) * (newSize) * (newSize);

        int serversNumber = calculateServerNumbers(footprint,multiplyBlockCalls,deadline);

        System.out.println("number of servers is --------" + serversNumber);

        int perServerCalls = multiplyBlockCalls / serversNumber;
        System.out.println("per server calls is --------" + perServerCalls);

        ArrayList<StreamObserver<MatrixRequest>> requestList = new ArrayList<StreamObserver<MatrixRequest>>();
        ArrayList<int[][]> resultMatrixArrayList = new ArrayList<int[][]>(multiplyBlockCalls);
        ArrayList<CountDownLatch> latchList = new ArrayList<CountDownLatch>();

        int[] requestNum = new int[serversNumber];
        int[] responseNum = new int[serversNumber];

        ManagedChannel channel1 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel3 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel4 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel5 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel6 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel7 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        ManagedChannel channel8 = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();

        MatrixServiceGrpc.MatrixServiceStub asyncStub1  = MatrixServiceGrpc.newStub(channel1);
        MatrixServiceGrpc.MatrixServiceStub asyncStub2  = MatrixServiceGrpc.newStub(channel2);
        MatrixServiceGrpc.MatrixServiceStub asyncStub3  = MatrixServiceGrpc.newStub(channel3);
        MatrixServiceGrpc.MatrixServiceStub asyncStub4  = MatrixServiceGrpc.newStub(channel4);
        MatrixServiceGrpc.MatrixServiceStub asyncStub5  = MatrixServiceGrpc.newStub(channel5);
        MatrixServiceGrpc.MatrixServiceStub asyncStub6  = MatrixServiceGrpc.newStub(channel6);
        MatrixServiceGrpc.MatrixServiceStub asyncStub7  = MatrixServiceGrpc.newStub(channel7);
        MatrixServiceGrpc.MatrixServiceStub asyncStub8  = MatrixServiceGrpc.newStub(channel8);

        ArrayList<MatrixServiceGrpc.MatrixServiceStub> asyncStubList = new ArrayList<MatrixServiceGrpc.MatrixServiceStub>(serversNumber);

        asyncStubList.add(asyncStub1);
        asyncStubList.add(asyncStub2);
        asyncStubList.add(asyncStub3);
        asyncStubList.add(asyncStub4);
        asyncStubList.add(asyncStub5);
        asyncStubList.add(asyncStub6);
        asyncStubList.add(asyncStub7);
        asyncStubList.add(asyncStub8);

        for (int i = 0; i < serversNumber; i++) {

            latchList.add(new CountDownLatch(1));
            int currentServer = i;

            StreamObserver<MatrixRequest> request =  asyncStubList.get(i).multiplyStreamBlock(new StreamObserver<MatrixResponse>() {
                @Override
                public void onNext(MatrixResponse matrixResponse) {

//                    int[][] t = DataConvertHelper.convertMatrixToArray(matrixResponse.getC());
//                    System.out.println(MatrixHelper.convertToString(t));

                    resultMatrixArrayList.set(currentServer * perServerCalls + (responseNum[currentServer]++),DataConvertHelper.convertMatrixToArray(matrixResponse.getC()));

                    if (requestNum[currentServer] < perServerCalls) {

                        requestList.get(currentServer).onNext(fetchRequestWithIndex(A, B, bSize, currentServer * perServerCalls + (requestNum[currentServer]++)));
                    }
                    else{
                        requestList.get(currentServer).onCompleted();
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    latchList.get(currentServer).countDown();
                }

                @Override
                public void onCompleted() {
                    latchList.get(currentServer).countDown();
                }
            });

            requestList.add(request);

            requestList.get(i).onNext(fetchRequestWithIndex(A,B, bSize ,i * perServerCalls + (requestNum[i]++)));
        }

        for (int j = 0; j < serversNumber; j++) {
            try {
                latchList.get(j).await(1, TimeUnit.MINUTES);
            } catch (Exception e) {

            }
        }

        // add
        ArrayList<CountDownLatch> addLatchList = new ArrayList<CountDownLatch>();

        int addServersNumber = calculateServerNumbers(footprint,newSize*newSize,deadline);
        for (int i = 0; i < addServersNumber; i++) {

            int finalI = i;

            addLatchList.add(new CountDownLatch(1));

            asyncStubList.get(i).addStreamBlock(new StreamObserver<MatrixResponse>() {
                @Override
                public void onNext(MatrixResponse matrixResponse) {

                    int[][] t = DataConvertHelper.convertMatrixToArray(matrixResponse.getC());
                    System.out.println(MatrixHelper.convertToString(t));
                }

                @Override
                public void onError(Throwable throwable) {
                    addLatchList.get(finalI).countDown();
                }

                @Override
                public void onCompleted() {
                    addLatchList.get(finalI).countDown();
                }
            });
        }

        for (int j = 0; j < addServersNumber; j++) {
            try {
                latchList.get(j).await(1, TimeUnit.MINUTES);
            } catch (Exception e) {

            }
        }


        int[][] mMatrix;
        int[][] temResponse;

        ArrayList<int[][]> addMatrix = new ArrayList<int[][]>(newSize);

        for (int k = 0; k < addMatrix.toArray().length; k++) {

            mMatrix = new int[A.length][B.length];

            for (int m = 0; m < mMatrix.length; m++)  {

                for (int n = 0; n < newSize; n++) {

                    temResponse = resultMatrixArrayList.get(n * (newSize * newSize) + k * newSize + n);

                    for (int p = 0; p < bSize; p++)  {

                        for (int q = 0; q < bSize; q++)  {
                            mMatrix[n * bSize + p][n * bSize + q] = temResponse[p][q];
                        }
                    }
                }
            }
            addMatrix.set(k,mMatrix);
        }

        int[][] resultMatrix = new int[A.length][A.length];
        for (int r = 0; r < resultMatrix.length; r++) {
            for (int c = 0; c < resultMatrix[r].length; c++) {
                for (int[][] matrix : addMatrix)
                    resultMatrix[r][c] += matrix[r][c];
            }
        }
        return resultMatrix;
    }

}
