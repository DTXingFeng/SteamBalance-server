package xyz.xingfeng.SteamBalanceServer.lang;

public class ScreenItem {
    private int page = 1;
    private String min_price = "0";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getMin_price() {
        return min_price;
    }

    public void setMin_price(String min_price) {
        this.min_price = min_price;
    }

    @Override
    public String toString() {
        return "ScreenItem{" +
                "page=" + page +
                ", min_price='" + min_price + '\'' +
                '}';
    }
}
