package xyz.xingfeng.SteamBalanceServer.Controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.xingfeng.SteamBalanceServer.Tool.FileDo;
import xyz.xingfeng.SteamBalanceServer.lang.SleepSecond;
import xyz.xingfeng.SteamBalanceServer.practice.CsPractice;
import xyz.xingfeng.SteamBalanceServer.practice.DotaPractice;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/run")
public class RunController {
    private static volatile boolean csIsRunning = false;
    private static volatile boolean dotaIsRunning = false;
    private Thread DotaThread;
    private Thread CsThread;

    @PostMapping(value = "/RunTheDota", produces = "application/json; charset=UTF-8")
    public Result runTheDota(){
        if (DotaThread != null) {
            if (!DotaThread.isInterrupted()) {
                //在运行中不允许再次启动
                return new Result(Code.OK, "程序已在运行状态");
            }
        }
        DotaThread = new Thread(() -> {
            while (true) {
                try {
                    // 执行可能抛出异常的任务
                    new DotaPractice();
                } catch (Exception e) {
                    log.error("任务发生异常: " + e.getMessage());
                    break;
                }
            }
        });
        DotaThread.start();
        log.info("收到指令启动dota爬虫");
        return new Result(Code.OK,"运行成功");
    }

    @PostMapping(value = "/stopDota", produces = "application/json; charset=UTF-8")
    public Result stopDota(){
        if (DotaThread == null){
            //线程为空
            return new Result(Code.OK,"线程已是中断状态");
        }
        if(!DotaThread.isInterrupted()){
            DotaThread.interrupt();
            return new Result(Code.OK,"中断成功");
        }
        return new Result(Code.OK,"线程已是中断状态");
    }

    @PostMapping(value = "/isRunDota", produces = "application/json; charset=UTF-8")
    public Result isRunDota(){
        if (DotaThread == null){
            return new Result(Code.OK,false,"程序未启动");
        }
        boolean alive = DotaThread.isInterrupted();
        return new Result(Code.OK,!alive,alive?"未运行":"运行中");
    }


    @PostMapping(value = "/RunTheCs", produces = "application/json; charset=UTF-8")
    public Result runTheCs(){
        if (CsThread != null) {
            if (!CsThread.isInterrupted()) {
                //在运行中不允许再次启动
                return new Result(Code.OK, "程序已在运行状态");
            }
        }
        CsThread = new Thread(() -> {
            while (true) {
                try {
                    // 执行可能抛出异常的任务
                    new CsPractice();
                }catch (IOException e){
                    log.error("任务发生异常: " + e.getMessage());
                }catch (Exception e) {
                    log.error("任务发生异常: " + e.getMessage());
                    break;
                }
            }
        });
        CsThread.start();
        log.info("收到指令启动Cs爬虫");
        return new Result(Code.OK,"运行成功");
    }

    @PostMapping(value = "/stopCs", produces = "application/json; charset=UTF-8")
    public Result stopCs(){
        if (CsThread == null){
            //线程为空
            return new Result(Code.OK,"线程已是中断状态");
        }
        if(!CsThread.isInterrupted()){
            CsThread.interrupt();
            return new Result(Code.OK,"中断成功");
        }
        return new Result(Code.OK,"线程已是中断状态");
    }

    @PostMapping(value = "/isRunCs", produces = "application/json; charset=UTF-8")
    public Result isRunCs(){
        if (CsThread == null){
            return new Result(Code.OK,false,"程序未启动");
        }
        boolean alive = CsThread.isInterrupted();
        return new Result(Code.OK,!alive,alive?"未运行":"运行中");
    }

    //更新cookie
    @PostMapping(value = "/updataBuff", produces = "application/json; charset=UTF-8")
    public Result updataBuff(@RequestBody String json){
        FileDo fileDo = new FileDo(new File("config/BuffCookie"));
        fileDo.fugai(json);
        return new Result(Code.OK,"更新成功");
    }

    @PostMapping(value = "/updataSteam", produces = "application/json; charset=UTF-8")
    public Result updataSteam(@RequestBody String json){
        FileDo fileDo = new FileDo(new File("config/SteamCookie"));
        fileDo.fugai(json);
        return new Result(Code.OK,"更新成功");
    }

    @PostMapping(value = "/updataSleepSecond", produces = "application/json; charset=UTF-8")
    public Result updataSleepSecond(@RequestBody SleepSecond sleepSecond){
        JSONObject jsonObject = new JSONObject(sleepSecond);
        FileDo fileDo = new FileDo(new File("config/SleepSecond.json"));
        fileDo.fugai(jsonObject.toString());
        return new Result(Code.OK,"成功");
    }


}
