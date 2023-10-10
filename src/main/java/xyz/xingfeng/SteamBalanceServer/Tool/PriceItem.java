package xyz.xingfeng.SteamBalanceServer.Tool;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceItem {
    private float sell_min_price;
    private float quick_price;
    private String buy_num;

    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getSell_min_price() {
        return sell_min_price;
    }

    public void setSell_min_price(String sell_min_price) {
        this.sell_min_price = toFloat(sell_min_price);
    }

    public float getQuick_price() {
        return quick_price;
    }

    public void setQuick_price(String quick_price) {
        this.quick_price = toFloat(quick_price);
    }

    private float toFloat(String price){
        String p1 = "0";
        try {
            p1 = price.replaceAll("¥", "").replaceAll(",","").trim();
        }catch (Exception e){
            log.error("大概率是爬虫未爬到价格返回空值了");
        }
        return Float.parseFloat(p1);
    }
}
