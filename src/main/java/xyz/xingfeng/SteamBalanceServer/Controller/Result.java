package xyz.xingfeng.SteamBalanceServer.Controller;

import lombok.Data;

@Data
public class Result {
    private int code;
    private Object data;
    private String msg;

    public Result(int code, Object data){
        this.code = code;
        this.data = data;
    }

    public Result(int code, Object data, String msg){
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
}
