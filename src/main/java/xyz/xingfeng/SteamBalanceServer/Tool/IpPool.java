package xyz.xingfeng.SteamBalanceServer.Tool;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * 代理池
 */
@Slf4j
public class IpPool {
    String ip;
    String port;
    private static final String url = "http://api.proxy.ipidea.io/getBalanceProxyIp?num=1&return_type=json&lb=1&sb=0&flow=1&regions=&protocol=http";
    //获得新的ip与端口代理
    public IpPool(){
        while (true) {
            try {
                URL url1 = new URL(url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url1.openConnection();
                httpURLConnection.setRequestMethod("GET");
                if (httpURLConnection.getResponseCode() == 200) {
                    //请求成功，获取数据
                    //获取返回流
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        response.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.getInt("code") == 0) {
                        ip = jsonObject.getJSONArray("data").getJSONObject(0).getString("ip");
                        port = String.valueOf(jsonObject.getJSONArray("data").getJSONObject(0).getInt("port"));
                    } else {
                        log.info(jsonObject.toString(1));
                        Thread.sleep(2000);
                        continue;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            break;
        }
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
