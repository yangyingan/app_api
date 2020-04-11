package com.dm.api.test;

import com.dm.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class TestApi {
    @GetMapping("/test/v1")
    public Result test(HttpServletRequest request){
        String remoteAddr=request.getLocalAddr() + ":" + request.getLocalPort();
        return Result.success("查询成功！", remoteAddr);
    }
}
