package com.yangtian.grpcclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author Yang Tian
 * @date 30/03/2021 02:16
 */

@RestController
public class MatrixController {

    @PostMapping("/matrix")
    public String upload(@RequestParam(name ="a") MultipartFile matrixA, @RequestParam(name="b") MultipartFile matrixB, @RequestParam(name = "deadline", defaultValue = "5") int deadline) throws IOException {

        // check if empty
        if (matrixA.isEmpty() || matrixB.isEmpty()) {

            return "upload failed, do not upload empty file";
//            throw new MatrixException("upload failed, do not upload empty file");
        }

        // check if 2 power
        if (MatrixHelper.checkpower(matrixA) == false || MatrixHelper.checkpower(matrixB) == false) {

            return  "upload failed, please check the matrix dimension";
//            throw new MatrixException("upload failed, please check the matrix dimension");
        }

        try {
            // read files
            int[][] a = MatrixHelper.readStream(matrixA);
            int[][] b = MatrixHelper.readStream(matrixB);

            // calculate
            // use blockingstub
//            int[][] result = GrpcClient.multiplyMatrixBlock(a,b);

            // use asy stub
            int[][] result = GrpcClient.multiplyMatrixBlock(a,b,deadline);
            return  MatrixHelper.convertToString(result);

        } catch (IOException e) {

        }
        return "upload failed";
    }
}
