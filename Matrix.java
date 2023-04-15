public class Matrix {

    public static void divideMatrix(int[][] M1, int[][] M2, int[][] M3, int[][] M4, int[][] matrix) {
        // Matrix 행렬을 4개의 행렬로 나눠줌
        // if (M1.length != M2.length || M1[0].length != M3[0].length
        //         || M3.length != M4.length || M2[0].length != M4[0].length) {
        //     System.out.println("getCombinedMatrix failed");
        //     return;
        // }
        int n = matrix.length / 2;

        for (int i = 0; i < n; i++) { // Matrix를 4개의 행렬로 나눠 복사
            for (int j = 0; j < n; j++) {
                M1[i][j] = matrix[i][j];
                M2[i][j] = matrix[i][j+n];
                M3[i][j] = matrix[i+n][j];
                M4[i][j] = matrix[i+n][j+n];
            } 
        }
    }

    public static int[][] sumMatrix(int[][] A, int[][] B, int sign) {
        //A+B 행렬 반환, sign: B의 부호
        // if (A.length != B.length || A.length != A[0].length || A[0].length != B[0].length) {
        //     System.out.println("sumMatrix failed");
        //     return null;
        // }
        int lenI = A.length;
        int lenJ = A[0].length;
        int[][] C = new int[lenI][lenJ];
        for (int i = 0; i < lenI; i++) {
            for (int j = 0; j < lenJ; j++) {
                C[i][j] = A[i][j] + (sign * B[i][j]);
            }
        }
        return C;
    }

    public static void combineMatrix(int[][] C1, int[][] C2, int[][] C3, int[][] C4, int[][] result) {
        //분할된 4개의 행렬들을 순서대로 배치하여 하나의 결합된 행렬로 반환
        // if (C1.length != C2.length || C1[0].length != C3[0].length
        //         || C3.length != C4.length || C2[0].length != C4[0].length) {
        //     System.out.println("getCombinedMatrix failed");
        //     return;
        // }
        int n = C1.length;

        for (int i = 0; i < n; i++) { //4개의 행렬을 result 행렬에 동시에 복사
            for (int j = 0; j < n; j++) {
                result[i][j] = C1[i][j];
                result[i][j + n] = C2[i][j];
                result[i + n][j] = C3[i][j];
                result[i + n][j + n] = C4[i][j];
                //C1 C2
            }   //C3 C4
        }
    }

    public static int[][] naive(int[][] A, int[][] B, int size) {
        //행렬 곱 naive 방식
        int[][] C = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static int[][] strassen(int[][] A, int[][] B) {
        //슈트라센 방법에 의한 행렬 곱셈 결과 반환
        //A: n*n, B: n*n, n = 2^k 가정
        int size = A.length;
        if (size == 32) //임계값
            return naive(A, B, size);
        else {
            int n = size / 2; //4등분한 행렬의 가로, 세로 크기

            int[][] A11 = new int[n][n];
            int[][] A12 = new int[n][n];
            int[][] A21 = new int[n][n];
            int[][] A22 = new int[n][n];

            int[][] B11 = new int[n][n];
            int[][] B12 = new int[n][n];
            int[][] B21 = new int[n][n];
            int[][] B22 = new int[n][n];

            divideMatrix(A11, A12, A21, A22, A);
            divideMatrix(B11, B12, B21, B22, B);

            int[][] M1 = strassen(sumMatrix(A11, A22, 1), sumMatrix(B11, B22, 1));
            int[][] M2 = strassen(sumMatrix(A21, A22, 1), B11);
            int[][] M3 = strassen(A11, sumMatrix(B12, B22, -1));
            int[][] M4 = strassen(A22, sumMatrix(B21, B11, -1));
            int[][] M5 = strassen(sumMatrix(A11, A12, 1), B22);
            int[][] M6 = strassen(sumMatrix(A21, A11, -1), sumMatrix(B11, B12, 1));
            int[][] M7 = strassen(sumMatrix(A12, A22, -1), sumMatrix(B21, B22, 1));

            int[][] C1 = sumMatrix(sumMatrix(M1, M4, 1), sumMatrix(M7, M5, -1), 1);
            int[][] C2 = sumMatrix(M3, M5, 1);
            int[][] C3 = sumMatrix(M2, M4, 1);
            int[][] C4 = sumMatrix(sumMatrix(M1, M2, -1), sumMatrix(M3, M6, 1), 1);

            int[][] result = new int[size][size];
            combineMatrix(C1, C2, C3, C4, result);
            return result;
        }
    }
}


// int[][] M;

// public Matrix(int size) {
// M = new int[size][size];
// }

// public Matrix(int[][] M) {
// this.M = M;
// }

// public void setValue(int i, int j, int n) {
// M[i][j] = n;
// }

// public int getValue(int i, int j) {
// return M[i][j];
// }

// public static void view(int[][] A) {
// int n = A.length;
// for (int i = 0; i < n; i++) {
// for (int j = 0; j < n; j++) {
// System.out.print(A[i][j] + " ");
// }
// System.out.println();
// }
// }