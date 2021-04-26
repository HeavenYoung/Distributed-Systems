package com.example.grpcserver;
import com.yangtian.matrix.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * @author Yang Tian
 * @date 30/03/2021 04:03
 */
@GrpcService
public class GrpcServerImpl extends MatrixServiceGrpc.MatrixServiceImplBase {

    @Override
    public void multiplyBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {

        MatrixResponse response = generateMultiplyResponse(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private int[][] multiplyBlock_function(int[][] a, int[][] b) {
        return BlockMulti.multiplyBlock(a, b);
    }

    @Override
    public void addBlock(MatrixRequest request, StreamObserver<MatrixResponse> responseObserver) {

        MatrixResponse response = generateAddResponse(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private int[][] addBlock_function(int[][] a, int[][] b){
        return BlockMulti.addBlock(a,b);
    }

    private MatrixResponse generateAddResponse (MatrixRequest request) {

        int[][] a = DataConvertHelper.convertMatrixToArray(request.getA());
        int[][] b = DataConvertHelper.convertMatrixToArray(request.getB());

        int[][] result = addBlock_function(a,b);

        Matrix c = DataConvertHelper.convertArrayToMatrix(result);

        MatrixResponse response = MatrixResponse.newBuilder().setC(c).build();

        return response;
    }

    private MatrixResponse generateMultiplyResponse (MatrixRequest request) {

        int[][] a = DataConvertHelper.convertMatrixToArray(request.getA());
        int[][] b = DataConvertHelper.convertMatrixToArray(request.getB());

        int[][] result = multiplyBlock_function(a,b);

        Matrix c = DataConvertHelper.convertArrayToMatrix(result);

        MatrixResponse response = MatrixResponse.newBuilder().setC(c).build();

        return response;
    }

    @Override
    public StreamObserver<MatrixRequest> multiplyStreamBlock(StreamObserver<MatrixResponse> responseObserver) {
        return new StreamObserver<MatrixRequest>() {
            @Override
            public void onNext(MatrixRequest matrixRequest) {

                MatrixResponse response = generateMultiplyResponse(matrixRequest);
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<MatrixRequest> addStreamBlock(StreamObserver<MatrixResponse> responseObserver) {
        return new StreamObserver<MatrixRequest>() {
            @Override
            public void onNext(MatrixRequest matrixRequest) {
                MatrixResponse response = generateAddResponse(matrixRequest);
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
