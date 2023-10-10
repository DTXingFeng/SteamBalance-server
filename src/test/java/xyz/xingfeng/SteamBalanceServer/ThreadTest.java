package xyz.xingfeng.SteamBalanceServer;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;

import java.io.File;
import java.util.*;

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

    @Test
    public void JsonArraySortWithPagination() throws Exception {
        FileDo fileDo = new FileDo(new File("data/record.json"));
        JSONArray jsonArray = new JSONArray(fileDo.copy());
        // 创建一个 List 来存储 JSONObject 元素
        List<JSONObject> jsonObjects = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            jsonObjects.add(jsonArray.getJSONObject(i));
        }
        // 使用自定义的比较器按照 "quick_price" 的值从大到小排序
        jsonObjects.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                int price1 = 0;
                try {
                    price1 = o1.getInt("quick_price");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                int price2 = 0;
                try {
                    price2 = o2.getInt("quick_price");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                return price2 - price1;
            }
        });

        // 分页功能
        int page = 1; // 页数，从1开始
        int pageSize = 10; // 每页的元素数量

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(page * pageSize, jsonObjects.size());

        if (startIndex < jsonObjects.size()) {
            List<JSONObject> pageData = jsonObjects.subList(startIndex, endIndex);

            // 创建一个新的 JSON 数组来存储分页后的元素
            JSONArray pagedArray = new JSONArray(pageData);

            // 输出分页后的 JSON 数组
            System.out.println(pagedArray.toString(1));
        } else {
            System.out.println("No data available for the requested page.");
        }
    }
}

