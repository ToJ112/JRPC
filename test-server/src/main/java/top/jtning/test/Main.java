package top.jtning.test;

import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int T = scanner.nextInt(); // 总共T关
        int N = scanner.nextInt(); // 每关有N个敌人
        for (int i = 0; i < T; i++) {
            int[] hp = new int[N];
            for (int j = 0; j < N; j++) {
                hp[j] = scanner.nextInt();
            }
            System.out.println(minShots(hp)); // 输出最少按键次数
        }
    }

    private static int minShots(int[] hp) {
        int shots = 0;
        Arrays.sort(hp);
        int n = hp.length;
        for (int i = 0; i < n; i++) {
            while (hp[i] > 0 && i != n - 1) {
                shots++;
                hp[i]--;
                if (i + 1 < n) {
                    hp[i + 1]--;
                }
            }
            if (i == n - 1 && hp[i] > 0) {
                shots++;
                break;
            }
        }
        return Math.min(shots, n);
    }
}




