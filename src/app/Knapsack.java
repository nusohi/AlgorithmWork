/*
 * @Description: 0-1背包问题
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-23 20:57:34
 * @LastEditTime: 2019-11-23 22:17:03
 */
package app;

public class Knapsack {
    public static void main(String[] args) {
        int len = 5;
        int capacity = 15;
        int[] value = new int[] { 10, 40, 20, 30, 50 };
        int[] weight = new int[] { 15, 4, 4, 15, 3 };
        int[][] dp = new int[len][capacity + 1];
        boolean[] x = new boolean[len];

        // 算法计算，输出 dp 数组
        Solution(value, weight, dp);
        System.out.println("最大价值：" + dp[len - 1][capacity]);
        PrintArr(dp, "dp");

        // Traceback 输出
        Traceback(dp, weight, capacity, x);
        System.out.println("========== [Traceback] =================>");
        for (boolean keep : x) {
            System.out.print(keep + "\t");
        }
        System.out.print("\n\n");

        // Value 和 Weight 输出
        PrintArr(new int[][] { value, weight }, "value and weight");
    }

    public static void Solution(int[] value, int[] weight, int[][] dp) {
        int len = value.length; // 物品数量
        int cap = dp[0].length - 1; // 背包容量

        // 初始化第 0 行
        for (int j = 0; j < weight[0]; j++)
            dp[0][j] = 0;
        for (int j = weight[0]; j <= cap; j++)
            dp[0][j] = value[0];

        // 自底向上填表(1->len)
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < weight[i]; j++) {
                dp[i][j] = dp[i - 1][j];
            }
            for (int j = weight[i]; j <= cap; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
            }
        }
    }

    private static void Traceback(int[][] dp, int[] weight, int cap, boolean[] x) {
        int len = x.length;
        for (int i = len - 1; i > 0; i--) {
            if (dp[i][cap] == dp[i - 1][cap]) {
                x[i] = false;
            } else {
                x[i] = true;
                cap -= weight[i];
            }
        }
        x[0] = (dp[0][cap] > 0) ? true : false;
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