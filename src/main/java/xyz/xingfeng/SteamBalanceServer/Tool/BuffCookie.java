package xyz.xingfeng.SteamBalanceServer.Tool;

import java.io.File;

public class BuffCookie {
    public static String BUFFCOOKIE = textSteamCookie();

    public static String textSteamCookie(){
        FileDo fileDo = new FileDo(new File("config/BuffCookie"));
        return fileDo.copy();
    }
}
