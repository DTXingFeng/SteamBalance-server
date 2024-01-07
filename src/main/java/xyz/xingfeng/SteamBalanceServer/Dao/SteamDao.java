package xyz.xingfeng.SteamBalanceServer.Dao;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;


@Data
@ToString
public class SteamDao {
    private int 数据ID;
    private int 饰品ID;
    private double 最低出售价格;
    private double 最高求购价格;
    private Date 更新时间;
}
