package xyz.xingfeng.SteamBalanceServer.Tool;

import org.json.JSONObject;

import java.io.File;

public class SleepTime {
    private static JSONObject jsonObject = new JSONObject(new FileDo(new File("config/SleepSecond.json")).copy());
    private static final int WARN_START = getWarn_start();
    private static final int WARN_END = getWarn_end();
    private static final int NORMAL_START = getNormal_start();
    private static final int NORMAL_END = getNormal_end();

    public static int getWarn_start() {
        return jsonObject.getInt("warn_start");
    }

    public static int getWarn_end() {
        return jsonObject.getInt("warn_end");
    }

    public static int getNormal_start() {
        return jsonObject.getInt("normal_start");
    }

    public static int getNormal_end() {
        return jsonObject.getInt("normal_end");
    }

    public static int normalRandomTime(){
        return (int)(NORMAL_START + Math.random() * (NORMAL_END - NORMAL_START));
    }

    public static int wranRandomTime(){
        return (int)(WARN_START + Math.random() * (WARN_END - WARN_START));
    }
}
