package xyz.xingfeng.SteamBalanceServer.Buff;

public class Item {
    //id
    private int appid;
    //这个变量用来在steam上搜索
    private String name;
    //求购价格
    private String quick_price;
    //最低售价
    private String sell_min_price;
    //购买最大（成交）价格

    //正在出售的数量
    private int sell_num;

    private String game;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Item{" +
                "appid=" + appid +
                ", name='" + name + '\'' +
                ", quick_price='" + quick_price + '\'' +
                ", sell_min_price='" + sell_min_price + '\'' +
                ", sell_num=" + sell_num +
                ", game='" + game + '\'' +
                '}';
    }

    public int getSell_num() {
        return sell_num;
    }

    public void setSell_num(int sell_num) {
        this.sell_num = sell_num;
    }

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuick_price() {
        return quick_price;
    }

    public void setQuick_price(String quick_price) {
        this.quick_price = quick_price;
    }

    public String getSell_min_price() {
        return sell_min_price;
    }

    public void setSell_min_price(String sell_min_price) {
        this.sell_min_price = sell_min_price;
    }
}
