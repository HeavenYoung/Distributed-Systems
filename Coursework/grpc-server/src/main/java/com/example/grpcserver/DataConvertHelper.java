package com.example.grpcserver;
import  com.yangtian.matrix.*;
/**
 * @author Yang Tian
 * @date 10/02/2021 02:39
 */
public class DataConvertHelper {
     public static Matrix convertArrayToMatrix(int[][] array) {

          int MAX = array[0].length;
          Matrix.Row.Builder row = Matrix.Row.newBuilder();
          Matrix.Builder matrix = Matrix.newBuilder();

          for (int i = 0; i < MAX; i++) {
               for (int j = 0; j < MAX; j++) {

                    row.addElement(array[i][j]);
               }
               row.build();
               matrix.addRow(row);
               row.clearElement();
          }

          return matrix.build();
     }

     public static int[][] convertMatrixToArray(Matrix matrix) {

          int MAX = matrix.getRow(0).getElementCount();

          int[][] array = new int[MAX][MAX];

          for (int i = 0; i < MAX; i++) {

               Matrix.Row row = matrix.getRow(i);

               for (int j = 0; j < MAX; j++) {
                    array[i][j] = row.getElement(j);
               }
          }
          return array;
     }

}
