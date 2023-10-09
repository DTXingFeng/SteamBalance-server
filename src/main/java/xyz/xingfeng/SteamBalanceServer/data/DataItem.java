package xyz.xingfeng.SteamBalanceServer.data;

import java.util.Date;

/**
 * 这个类中拥有在网页上需要展示的内容
 * 单个物品数据类
 */
public class DataItem {
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
    private double off_sell;

    //打折余额持平收益
    //出售
    private double quick_price;
    //求购
    private double sell_price;

    //时间
    private Date date;

}
