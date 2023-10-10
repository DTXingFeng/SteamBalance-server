package xyz.xingfeng.SteamBalanceServer;


import org.junit.jupiter.api.Test;

import java.util.Scanner;

public class ThreadTest {


    private static volatile boolean isRunning = false;

    @Test
    public void test() {
        Thread workerThread = new Thread(() -> {
            while (true) {
                if (Thread.interrupted()) {
                    System.out.println("线程被中断");
                    break;
                }
                if (isRunning) {
                    // 执行长时间任务
                    System.out.println("线程正在工作");
                }
            }
        });

        workerThread.start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入 start 启动线程，stop 停止线程，status 查看线程状态:");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("start")) {
                isRunning = true;
            } else if (input.equalsIgnoreCase("stop")) {
                isRunning = false;
                workerThread.interrupt(); // 中断线程的执行
            } else if (input.equalsIgnoreCase("status")) {
                System.out.println("线程状态: " + (isRunning ? "运行中" : "已停止"));
            } else {
                System.out.println("无效的输入，请输入 start、stop 或 status");
            }
        }
    }
}

