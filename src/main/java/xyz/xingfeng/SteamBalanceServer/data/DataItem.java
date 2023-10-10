package xyz.xingfeng.SteamBalanceServer.data;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Date;

/**
 * 这个类中拥有在网页上需要展示的内容
 * 单个物品数据类
 */
public class DataItem {
    //饰品的名字
    private String name;
    //饰品在steam中的id
    private String steam_id;
    //饰品在steam中的最小售价
    private double steam_min_sell;
    //饰品在steam中的最高求购价
    private double steam_quick;

    //饰品在buff中的id
    private String buff_id;
    //饰品在buff中的最小售价
    private double buff_min_sell;
    //饰品在buff中的最高求购价
    private double buff_quick;

    //打折余额
    //出售(挂售)
    private double off_sell;
    //求购（丢求购）
    private double off_quick;

    //打折余额持平收益
    //出售
    private double quick_price;
    //求购
    private double sell_price;

    //时间
    private String date;


    @Override
    public String toString() {
        return "DataItem{" +
                "name='" + name + '\'' +
                ", steam_id='" + steam_id + '\'' +
                ", steam_min_sell=" + steam_min_sell +
                ", steam_quick=" + steam_quick +
                ", buff_id='" + buff_id + '\'' +
                ", buff_min_sell=" + buff_min_sell +
                ", buff_quick=" + buff_quick +
                ", off_sell=" + off_sell +
                ", off_quick=" + off_quick +
                ", quick_price=" + quick_price +
                ", sell_price=" + sell_price +
                ", date='" + date + '\'' +
                '}';
    }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("steam_id",steam_id);
        jsonObject.put("steam_min_sell",steam_min_sell);
        jsonObject.put("steam_quick",steam_quick);
        jsonObject.put("buff_id",buff_id);
        jsonObject.put("buff_min_sell",buff_min_sell);
        jsonObject.put("buff_quick",buff_quick);
        jsonObject.put("off_sell",off_sell);
        jsonObject.put("off_quick",off_quick);
        jsonObject.put("sell_price",sell_price);
        jsonObject.put("quick_price",quick_price);
        jsonObject.put("date",date);
        return jsonObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSteam_id() {
        return steam_id;
    }

    public void setSteam_id(@NotNull String steam_id) {
        this.steam_id = steam_id;
    }

    public double getSteam_min_sell() {
        return steam_min_sell;
    }

    public void setSteam_min_sell(@NotNull double steam_min_sell) {
        this.steam_min_sell = steam_min_sell;
    }

    public double getSteam_quick() {
        return steam_quick;
    }

    public void setSteam_quick(@NotNull double steam_quick) {
        this.steam_quick = steam_quick;
    }

    public String getBuff_id() {
        return buff_id;
    }

    public void setBuff_id(@NotNull String buff_id) {
        this.buff_id = buff_id;
    }

    public double getBuff_min_sell() {
        return buff_min_sell;
    }

    public void setBuff_min_sell(@NotNull double buff_min_sell) {
        this.buff_min_sell = buff_min_sell;
    }

    public double getBuff_quick() {
        return buff_quick;
    }

    public void setBuff_quick(@NotNull double buff_quick) {
        this.buff_quick = buff_quick;
    }

    public double getOff_sell() {
        return off_sell;
    }

    public void setOff_sell(@NotNull double off_sell) {
        this.off_sell = off_sell;
    }

    public double getOff_quick() {
        return off_quick;
    }

    public void setOff_quick(@NotNull double off_quick) {
        this.off_quick = off_quick;
    }

    public double getQuick_price() {
        return quick_price;
    }

    public void setQuick_price(@NotNull double quick_price) {
        this.quick_price = quick_price;
    }

    public double getSell_price() {
        return sell_price;
    }

    public void setSell_price(@NotNull double sell_price) {
        this.sell_price = sell_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(@NotNull String date) {
        this.date = date;
    }
}
