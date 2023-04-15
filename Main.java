import java.io.*;
import java.util.Date;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        final int SIZE = 1024;
        Date start, end; // �ð� ����
        long time; // �ð� ����
        int[][] A = new int[SIZE][SIZE];
        int[][] B = new int[SIZE][SIZE];
        int[][] C_naive = new int[SIZE][SIZE];
        int[][] C_strassen = new int[SIZE][SIZE];

        try { // a.txt, b.txt �б�
            FileInputStream finA = new FileInputStream("a.txt");
            FileInputStream finB = new FileInputStream("b.txt");

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    A[i][j] = finA.read();
                    B[i][j] = finB.read();
                }
            }
            finA.close();
            finB.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } 
        System.out.println("a.txt, b.txt �ε� ����\n��� ���� �غ�Ϸ�");

        start = new Date(); //naive ��� �ð� ����
        C_naive = Matrix.naive(A, B, SIZE);
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("naive ��� ����ð�: " + time + "ms");

        start = new Date(); //naive ��� �ð� ����
        C_strassen = Matrix.strassen(A, B);
        end = new Date();
        time = end.getTime() - start.getTime();
        System.out.println("strassen ��� ����ð�: " + time + "ms");

        System.out.print("(naive ��� ���) == (strassen ��� ���): ");
        System.out.println(Arrays.deepEquals(C_naive, C_strassen));
        //�� ��� ����� �������� Ȯ��

        try { // c.txt ��� ��� ���� ���
            FileOutputStream file = new FileOutputStream("c.txt");
            DataOutputStream c = new DataOutputStream(file);

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    c.writeInt(C_strassen[i][j]); 
                }
            }
            file.close();
            c.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }

        //���� �� Ȯ��
        int[][] savedC = new int[SIZE][SIZE];
        try {
            FileInputStream finM = new FileInputStream("c.txt");
            DataInputStream dinM = new DataInputStream(finM);
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    savedC[i][j] = dinM.readInt();
                }
            }
            finM.close();
            dinM.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        System.out.print("���� ���� ����: ");
        System.out.println(Arrays.deepEquals(C_naive, savedC));
    }
}
// int buffer;
// int i = 0, j = 0;
// while ((buffer = finA.read()) != -1) {
// A[i][j] = buffer;
// B[i][j] = finB.read();
// j++;
// if (j == 1024) {
// j = 0;
// i++;
// }
// }

//�������� �ϰ������ ��ü ����, toString ������ ���


// int[][] A = { { 1, 1, 1, 1 },
// { 2, 2, 2, 2 },
// { 3, 3, 3, 4 },
// { 4, 4, 4, 4 } };
// int[][] a1 = { { 1, 1 },
// { 2, 2 } };

// int[][] a2 = { { 1, 1 },
// { 2, 2 } };

// int[][] a3 = { { 3, 3 },
// { 4, 4 } };

// int[][] a4 = { { 3, 4 },
// {4,4}};

// int[][] B;
// B = Matrix.getCombinedMatrix(a1, a2, a3, a4);
// System.out.println(Arrays.deepEquals(A, B));