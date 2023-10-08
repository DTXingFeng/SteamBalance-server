package xyz.xingfeng.SteamBalanceServer.Steam;

public class Item {
    //id
    private String id;
    //链接
    private String href;
    //图片链接
    private String img;
    //名称
    private String name;
    //最低出售价
    private String normalPrice;
    //销售价（？
    private String salePrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(String normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", normalPrice='" + normalPrice + '\'' +
                ", salePrice='" + salePrice + '\'' +
                '}';
    }
}
