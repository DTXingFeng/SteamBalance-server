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
## buff:
- 物品的id
- 在售数量
- 最低在售
- 最高求购
## steam:
- 物品的id
- 在售数量
- 最低在售
- 最高求购
# 网页的展示效果
![image](https://github.com/DTXingFeng/SteamBalance-server/blob/main/image/img.png)
# 一些要说的
- ~~这个项目不使用数据库，使用的是json文件储存~~  
- 新版本使用mysql数据库，json文件遍历太慢了
- ~~steam的爬虫爬一会就提示访问次数过多了，不知道是账号出问题了还是程序出问题了，如果有大佬知道还请告知~~
- steam反爬策略，需要切换ip，账号似乎不用换
# steam请求api
- steam的单页列表(搜索页)api  
**https://steamcommunity.com/market/search/render/?query= `{搜索内容，可默认空白}`&start={从第几个开始}&count= `{一共展示多少个，一般为10}`&appid= `{游戏id，cs2为730,dota2为570}`**  
> 请求完成后返回后是json数据格式  
> 大概为  
> ```json
> {
>  "success": true,
>  "start": 10,
>  "pagesize": 10,
>  "total_count": 20521,
>  "tip": "Set norender=1 if you don't want HTML",
>  "results_html": "\t<div class=\"market_listing_table_header\">...."
> }
> ```
> results_html中包含了
> - 饰品详情的链接  
> - 饰品缩略图
> - 饰品昵称
> - 饰品的出售价格
> 
> 但我需要获得最高求购价与最低售价  
> 这个需要知道饰品的id才能进行访问，所以要用到`results_html`中的`饰品详情的链接`  
> 访问这个链接，正则查找`Market_LoadOrderSpread(`到`)`之间的内容，即可获得饰品在steam中的id

- steam饰品的详情
**https://steamcommunity.com/market/itemordershistogram?&language=schinese&currency=1&item_nameid= `饰品id`**
> 请求完成后返回后是json数据格式  
> 内容和上面类似，自己研究