package xyz.xingfeng.SteamBalanceServer.data;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;

import java.io.File;
import java.io.IOException;

@Slf4j
public class RecordDo {
    //文件所在位置
    private final static String FILE_PATH = "data/record.json";

    //更新数据
    public void upData(DataItem dataItem){
        FileDo fileDo = new FileDo(new File(FILE_PATH));
        JSONArray objects = new JSONArray(fileDo.copy());
        for (int i = 0; i < objects.length(); i++){
            //查看这个饰品是否存在于record.json文件中
            if (objects.getJSONObject(i).getString("steam_id").equals(dataItem.getSteam_id())){
                //存在，替换
                //先将旧数据搬走
                JSONObject jsonObject = objects.getJSONObject(i);
                dataToHistory(jsonObject);
                //然后把原来的删除
                objects.remove(i);
                //再把新的加进去
                objects.put(dataItem.toJson());
                fileDo.fugai(objects.toString());
                return;
            }
        }
        //不存在
        //直接添加
        objects.put(dataItem.toJson());
        fileDo.fugai(objects.toString());
    }

    private void dataToHistory(JSONObject json){
        String steam_id = json.getString("steam_id");
        //查看用于存放旧数据的文件是否存在
        File historyFile = new File("data/History/" + steam_id + ".json");
        if (historyFile.exists()){
            //存在，则追加记录
            FileDo fileDo = new FileDo(historyFile);
            JSONArray objects = new JSONArray(fileDo.copy());
            objects.put(json);
            fileDo.fugai(objects.toString());
        }else {
            //不存在，则新建
            try {
                if (historyFile.createNewFile()){
                    //创建成功
                    FileDo fileDo = new FileDo(historyFile);
                    fileDo.fugai("["+ json +"]");
                }else {
                    //创建失败
                    log.error("创建文件"+historyFile.toString()+"失败");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
