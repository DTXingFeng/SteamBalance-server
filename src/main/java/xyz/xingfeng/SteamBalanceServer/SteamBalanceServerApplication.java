package xyz.xingfeng.SteamBalanceServer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SteamBalanceServerApplication {

	public static void main(String[] args) {
		//开始初始化程序
		log.info("检查必要文件");

		SpringApplication.run(SteamBalanceServerApplication.class, args);
	}

}
