/*
 * @Description: 流水线调度算法
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-22 10:25:38
 * @LastEditTime: 2019-11-23 18:29:58
 */
package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Pipeline {
    public static void main(String[] args) {
        int[] _ = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // index 参照
        
        int[] a = new int[] { 3, 3, 3, 4, 5, 6, 7, 8 };
        int[] b = new int[] { 2, 4, 5, 6, 7, 6, 1, 2 };
        int[] res = new int[a.length];
        
        Schedule(a, b, res);
        int time = ComputeTime(a, b, res);
        System.out.println("总执行时间：" + time);
        
        Show(a, b, res);
        PrintArr(new int[][] { res }, "Result");
    }

    public static void Schedule(int[] a, int[] b, int[] res) {
        int len = a.length;
        Task[] tasks = new Task[len];

        for (int i = 0; i < len; i++) {
            tasks[i] = new Task(i, Math.min(a[i], b[i]), a[i] <= b[i]);
        }

        Arrays.sort(tasks);

        for (int i = 0, m = 0, n = len - 1; i < len; i++) {
            if (tasks[i].isA)
                res[m++] = tasks[i].index;
            else
                res[n--] = tasks[i].index;
        }
    }

    private static int ComputeTime(int[] a, int[] b, int[] res) {
        int m = 0, n = 0; // 两条流水线完成当前 Task 的时间
        for (int i = 0; i < res.length; i++) {
            m += a[res[i]];
            n = Math.max(m, n) + b[res[i]];
        }
        return n;
    }

    private static int MaxLenOfLine = 30; // 字串折叠时的一行最多字符数

    private static void Show(int[] a, int[] b, int[] res) {
        // 生成流水线字串 *2
        List<Word> MWords = new ArrayList<Word>();
        List<Word> NWords = new ArrayList<Word>();
        int m = 0, n = 0; // 两条流水线完成当前 Task 的时间
        for (int i = 0; i < res.length; i++) {
            int colorIndex = new Random().nextInt(ANSI_List.length);
            m += a[res[i]];

            addWords(MWords, res[i], a[res[i]], colorIndex);
            if (m > n) {
                addWords(NWords, -1, m - n, colorIndex);
            }
            addWords(NWords, res[i], b[res[i]], colorIndex);

            n = Math.max(m, n) + b[res[i]];
        }

        // 输出
        int index = 0;
        int lazyColorIndex_M = -1, lazyColorIndex_N = -1;
        while (index < MWords.size() || index < NWords.size()) {
            String str = "";

            // M 生产线
            for (int i = index; i < index + MaxLenOfLine; i++) {
                if (i >= MWords.size())
                    break;

                int MColorIndex = MWords.get(i).colorIndex;
                if (i == index || lazyColorIndex_M != MColorIndex) {
                    str += ANSI_List[MColorIndex];
                    lazyColorIndex_M = MColorIndex;
                }
                str += (MWords.get(i).index == -1 ? "-" : MWords.get(i).index) + " ";
            }

            str += "\n";

            // N 生产线
            for (int i = index; i < index + MaxLenOfLine; i++) {
                if (i >= NWords.size())
                    break;

                int NColorIndex = NWords.get(i).colorIndex;
                if (i == index || lazyColorIndex_N != NColorIndex) {
                    str += ANSI_List[NColorIndex];
                    lazyColorIndex_N = NColorIndex;
                }
                str += (NWords.get(i).index == -1 ? "-" : NWords.get(i).index) + " ";
            }

            System.out.println(str + "\n");
            index = index + MaxLenOfLine;
        }

        System.out.print(ANSI_RESET);
    }

    private static void addWords(List<Word> words, int index, int times, int colorIndex) {
        for (int i = 0; i < times; i++) {
            words.add(new Word(index, colorIndex));
        }
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static String[] ANSI_List = new String[] { ANSI_RED, ANSI_GREEN, ANSI_YELLOW, ANSI_BLUE, ANSI_PURPLE,
            ANSI_CYAN, ANSI_WHITE };

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

class Task implements Comparable<Task> {
    int index;
    int value;
    boolean isA;

    public Task(int index, int value, boolean isA) {
        this.index = index;
        this.value = value;
        this.isA = isA;
    }

    @Override
    public int compareTo(Task other) {
        if (value > other.value)
            return 1;
        else if (value < other.value)
            return -1;
        else
            return 0;
    }
}

class Word {
    int index;
    int colorIndex;

    public Word(int index, int colorIndex) {
        this.index = index;
        this.colorIndex = colorIndex;
    }
}