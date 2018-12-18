package com.demon.client.parame;

/**
 * Created by wangpengpeng on 2018/12/13.
 */
public class ResponseUtil {
    public static Response createFailResponse(String code, String msg) {
        Response response = new Response();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static Response createSuccessResponse(Object result) {
        Response response = new Response();
        response.setResult(result);
        return response;
    }
}
