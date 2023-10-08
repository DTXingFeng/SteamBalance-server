package xyz.xingfeng.SteamBalanceServer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.xingfeng.SteamBalanceServer.Initialization.Inspect;

@Slf4j
@SpringBootApplication
public class SteamBalanceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SteamBalanceServerApplication.class, args);
		//开始初始化程序
		log.info("检查必要文件");
		new Inspect();
	}

}
