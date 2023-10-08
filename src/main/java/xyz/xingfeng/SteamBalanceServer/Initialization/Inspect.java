package xyz.xingfeng.SteamBalanceServer.Initialization;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class Inspect {
    public Inspect(){
        log.info("检查'cookie'文件是否存在");
        File file = new File("config/cookie");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”cookie”时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        log.info("检查“dotaItem.json”文件与”csItem.json“是否存在");
        file = new File("config/dotaItem.json");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”dotaItem.json“时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        file = new File("config/csItem.json");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”csItem.json“时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
    }
}
