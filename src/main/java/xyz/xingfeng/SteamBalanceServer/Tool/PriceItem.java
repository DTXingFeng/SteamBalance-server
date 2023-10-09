package xyz.xingfeng.SteamBalanceServer.Tool;

public class PriceItem {
    private float sell_min_price;
    private float quick_price;

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
        String p1 = price.replaceAll("¥", "").replaceAll(",","").trim();
        return Float.parseFloat(p1);
    }
}
