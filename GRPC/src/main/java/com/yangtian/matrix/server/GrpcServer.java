package com.yangtian.matrix.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @author Yang Tian
 * @date 08/02/2021 17:30
 */
public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Multiply Server
        Server server = ServerBuilder.forPort(8080).addService(new MatrixServiceImpl()).build();
        System.out.println("Starting  Server..");
        server.start();
        System.out.println(" Server Started!");
        server.awaitTermination();

//        // addBlock Server
//        Server server2 = ServerBuilder.forPort(8081).addService(new MatrixServiceImpl()).build();
//        System.out.println("Starting Addition Server..");
//        server2.start();
//        System.out.println("Addition Server Started!");
//        server2.awaitTermination();
    }
}
