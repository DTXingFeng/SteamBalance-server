package xyz.xingfeng.SteamBalanceServer.Controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;
import xyz.xingfeng.SteamBalanceServer.lang.ScreenItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/sort")
public class SortController {

    @PostMapping(value = "/offQuick", produces = "application/json; charset=UTF-8")
    public String offQuick(@RequestBody ScreenItem screenItem){
        JSONObject jsonObject = new JSONObject();
        JSONArray quick_price = JsonArraySortWithPaginationUp("off_quick", screenItem.getPage(), Double.parseDouble(screenItem.getMin_price()));
        if (quick_price != null){
            jsonObject.put("code",Code.OK);
            jsonObject.put("data",quick_price);
            return jsonObject.toString();
        }
        jsonObject.put("code",Code.NO);
        return jsonObject.toString();
    }

    @PostMapping(value = "/offSell", produces = "application/json; charset=UTF-8")
    public String offSell(@RequestBody ScreenItem screenItem){
        JSONObject jsonObject = new JSONObject();
        JSONArray quick_price = JsonArraySortWithPaginationUp("off_sell", screenItem.getPage(), Double.parseDouble(screenItem.getMin_price()));
        if (quick_price != null){
            jsonObject.put("code",Code.OK);
            jsonObject.put("data",quick_price);
            return jsonObject.toString();
        }
        jsonObject.put("code",Code.NO);
        return jsonObject.toString();
    }

    @PostMapping(value = "/sellPrice", produces = "application/json; charset=UTF-8")
    public String sellPrice(@RequestBody ScreenItem screenItem){
        JSONObject jsonObject = new JSONObject();
        JSONArray quick_price = JsonArraySortWithPaginationDown("sell_price", screenItem.getPage(), Double.parseDouble(screenItem.getMin_price()));
        if (quick_price != null){
            jsonObject.put("code",Code.OK);
            jsonObject.put("data",quick_price);
            return jsonObject.toString();
        }
        jsonObject.put("code",Code.NO);
        return jsonObject.toString();
    }


    @PostMapping(value = "/quickPrice", produces = "application/json; charset=UTF-8")
    public String quickPrice(@RequestBody ScreenItem screenItem){
        JSONObject jsonObject = new JSONObject();
        JSONArray quick_price = JsonArraySortWithPaginationDown("quick_price", screenItem.getPage(), Double.parseDouble(screenItem.getMin_price()));
        if (quick_price != null){
            jsonObject.put("code",Code.OK);
            jsonObject.put("data",quick_price);
            return jsonObject.toString();
        }
        jsonObject.put("code",Code.NO);
        return jsonObject.toString();
    }


    private JSONArray JsonArraySortWithPaginationDown(String key,int page,double min_price){
        FileDo fileDo = new FileDo(new File("data/record.json"));
        JSONArray jsonArray = new JSONArray(fileDo.copy());
        // 创建一个 List 来存储 JSONObject 元素
        List<JSONObject> jsonObjects = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            double buffMinSell = jsonObject.getDouble("buff_min_sell");
            if (buffMinSell >= min_price) {
                jsonObjects.add(jsonArray.getJSONObject(i));
            }
        }

        // 使用自定义的比较器按照 "quick_price" 的值从大到小排序（作为浮点数）
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                double price1 = o1.getDouble(key);
                double price2 = o2.getDouble(key);
                return Double.compare(price2, price1);
            }
        });

        // 分页功能
//        int page = 1; // 页数，从1开始
        return getJsonArray(page, jsonObjects);
    }

    private JSONArray JsonArraySortWithPaginationUp(String key,int page,double min_price){
        FileDo fileDo = new FileDo(new File("data/record.json"));
        JSONArray jsonArray = new JSONArray(fileDo.copy());
        // 创建一个 List 来存储 JSONObject 元素
        List<JSONObject> jsonObjects = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            double buffMinSell = jsonObject.getDouble("buff_min_sell");
            if (buffMinSell >= min_price) {
                jsonObjects.add(jsonArray.getJSONObject(i));
            }
        }

        // 使用自定义的比较器按照 "off_quick" 的值从小到大排序（作为浮点数）
        Collections.sort(jsonObjects, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                double offQuick1 = o1.getDouble(key);
                double offQuick2 = o2.getDouble(key);
                return Double.compare(offQuick1, offQuick2);
            }
        });

        // 分页功能
//        int page = 2; // 页数，从1开始
        return getJsonArray(page, jsonObjects);
    }

    @Nullable
    private JSONArray getJsonArray(int page, List<JSONObject> jsonObjects) {
        int pageSize = 10; // 每页的元素数量

        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(page * pageSize, jsonObjects.size());
        if (startIndex < jsonObjects.size()) {
            List<JSONObject> pageData = jsonObjects.subList(startIndex, endIndex);

            // 创建一个新的 JSON 数组来存储分页后的元素
            JSONArray pagedArray = new JSONArray(pageData);
            // 输出分页后的 JSON 数组
            return pagedArray;
        } else {
            System.out.println("No data available for the requested page.");
        }
        return null;
    }
}
