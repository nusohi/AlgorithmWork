/*
 * @Description: 循环赛日程表，n=2^k 个运动员
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-17 11:42:50
 * @LastEditTime: 2019-12-06 10:27:06
 */

package app;

public class Cycle {
    public static void main(String args[]) {
        int n = 9; // 运动员人数（2^k）
        int[][] table = cycle(n);
        PrintTable(table);
    }

    public static int[][] cycle(int n) {
        int[][] table;
        int delete = -1;
        // N 为奇数时计算 n = n + 1
        if (n % 2 != 0) {
            table = new int[n + 1][n + 1];
            delete = n + 1;
            // _cycle(table, n + 1);
        } else {
            table = new int[n][n];
            // _cycle(table, n);
        }
        __cycle(table, n);
        if (delete != -1) {
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[0].length; j++) {
                    if (table[i][j] == delete) {
                        table[i][j] = 0;
                    }
                }
                table[table.length - 1][i] = 0;
            }
        }
        return table;
    }

    public static void _cycle(int[][] table, int n) {
        int n_2 = n / 2;
        if (n_2 != 1 && n_2 % 2 != 0)
            n_2++;
        if (n == 1) {
            table[0][0] = 1;
            return;
        }

        _cycle(table, n_2);

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

    private static void __cycle(int[][] a, int n) {
        if (n == 1) {
            a[0][0] = 1;
            return;
        }
        if (odd(n)) {
            __cycle(a, n + 1);
            return;
        }
        __cycle(a, n / 2);
        Copy(a, n);
    }

    private static boolean odd(int n) {
        return n % 2 != 0;
    }

    private static void Copy(int[][] a, int n) // makecopy 与copy算法类似，但是要区分n/2为奇数或偶数的情形
    {
        if (n / 2 > 1 && odd(n / 2))
            copyodd(a, n);
        else
            copy(a, n);
    }

    private static void copy(int[][] a, int n) {
        int m = n / 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j + m] = a[i][j] + m;
                a[i + m][j] = a[i][j + m];
                a[i + m][j + m] = a[i][j];
            }
        }

    }

    private static void copyodd(int[][] a, int n) // 实现n/2为奇数时的复制
    {
        int[] b = new int[n];
        int m = n / 2;
        for (int i = 0; i < m; i++) {
            b[i] = m + i;
            b[m + i] = b[i];
        }
        for (int i = 0; i < m; i++) {
            // 1-3行复制到4-6行（左半）
            for (int j = 0; j <= m; j++) {
                if (a[i][j] > m) {
                    a[i][j] = b[i] + 1;
                    a[m + i][j] = (b[i] + 1 + m) % n;
                } else
                    a[m + i][j] = a[i][j] + m;
            }
            // 构造最后两列
            for (int j = 1; j < m; j++) {
                a[i][m + j] = b[i + j] + 1;
                a[b[i + j]][m + j] = i + 1;
            }
        }
    }
}