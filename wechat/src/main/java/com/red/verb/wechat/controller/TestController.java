package com.red.verb.wechat.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * test
 * <pre>
 *  Version         Date            Author          Description
 * ------------------------------------------------------------
 *  1.0.0           2019/05/09     red        -
 * </pre>
 *
 * @author redli
 * @version 1.0.0 2019-05-09 21:39
 * @date 2019-05-09 21:39
 * @since 1.0.0
 */
@EnableSwagger2
@RestController
@Api(value = "Swagger Test Control", description = "演示Swagger用法的Control类", tags = "Swagger Test Control Tag")
public class TestController {
    // 方法的说明
    @ApiOperation(value = "根据id获取记录", produces = "application/json")
    // 定义请求参数
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "id", value = "主键", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "服务器内部异常"),
            @ApiResponse(code = 500, message = "权限不足") })
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public String queryById(String id) {
        System.out.println("queryById id = " + id);
        return "test";
    }
}
