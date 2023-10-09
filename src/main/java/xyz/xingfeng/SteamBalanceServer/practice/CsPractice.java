package xyz.xingfeng.SteamBalanceServer.practice;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.xingfeng.SteamBalanceServer.Buff.CSGetList;
import xyz.xingfeng.SteamBalanceServer.Buff.Item;
import xyz.xingfeng.SteamBalanceServer.Steam.Price;
import xyz.xingfeng.SteamBalanceServer.Steam.Search;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;
import xyz.xingfeng.SteamBalanceServer.Tool.PriceItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class CsPractice {
    public CsPractice() throws Exception {

        setProperty("127.0.0.1","7890");
        while (true) {
            CSGetList getList = new CSGetList();
            ArrayList<Item> items = getList.getItems();
            for (Item item : items) {
                log.info("--------" + item.getName() + "--------");
                log.info("buff在售底价:" + item.getSell_min_price());
                log.info("buff求购最高价:" + item.getQuick_price());
                log.info("buff在售量" + item.getSell_num());
                //对比steam价格
                PriceItem sousuo = sousuo(item.getName());
                //算出优惠
                //buff底价/steam最高求购价*1.15
                double offers = Float.parseFloat(item.getSell_min_price()) / sousuo.getQuick_price() * 1.15;
                log.info("最终优惠" + offers);
                //计算出如果提现会赚多少钱
                //假设是八折余额
                //buff最高求购价-steam最低售价*0.8
                //buff的cs板块收取2.5%的手续费
                double put = (Float.parseFloat(item.getQuick_price()) - sousuo.getSell_min_price() * 0.8) * 0.975;
                if (offers < 0.8) {
                    log.info("卧槽出了");
                }
                if (put > 0.0 && sousuo.getSell_min_price() != 0.0){
                    log.info("买"+item.getName()+"可以赚" + put);

                    double zhuan = (100/(sousuo.getSell_min_price() * 0.8)) * put;
                    log.info("每一百块钱可赚" + zhuan);
                }
            }
        }
    }


    /**
     * 设置代理
     */
    public void setProperty(String Host,String Port){
        System.setProperty("http.proxyHost", Host);
        System.setProperty("http.proxyPort", Port);

        // 对https也开启代理
        System.setProperty("https.proxyHost", Host);
        System.setProperty("https.proxyPort", Port);
    }

    public PriceItem sousuo(String name) throws IOException {
        //先看看json文件有没有数据
        FileDo fileDo = new FileDo(new File("config/csItems.json"));
        JSONObject jsonObject = new JSONObject(fileDo.copy());
        JSONArray data = jsonObject.getJSONArray("data");
        for (int i = 0; i < data.length(); i++){
            JSONObject item = data.getJSONObject(i);
            String itemName = item.getString("name");
            if (itemName.equals(name)){
                PriceItem pi = new PriceItem();
                Price price = new Price(item.getString("item_id"));
                log.info("steam在售底价:"+price.getSellingPrice());
                log.info("steam求购最高价:"+price.getPurchasePrice());
                pi.setQuick_price(price.getPurchasePrice());
                pi.setSell_min_price(price.getSellingPrice());
                //只要一个
                return pi;
            }
        }
        Search search = new Search(name.replaceAll(" ",""));
        ArrayList<xyz.xingfeng.SteamBalanceServer.Steam.Item> items = search.getItems();
        PriceItem pi = new PriceItem();
        for (xyz.xingfeng.SteamBalanceServer.Steam.Item item : items){
            if (!item.getName().equals(name)){
                continue;
            }
            Price price = new Price(item.getId());
            log.info("steam在售底价:"+price.getSellingPrice());
            log.info("steam求购最高价:"+price.getPurchasePrice());
            pi.setQuick_price(price.getPurchasePrice());
            pi.setSell_min_price(price.getSellingPrice());
            //只要一个
            log.info(item.getName());
            break;
        }
        return pi;
    }
}
