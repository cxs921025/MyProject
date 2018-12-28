package com.cxs;

import com.cxs.core.utils.LogUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:config/dubbo-provider.xml"})
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        LogUtil.debug("程序启动成功！ \\^0^/ ");
    }
}
