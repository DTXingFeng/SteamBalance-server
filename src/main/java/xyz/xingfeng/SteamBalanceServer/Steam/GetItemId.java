package xyz.xingfeng.SteamBalanceServer.Steam;

import xyz.xingfeng.SteamBalanceServer.Tool.SleepTime;
import xyz.xingfeng.SteamBalanceServer.Tool.SteamCookie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetItemId {

    private String id;

    /**
     * 获得物品id
     *
     */
    public GetItemId(String urL){
        URL url = null;
        int i = 0;
        while (true) {
            try {
                url = new URL(urL);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("GET");
                huc.addRequestProperty("Cookie", SteamCookie.STEAMCOOKIE);
                huc.addRequestProperty("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
                if (huc.getResponseCode() == 200) {
                    //请求成功，获取数据
                    //获取返回流
                    InputStream inputStream = huc.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();

                    String substring = response.substring(response.indexOf("Market_LoadOrderSpread("));
                    id = substring.substring(substring.indexOf("(") + 1, substring.indexOf(")")).trim();
//                System.out.println(id);

                } else {
                    id = "0";
                }
                try {
                    Thread.sleep(1000L * SleepTime.normalRandomTime());
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
                break;
            } catch (Exception e) {
                i++;
                if (i > 5) {
                    //休息
                    try {
                        Thread.sleep(1000L * SleepTime.normalRandomTime());
                    } catch (InterruptedException e1) {
                        throw new RuntimeException(e1);
                    }
                    break;
                }
            }
        }
    }

    public String getId() {
        return id;
    }
}
