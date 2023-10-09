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

import static xyz.xingfeng.SteamBalanceServer.practice.DotaPractice.getPriceItem;

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
                //需要多少折才能持平收益
                //steam售价*打折 = buff出售价格*0.975的手续费
                //打折 = buff出售价格*0.975的手续费/steam售价
                double quickPrice = Float.parseFloat(item.getQuick_price())*0.975 /sousuo.getSell_min_price();
                double sellPrice = Float.parseFloat(item.getSell_min_price())*0.975 /sousuo.getSell_min_price();

                log.info("如果打算丢求购，需要" + quickPrice + "折余额才能回本");
                log.info("如果打算慢慢卖，需要" + sellPrice + "折余额才能回本");
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
                //完成一个饰品筛选，休息五秒
                Thread.sleep(5000);
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
        return getPriceItem(name, data, log);
    }
}
