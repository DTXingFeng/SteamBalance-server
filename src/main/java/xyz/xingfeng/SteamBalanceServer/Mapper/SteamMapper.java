package xyz.xingfeng.SteamBalanceServer.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import xyz.xingfeng.SteamBalanceServer.Dao.SteamDao;

import java.util.List;

@Mapper
public interface SteamMapper {

    @Select("select * from steam数据表")
    public List<SteamDao> getAll();

    @Select("SELECT * from steam数据表 where 数据ID = #{id}")
    public SteamDao getById(int id);

    @Insert("INSERT INTO `steam数据表`(`饰品ID`, `最低出售价格`, `最高求购价格`) VALUES (#{饰品ID},#{最低出售价格},#{最高求购价格})")
    public boolean addData(SteamDao steamDao);
}
