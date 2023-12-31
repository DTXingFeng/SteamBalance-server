package xyz.xingfeng.SteamBalanceServer.Steam;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;
import xyz.xingfeng.SteamBalanceServer.Tool.SleepTime;
import xyz.xingfeng.SteamBalanceServer.Tool.SteamCookie;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetList {

    private ArrayList<Item> items;


    public GetList() throws Exception {
        String urL = "https://steamcommunity.com/market/search/render/?query=&start=0&count=10&search_descriptions=0&sort_column=popular&sort_dir=desc&appid=730";
        URL url = new URL(urL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.addRequestProperty("Cookie", SteamCookie.STEAMCOOKIE);
        httpURLConnection.addRequestProperty("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        httpURLConnection.setRequestMethod("GET");
        if (httpURLConnection.getResponseCode() == 200){
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

            //解析json
            System.out.println(response.toString());
            JSONObject jsonObject = new JSONObject(response.toString());
            //获得html语句
            String results_html = jsonObject.getString("results_html");
//            System.out.println(results_html);
            //解析html语句
            Document parse = Jsoup.parse(results_html);
//            System.out.println(parse.text());
            //获得所有的物品链接
            Elements market_listing_row_link = parse.getElementsByClass("market_listing_row_link");
            items = new ArrayList<>();
            for (Element element: market_listing_row_link){
                Item item = new Item();
                //获得链接
                String href = element.attr("href");
                item.setHref(href);
                //获得图片链接
                String img = element.getElementsByTag("img").get(0).attr("src");
                item.setImg(img);
                //获得商品名称
                String market_listing_item_name = element.getElementsByClass("market_listing_item_name").get(0).text();
                item.setName(market_listing_item_name);
                //获得价格
                String normal_price = element.select(".normal_price .normal_price").get(0).text();
                item.setNormalPrice(normal_price);
                //获得销售价格
                String sale_price = element.getElementsByClass("sale_price").get(0).text();
                item.setSalePrice(sale_price);
                //获得id
                String id = getId(market_listing_item_name);
                if (id == null){
                    id = new GetItemId(href).getId();
                    if (id.equals("0")){
                        continue;
                    }
                    addItemId(id,market_listing_item_name);
                }
                item.setId(id);
                items.add(item);
//                System.out.println(market_listing_item_name +"\n最低售价"+normal_price+"\t销售价格"+sale_price);
            }
        }
        try {
            Thread.sleep(1000L * SleepTime.normalRandomTime());
        } catch (InterruptedException e1) {
            throw new RuntimeException(e1);
        }
    }


    public String getId(String name){
        FileDo fileDo = new FileDo(new File("config/items.json"));
        String copy = fileDo.copy();
        JSONObject jsonObject = new JSONObject(copy);
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0 ; i < data.length(); i++){
            String s = data.getJSONObject(i).getString("name");
            if (s.equals(name)){
                return data.getJSONObject(i).getString("item_id");
            }
        }
        return null;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void addItemId(String id, String name){
        FileDo fileDo = new FileDo(new File("config/items.json"));
        JSONObject jsonObject = new JSONObject(fileDo.copy());
        JSONArray data = jsonObject.getJSONArray("data");
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("item_id",id);
        data.put(json);
        fileDo.fugai(jsonObject.toString());
    }
}
