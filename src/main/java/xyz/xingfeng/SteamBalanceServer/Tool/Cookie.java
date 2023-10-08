package xyz.xingfeng.SteamBalanceServer.Tool;

import java.io.File;

public class Cookie {
    public static String steamCookie = textSteamCookie();

    public static String textSteamCookie(){
        FileDo fileDo = new FileDo(new File("config/cookie"));
        return fileDo.copy();
    }
}
