package xyz.xingfeng.SteamBalanceServer.Dao;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MainDao {
    private int ID;
    private String 饰品名称;
    private int SteamID;
    private String 图片url;
    private int 图片是否下载;
    private Date 更新时间;
    private String 来自哪个游戏;

}
