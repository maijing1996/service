package com.mj.utils;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mj on 2017/7/28.
 */
@Service
public class ErrorMessageBuilder {

    @Autowired
    private HttpServletRequest request;

    public Map<String, Object> build(int status, ErrorCode error, ErrorMessage message)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timestamp", new Date().getTime());
        map.put("status",status);
        map.put("path",request.getServletPath());
        map.put("error",error);
        map.put("message",message);
        return map;
    }

    public Map<String, Object> build2(int status, String error, String message)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("timestamp", new Date().getTime());
        map.put("status",status);
        map.put("path",request.getServletPath());
        map.put("error",error);
        map.put("message",message);
        return map;
    }
}
