package xyz.xingfeng.SteamBalanceServer.practice;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import xyz.xingfeng.SteamBalanceServer.Buff.DotaGetList;
import xyz.xingfeng.SteamBalanceServer.Buff.Item;
import xyz.xingfeng.SteamBalanceServer.Steam.Price;
import xyz.xingfeng.SteamBalanceServer.Steam.Search;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;
import xyz.xingfeng.SteamBalanceServer.Tool.PriceItem;
import xyz.xingfeng.SteamBalanceServer.data.DataItem;
import xyz.xingfeng.SteamBalanceServer.data.RecordDo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class DotaPractice {
    public DotaPractice() throws Exception {

//        setProperty("127.0.0.1","26501");
        while (true) {
            DotaGetList getList = new DotaGetList();
            ArrayList<Item> items = getList.getItems();
            for (Item item : items) {
                if(item.getSell_num() < 50){
                    //小于30在售就不要浪费时间了
                    continue;
                }
                log.info("--------" + item.getName() + "--------");
                log.info("buff在售底价:" + item.getSell_min_price());
                log.info("buff求购最高价:" + item.getQuick_price());
                log.info("buff在售量" + item.getSell_num());
                //对比steam价格
                PriceItem sousuo = sousuo(item.getName());
                //算出优惠
                //buff底价/steam最高求购价*1.15
                double off_quick = Float.parseFloat(item.getSell_min_price()) / sousuo.getQuick_price() * 1.15;
                log.info("求购优惠" + off_quick);
                double off_sell = Float.parseFloat(item.getSell_min_price()) / sousuo.getSell_min_price() * 1.15;
                log.info("底价优惠" + off_sell);
                //需要多少折才能持平收益
                //steam售价*打折 = buff出售价格*0.982的手续费
                //打折 = buff出售价格*0.982的手续费/steam售价
                double quickPrice = Float.parseFloat(item.getQuick_price())*0.982 /sousuo.getSell_min_price();
                double sellPrice = Float.parseFloat(item.getSell_min_price())*0.982 /sousuo.getSell_min_price();

                log.info("如果打算丢求购，需要" + quickPrice + "折余额才能回本");
                log.info("如果打算慢慢卖，需要" + sellPrice + "折余额才能回本");
                //计算出如果提现会赚多少钱
                //假设是八折余额
                //buff最高求购价-steam最低售价*0.8
                //buff的dota2板块收取1.8%的手续费
                double put = (Float.parseFloat(item.getQuick_price()) - sousuo.getSell_min_price() * 0.8) * 0.982;
                if (off_quick < 0.8) {
                    log.info("卧槽出了");
                }
                if (put > 0.0 && sousuo.getSell_min_price() != 0.0){
                    log.info("买"+item.getName()+"可以赚" + put);

                    double zhuan = (100/(sousuo.getSell_min_price() * 0.8)) * put;
                    log.info("每一百块钱可赚" + zhuan);
                }
                if (quickPrice != off_quick) {
                    //将数据都添加进记录
                    DataItem dataItem = new DataItem();
                    dataItem.setName(item.getName());
                    //steam部分
                    dataItem.setSteam_id(sousuo.getId());
                    dataItem.setSteam_min_sell(sousuo.getSell_min_price());
                    dataItem.setSteam_quick(sousuo.getSell_min_price());
                    //buff部分
                    dataItem.setBuff_id(String.valueOf(item.getAppid()));
                    dataItem.setBuff_min_sell(Double.parseDouble(item.getSell_min_price()));
                    dataItem.setBuff_quick(Double.parseDouble(item.getQuick_price()));
                    //综合部分
                    dataItem.setOff_quick(off_quick);
                    dataItem.setOff_sell(off_sell);
                    dataItem.setQuick_price(quickPrice);
                    dataItem.setSell_price(sellPrice);
                    //生成时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    dataItem.setDate(formatter.format(date));
                    //在售数量
                    dataItem.setBuff_buy_num(item.getSell_num());
                    dataItem.setSteam_buy_num(sousuo.getBuy_num());

                    //进行更新
                    new RecordDo().upData(dataItem);
                }

                //完成一个饰品筛选，休息五秒
                Thread.sleep(20000);
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
        FileDo fileDo = new FileDo(new File("config/dotaItems.json"));
        JSONObject jsonObject = new JSONObject(fileDo.copy());
        JSONArray data = jsonObject.getJSONArray("data");
        return getPriceItem(name, data, log);
    }

    public static PriceItem getPriceItem(String name, JSONArray data, Logger log) {
        for (int i = 0; i < data.length(); i++){
            JSONObject item = data.getJSONObject(i);
            String itemName = item.getString("name");
            if (itemName.equals(name)){
                PriceItem pi = new PriceItem();
                pi.setId(item.getString("item_id"));
                Price price = new Price(item.getString("item_id"));
                pi.setBuy_num(price.getBuy_num());
                if (frequentState(log, pi, price)) break;
                //只要一个
                return pi;
            }
        }
        //未在json文件中找到目标
        //使用steam的搜索模式
        Search search = new Search(name.replaceAll(" ",""));
        //搜索模式返回的数据为数组形态
        ArrayList<xyz.xingfeng.SteamBalanceServer.Steam.Item> items = search.getItems();
        //价钱转换对象
        PriceItem pi = new PriceItem();
        for (xyz.xingfeng.SteamBalanceServer.Steam.Item item : items){
            if (!item.getName().equals(name)){
                //如果是名字不符的饰品就直接往下走
                continue;
            }
            //查看id为item.getId()的饰品价格
            Price price = new Price(item.getId());
            pi.setId(item.getId() );
            pi.setBuy_num(price.getBuy_num());
            if (frequentState(log, pi, price)) break;
            //只要一个
            log.info(item.getName());
            break;
        }
        return pi;
    }

    /**
     * 针对频繁爬取的对策
     * @return
     */
    public static boolean frequentState(Logger log, PriceItem pi, Price price) {
        if (price.getCode() == 429){
            //访问次数太多需要休息
            //休息一分钟
            try {
                Thread.sleep(1000*60*2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        log.info("steam在售底价:"+price.getSellingPrice());
        log.info("steam求购最高价:"+price.getPurchasePrice());
        pi.setQuick_price(price.getPurchasePrice());
        pi.setSell_min_price(price.getSellingPrice());
        return false;
    }

    public void addItemId(String id, String name){
        FileDo fileDo = new FileDo(new File("config/dotaItems.json"));
        JSONObject jsonObject = new JSONObject(fileDo.copy());
        JSONArray data = jsonObject.getJSONArray("data");
        JSONObject json = new JSONObject();
        json.put("name",name);
        json.put("item_id",id);
        data.put(json);
        fileDo.fugai(jsonObject.toString());
    }

    public void delItemId(String id){
        FileDo fileDo = new FileDo(new File("config/items.json"));
        JSONObject jsonObject = new JSONObject(fileDo.copy());
        JSONArray data = jsonObject.getJSONArray("data");
        jsonObject.remove("data");
        for (int i = 0; i < data.length(); i++){
            if (data.getJSONObject(i).getString("item_id").equals(id)){
                data.remove(i);
            }
        }
        jsonObject.put("data",data);
        fileDo.fugai(jsonObject.toString());
    }


}
