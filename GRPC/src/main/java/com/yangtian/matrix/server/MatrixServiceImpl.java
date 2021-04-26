package com.yangtian.matrix.server;

import com.yangtian.matrix.*;
import com.yangtian.matrix.MatrixServiceGrpc.MatrixServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * @author Yang Tian
 * @date 08/02/2021 17:31
 */
public class MatrixServiceImpl extends MatrixServiceImplBase {
    @Override
    public void multiplyBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {

        int[][] a = DataConvertHelper.convertMatrixToArray(request.getA());
        int[][] b = DataConvertHelper.convertMatrixToArray(request.getB());

        int[][] result = multiplyBlock_function(a,b);

        Matrix c = DataConvertHelper.convertArrayToMatrix(result);

        MatrixResponse response = MatrixResponse.newBuilder().setC(c).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private int[][] multiplyBlock_function(int[][] a, int[][] b) {
        return BlockMulti.multiplyBlock(a, b);
    }

    @Override
    public void addBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {

        int[][] a = DataConvertHelper.convertMatrixToArray(request.getA());
        int[][] b = DataConvertHelper.convertMatrixToArray(request.getB());

        int[][] result = addBlock_function(a,b);

        Matrix c = DataConvertHelper.convertArrayToMatrix(result);

        MatrixResponse response = MatrixResponse.newBuilder().setC(c).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private int[][] addBlock_function(int[][] a, int[][] b){
        return BlockMulti.addBlock(a,b);
    }
}
