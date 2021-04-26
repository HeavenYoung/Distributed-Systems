package com.yangtian.grpcclient;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yang Tian
 * @date 30/03/2021 02:20
 */
public class MatrixHelper {

    static int[][] matrix;
    public static int[][] readStream(MultipartFile file) throws IOException {

        int row = 0;
        int size = 0;
        InputStream is = file.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try{
            BufferedReader br = new BufferedReader(isReader);
            String result = null;

            while((result = br.readLine())!=null){
                String[] stringArray = result.trim().split("\\s+");
                size = stringArray.length;
                if (matrix == null) {
                    matrix = new int[size][size];
                }
                for (int i = 0; i < size; i++) {
                    matrix[row][i] = Integer.parseInt(stringArray[i]);
                }
                row++;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        int[][] mArray = matrix;
        matrix = null;
        return mArray;
    }

    private static int[] stringToArray(String line) {

        String[] result = line.split(" ");
        int len = result.length;
        int[] mArray = new int[len];
        for (int i = 0; i < len; i++) {
            String str = result[i];
            mArray[i] =Integer.parseInt(str);
        }
        return mArray;
    }

    public static String convertToString(int[][] array){

        int len = array[0].length;
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < len; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < len; j++) {
                if (j!=len-1) {
                    row.append(array[i][j]+" ");
                } else {
                    row.append(array[i][j]);
                }
                row.toString();
            }
            result.append(row+System.lineSeparator());
        }

        return result.toString();
    }

    public static boolean is2Power(int n) {
        return (int) (Math.ceil((Math.log(n)/Math.log(2)))) == (int) (Math.floor(((Math.log(n)/Math.log(2)))));
    }

    public static Boolean checkpower(MultipartFile file) throws IOException {
        int row = 0;
        int size = 0;
        InputStream is = file.getInputStream();
        InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        try{
            BufferedReader br = new BufferedReader(isReader);
            String result = null;
            while((result = br.readLine())!=null){
                String[] stringArray = result.trim().split("\\s+");
                size = stringArray.length;
                if (is2Power(size) == false) {
                    return false;
                }
                row++;
            }
            if (size!= row) {
                return false;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<int[][]> splitMatrix(int[][]matrix) {

        int bSize = 4;
        if (matrix[0].length == 2) {
            bSize = 2;
        }

        int col = matrix[0].length/bSize;
        int row = matrix[0].length/bSize;

        int size = (col/bSize)* (row/bSize);

        ArrayList<int[][]> matrixList = new ArrayList<int[][]>(size);

        for (int i = 0; i < row; i++) {
            int[][] smallMatrix = new int[bSize][bSize];
            int[] rowArray = new int[bSize];
            for (int j = 0; j < col; j++) {
                for (int k = i*bSize; k < i*bSize +bSize; k++) {
                    for (int l = j*bSize; l < j*bSize+bSize; l++) {
                        rowArray[j] = matrix[k][l];
                    }
                }
            }
            smallMatrix[i] = rowArray;
        }

        return matrixList;
    }
}
