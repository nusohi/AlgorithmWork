/*
 * @Description: 活动安排 贪心算法
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-24 13:53:10
 * @LastEditTime: 2019-11-24 14:14:25
 */
package app;

class Activity {
    public static void main(String[] args) {
        int[] start = new int[] { 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
        int[] finish = new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }; // len=11 (0-10)
        boolean[] X = new boolean[start.length];

        Arrange(start, finish, X);
        Show(start, finish, X);
    }

    public static void Arrange(int[] start, int[] finish, boolean[] X) {
        int last = 0;
        for (int i = 0, len = finish.length; i < len; i++) {
            if (start[i] >= last) {
                X[i] = true;
                last = finish[i];
            } else {
                X[i] = false;
            }
        }
    }

    private static void Show(int[] start, int[] finish, boolean[] X) {
        int last = 0;
        StringBuilder s = new StringBuilder();
        for (int i = 0, len = finish.length; i < len; i++) {
            if (!X[i])
                continue;
            for (; last < start[i]; last++) {
                s.append("- ");
            }
            for (; last < finish[i]; last++) {
                s.append(i + " ");
            }
        }
        System.out.println(s.toString());
    }

}