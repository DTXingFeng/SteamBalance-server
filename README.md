# Cs饰品 Buff商城 steam市场数据分析 
使用spring boot框架开发的拥有爬虫功能的网页程序  
小白作品，还请大佬多多指教
# 能做什么？ 
- 主要是获得buff上的饰品数据，然后在steam上获得该饰品数据，然后进行分析从而获得到：  
- - 如果要倒余额，会获得多少折的余额  
- - 如果要套现，需要多少折的余额才能回本
  
- 获得数据之后会在项目中储存起来，等在有人使用网页访问时展示所需的数据（最优余额，与最优套现金）
# 爬虫所需的数据
steam的cookie与buff的cookie  
使用的是cookie的方式进行登录操作
# 爬虫收集的数据
### buff:
- 物品的id
- 在售数量
- 最低在售
- 最高求购
### steam:
- 物品的id
- 在售数量
- 最低在售
- 最高求购
# 网页的展示效果
![image]([[https://github.com/DTXingFeng/SteamBalance-server/blob/main/image/img.png](https://github.com/DTXingFeng/SteamBalance-server/blob/main/image/img.png?raw=true)])
# 一些要说的
- 这个项目不使用数据库，使用的是json文件储存  
- steam的爬虫爬一会就提示访问次数过多了，不知道是账号出问题了还是程序出问题了，如果有大佬知道还请告知
