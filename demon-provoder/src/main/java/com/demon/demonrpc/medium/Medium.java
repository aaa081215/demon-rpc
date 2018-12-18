package com.demon.demonrpc.medium;

import com.alibaba.fastjson.JSONObject;
import com.demon.demonrpc.handler.domain.ServerRequest;
import com.demon.demonrpc.util.Response;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangpengpeng on 2018/12/12.
 */
public class Medium {
    public static Map<String, BeamMethod> beamMap;
    //单例模式
    private static Medium m = null;

    static {
        beamMap = new HashMap<String, BeamMethod>();
    }

    private Medium() {

    }

    public static Medium newInstance() {
        if (m == null) {
            m = new Medium();
        }
        return m;
    }

    //    private volatile static Medium m;
//    private Medium(){
//
//    }
//    public static Medium newInstance() {
//        if(m==null){
//            synchronized (Medium.class){
//                if(m==null){
//                    m=new Medium();
//                }
//            }
//        }
//        return m;
//    }
    //反射
    public Object process(ServerRequest request) {
        Response result = null;
        String command = request.getCommand();  //com.com.user.remote.UserRemote.saveUser
        BeamMethod beamMethod = beamMap.get(command);
        if (beamMethod == null) {
            return null;
        }
        Object bean = beamMethod.getBean();
        Method m = beamMethod.getM();
        Class paramType = m.getParameterTypes()[0];
        Object content = request.getContent();
        Object args = JSONObject.parseObject(JSONObject.toJSONString(content), paramType);
        try {
            result = (Response) m.invoke(bean, args);
            result.setId(request.getId());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }
}
