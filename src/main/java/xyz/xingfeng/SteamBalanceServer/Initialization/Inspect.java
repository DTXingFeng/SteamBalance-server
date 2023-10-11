package xyz.xingfeng.SteamBalanceServer.Initialization;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class Inspect {
    public Inspect(){
        log.info("检查'SteamCookie'文件是否存在");
        File file = new File("config/SteamCookie");
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
                log.error("创建文件”SteamCookie”时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        log.info("检查'BuffCookie'文件是否存在");
        file = new File("config/BuffCookie");
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
                log.error("创建文件”BuffCookie”时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        log.info("检查“dotaItem.json”文件与”csItem.json“是否存在");
        file = new File("config/dotaItems.json");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("{\"data\":[]}");
                    fileWriter.close();
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”dotaItems.json“时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        file = new File("config/csItems.json");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("{\"data\":[]}");
                    fileWriter.close();
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”csItems.json“时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        file = new File("config/SleepSecond.json");
        if (!file.exists()){
            //不存在
            log.info("文件不存在，进行初始化");
            try {
                if (file.createNewFile()){
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("{\n" +
                            " \"warn_start\": 1,\n" +
                            " \"warn_end\": 1,\n" +
                            " \"normal_start\": 1,\n" +
                            " \"normal_end\": 1\n" +
                            "}");
                    fileWriter.close();
                    log.info("创建成功");
                }else {
                    log.error("创建失败");
                }
            } catch (IOException e) {
                log.error("创建文件”SleepSecond.json“时发生了未知错误”");
                throw new RuntimeException(e);
            }
        }
        log.info("初始化完成");
    }
}
