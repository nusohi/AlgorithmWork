/*
 * @Description: 活动安排 贪心算法
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-24 13:53:10
 * @LastEditTime: 2019-12-06 10:08:30
 */
package app;

class Activity {
    public static void main(String[] args) {
        int[] start = new int[] { 1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12 };
        int[] finish = new int[] { 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 }; // len=11 (0-10)
        // start = new int[] { 5, 2, 9, 7, 4 };
        // finish = new int[] { 6, 9, 15, 10, 5 };
        boolean[] X = new boolean[start.length];

        Sort(start, finish);
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
        StringBuilder array = new StringBuilder();
        for (int i : start) {
            array.append(i + "\t");
        }
        array.append("\n");
        for (int i : finish) {
            array.append(i + "\t");
        }

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
        System.out.println(array.toString());
        System.out.println(s.toString());
    }

    private static void Sort(int[] start, int[] finish) {
        for (int j = finish.length - 1; j > 0; j--) {
            for (int i = 0; i < j; i++) {
                if (finish[i] > finish[i + 1]) {
                    int temp = finish[i];
                    finish[i] = finish[i + 1];
                    finish[i + 1] = temp;
                    temp = start[i];
                    start[i] = start[i + 1];
                    start[i + 1] = temp;
                }
            }
        }
    }
}