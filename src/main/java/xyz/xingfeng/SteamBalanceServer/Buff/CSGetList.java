package xyz.xingfeng.SteamBalanceServer.Buff;

import org.json.JSONArray;
import org.json.JSONObject;
import xyz.xingfeng.SteamBalanceServer.Tool.BuffCookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CSGetList {
    ArrayList<Item> items = new ArrayList<>();
    public final static String URL = "https://buff.163.com/api/market/goods?game=csgo&page_num=1&use_suggestion=0&_=1695793716943";
    public CSGetList() throws IOException {
        int num = 0;
        while (true) {
            try {
                java.net.URL url = new URL(URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.addRequestProperty("Cookie", BuffCookie.BUFFCOOKIE);
                httpURLConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36 Edg/117.0.2045.60");
                httpURLConnection.addRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
                httpURLConnection.setRequestMethod("GET");
                if (httpURLConnection.getResponseCode() == 200) {
                    //请求成功，获取数据
                    //获取返回流
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("items");
                    items = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Item item = new Item();
                        JSONObject itemJson = jsonArray.getJSONObject(i);
                        item.setGame(itemJson.getString("game"));
                        item.setAppid(itemJson.getInt("appid"));
                        item.setName(itemJson.getString("name"));
                        item.setQuick_price(itemJson.getString("quick_price"));
                        item.setSell_min_price(itemJson.getString("sell_min_price"));
                        item.setSell_num(itemJson.getInt("sell_num"));
                        items.add(item);
                    }
                }
                return;
            }catch (Exception e) {
                num++;
                if (num >=5){
                    throw new RuntimeException(e);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
