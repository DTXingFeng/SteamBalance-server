package xyz.xingfeng.SteamBalanceServer.Steam;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xyz.xingfeng.SteamBalanceServer.Tool.SteamCookie;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 这个类用来查看饰品的价格
 */
@Slf4j
public class Price {

    //求购价
    String purchasePrice;
    //出售价
    String sellingPrice;

    int code = 199;
    public static final String urL = "https://steamcommunity.com/market/itemordershistogram?country=CN&language=schinese&currency=23&item_nameid={{id}}&two_factor=0";
    private StringBuilder response;

    public int getCode() {
        return code;
    }

    public Price(String item_id){
        int num = 0;
        while (true) {
            if (num >= 5){
                break;
            }
            try {
                //构建请求url
                URL url = new URL(urL.replace("{{id}}",item_id));
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.addRequestProperty("Cookie", SteamCookie.STEAMCOOKIE);
                urlc.addRequestProperty("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
                urlc.setRequestMethod("GET");
                //是否成功
                if (urlc.getResponseCode() == 200) {
                    //获取返回流
                    InputStream inputStream = urlc.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                }else if (urlc.getResponseCode() == 429){
                    log.warn("请求次数太多，请稍后尝试");
                    sellingPrice = "0";
                    purchasePrice = "0";
                    this.code = 429;
                    return;
                }else {
                    sellingPrice = "0";
                    purchasePrice = "0";
                    num++;
                    if (num >= 5){
                        return;
                    }
                    Thread.sleep(1000);
                    continue;
                }

                //将返回内容解析成json格式
                //System.out.println(response.toString());
                JSONObject jsonObject = new JSONObject(response.toString());
                String sell_order_table = jsonObject.getString("buy_order_summary");
                String sell_order_summary = jsonObject.getString("sell_order_summary");

                //json内容中有html格式，交给jsoup解析
                //System.out.println(sell_order_table);
                Document parse = Jsoup.parse(sell_order_table);
                //得出最高求购价
                purchasePrice = parse.getElementsByClass("market_commodity_orders_header_promote").get(1).text();
//                System.out.println("求购价" + parse.getElementsByClass("market_commodity_orders_header_promote").get(1).text());
                parse = Jsoup.parse(sell_order_summary);
//                System.out.println(parse.getElementsByClass("market_commodity_orders_header_promote").get(1).text());
                //得出最低寄售价
                sellingPrice = parse.getElementsByClass("market_commodity_orders_header_promote").get(1).text();
                this.code = 200;
            } catch (Exception e) {
                num++;
                continue;
            }
            break;
        }
    }

    @Override
    public String toString() {
        return "Price{" +
                "最高求购价='" + purchasePrice + '\'' +
                ", 最低寄售价='" + sellingPrice + '\'' +
                '}';
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
