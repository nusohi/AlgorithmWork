/*
 * @Description: 循环赛日程表，n=2^k 个运动员
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-17 11:42:50
 * @LastEditTime: 2019-11-17 12:19:49
 */

package app;

public class Cycle {
    public static void main(String args[]) {
        int n = 8;  // 运动员人数（2^k）
        int[][] table = new int[n][n];
        cycle(table, n);
        PrintTable(table);
    }

    public static void cycle(int[][] table, int n) {
        if (n == 1) {
            table[0][0] = 1;
            return;
        } else {
            cycle(table, n / 2);
        }

        int n_2 = n / 2;

        // 左上角复制到右下
        for (int i = n_2; i < n; i++) {
            for (int j = n_2; j < n; j++) {
                table[i][j] = table[i - n_2][j - n_2];
            }
        }
        // 左上角复制到左下 并加上 n/2
        for (int i = n_2; i < n; i++) {
            for (int j = 0; j < n_2; j++) {
                table[i][j] = table[i - n_2][j] + n_2;
            }
        }
        // 左下角复制到右上
        for (int i = 0; i < n_2; i++) {
            for (int j = n_2; j < n; j++) {
                table[i][j] = table[i + n_2][j - n_2];
            }
        }
    }

    private static void PrintTable(int[][] table) {
        // 表头
        StringBuilder head = new StringBuilder();
        head.append("人\\天\t");
        for (int i = 1; i < table.length; i++) {
            head.append(i);
            head.append("\t");
        }
        head.append("\n");

        // 表内容
        StringBuilder content = new StringBuilder();
        for (int[] row : table) {
            for (int id : row) {
                content.append(id);
                content.append('\t');
            }
            content.append("\n\n");
        }
        
        System.out.println("== 循环赛日程表 =================>");
        System.out.println(head.toString());
        System.out.println(content.toString());
    }

}