package xyz.xingfeng.SteamBalanceServer.Tool;

import java.io.File;

public class SteamCookie {
    public static String STEAMCOOKIE = textSteamCookie();

    public static String textSteamCookie(){
        FileDo fileDo = new FileDo(new File("config/SteamCookie"));
        return fileDo.copy();
    }
}
