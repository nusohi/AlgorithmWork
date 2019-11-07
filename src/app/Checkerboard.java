/*
 * @Description: 棋盘覆盖算法
 * @Author: nuso
 * @LastEditors: nuso
 * @Date: 2019-11-06 10:15:01
 * @LastEditTime: 2019-11-07 16:35:32
 */
package app;

public class Checkerboard {
    public static void main(String args[]) {
        int length = 8;                     // 棋盘长度
        Pos special = new Pos(0, 0);        // 特殊方格位置
        
        int[][] board = new int[length][length];
        board[special.x][special.y] = -1;   // 特殊方格：-1

        CoverBoard(board, new Pos(0, 0), new Pos(0, 0), length);
        ShowBoard(board);
    }

    public static int ID = 0; // L 型骨牌的序号

    public static void CoverBoard(int[][] board, Pos start, Pos special, int len) {
        if (len == 1)
            return;
        int id = ID++;
        int halfLen = len / 2;

        // 左上角
        if (start.Include(special, halfLen)) {
            CoverBoard(board, start, special, halfLen);
        } else {
            Pos newSpecial = new Pos(start.x + halfLen - 1, start.y + halfLen - 1);
            board[newSpecial.x][newSpecial.y] = id;
            CoverBoard(board, start, newSpecial, halfLen);
        }

        // 右上角
        Pos rightPos = new Pos(start.x + halfLen, start.y);
        if (rightPos.Include(special, halfLen)) {
            CoverBoard(board, rightPos, special, halfLen);
        } else {
            Pos newSpecial = new Pos(rightPos.x, rightPos.y + halfLen - 1);
            board[newSpecial.x][newSpecial.y] = id;
            CoverBoard(board, rightPos, newSpecial, halfLen);
        }

        // 左下角
        Pos downPos = new Pos(start.x, start.y + halfLen);
        if (downPos.Include(special, halfLen)) {
            CoverBoard(board, downPos, special, halfLen);
        } else {
            Pos newSpecial = new Pos(downPos.x + halfLen - 1, downPos.y);
            board[newSpecial.x][newSpecial.y] = id;
            CoverBoard(board, downPos, newSpecial, halfLen);
        }

        // 右下角
        Pos rightDownPos = new Pos(start.x + halfLen, start.y + halfLen);
        if (rightDownPos.Include(special, halfLen)) {
            CoverBoard(board, rightDownPos, special, halfLen);
        } else {
            Pos newSpecial = rightDownPos;
            board[newSpecial.x][newSpecial.y] = id;
            CoverBoard(board, rightDownPos, newSpecial, halfLen);
        }
    }

    public static void ShowBoard(int[][] board) {
        StringBuilder s = new StringBuilder();
        for (int[] row : board) {
            for (int id : row) {
                s.append(id);
                s.append('\t');
            }
            s.append("\n\n");
        }
        System.out.println("== ShowBoard =================>");
        System.out.println(s.toString());
    }
}

class Pos {
    public int x;
    public int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 判断 target 是否在 Pos 向右下角拓展的正方形范围中
    // len 为范围, offset 为起点的偏移
    public boolean Include(Pos target, int len) {
        return (target.x < x + len) && (target.x >= x) && (target.y < y + len) && (target.y >= y);
    }
}