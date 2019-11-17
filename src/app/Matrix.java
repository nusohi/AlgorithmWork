/*
 * @Description: 矩阵连乘算法
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-17 13:06:55
 * @LastEditTime: 2019-11-17 14:16:21
 */
package app;

public class Matrix {
    public static void main(String[] args) {
        int n = 6; // 矩阵个数
        int[] matrix = new int[] { 30, 35, 15, 5, 10, 20, 25 };
        int[][] res = new int[n][n];
        int[][] trace = new int[n][n];
        MatrixMultiply(matrix, res, trace);
        Show(matrix, res, trace);
    }

    public static void MatrixMultiply(int[] matrix, int[][] res, int[][] trace) {
        int len = res.length;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j <= i; j++)
                res[i][j] = 0;
        }

        for (int round = 1; round <= len; round++) {
            for (int i = 0, j = round; i < len - round; i++, j++) {
                // 计算 res[i][j] e.g. 0->2: 01 2 | 0 12
                res[i][j] = Integer.MAX_VALUE;
                for (int interval = i; interval < j; interval++) {
                    int times = matrix[i] * matrix[interval + 1] * matrix[j + 1];
                    times += res[i][interval] + res[interval + 1][j];
                    if (times < res[i][j]) {
                        res[i][j] = times;
                        trace[i][j] = interval;
                    }
                }
            }
        }
    }

    private static void Show(int[] matrix, int[][] res, int[][] trace) {
        PrintArr(res, "res");
        PrintArr(trace, "trace");
        PrintTrace(trace, 0, res.length - 1);
    }

    private static void PrintTrace(int[][] trace, int i, int j) {
        StringBuilder sb = new StringBuilder();
        Trace(trace, i, j, sb);
        System.out.println("== PrintTrace  ======================>");
        System.out.println(sb.toString());
    }

    private static void Trace(int[][] trace, int i, int j, StringBuilder sb) {
        if (i == j)
            return;
        Trace(trace, i, trace[i][j], sb);
        Trace(trace, trace[i][j] + 1, j, sb);
        String A, B;

        if (i == trace[i][j]) {
            A = "A[" + i + "]";
        } else {
            A = "A[" + i + "," + trace[i][j] + "]";
        }

        if (trace[i][j] + 1 == j) {
            B = "B[" + j + "]";
        } else {
            B = "B[" + (trace[i][j] + 1) + "," + j + "]";
        }

        String s = A + "\t*  " + B;
        sb.append(s + "\n");
    }

    private static void PrintArr(int[][] arr, String name) {
        StringBuilder s = new StringBuilder();
        for (int[] row : arr) {
            for (int item : row) {
                s.append(item);
                s.append('\t');
            }
            s.append("\n\n");
        }
        System.out.println("== PrintArr [" + name + "] =================>");
        System.out.println(s.toString());
    }
    
}