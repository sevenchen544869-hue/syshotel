package com.example.web.tools;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.example.web.tools.dto.ResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
/**
 * 全局响应处理切面
 */
@ControllerAdvice
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {



    @Value("${server.port:7245}")
    private String serverPort;

    @Value("${server.ip:http://localhost:7245}")
    private String serverIp;

    public GlobalResponseAdvice() {
    }

    /**
     * 是否开启支持
     *
     * @param returnType    返回的类型
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
 /**
     * 对写入body之前进行拦截拦截处理
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        Object result;
        if (body == null) {
            result = ResponseData.OfSuccess();
        } else if (body instanceof ResponseData<?>) {
            result = body;
        } else {
            result = ResponseData.GetResponseDataInstance(body, "成功", true);
        }

        // 处理响应内容中的URL替换（使用反射遍历）
        if (result instanceof ResponseData<?>) {
            try {
                ResponseData<?> responseData = (ResponseData<?>) result;
                Object data = responseData.getData();
                if (data != null) {
                    replaceUrlInObject(data, new HashSet<>());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 递归遍历对象，替换字符串字段中的URL
     */
    private void replaceUrlInObject(Object obj, Set<Object> visited) {
        if (obj == null || visited.contains(obj)) {
            return;
        }
        
        // 避免循环引用
        if (!isSimpleType(obj.getClass())) {
            visited.add(obj);
        }

        String oldUrl = "http://localhost:" + serverPort + "/";

        // 处理集合类型
        if (obj instanceof Collection<?>) {
            for (Object item : (Collection<?>) obj) {
                replaceUrlInObject(item, visited);
            }
            return;
        }

        // 处理Map类型
        if (obj instanceof Map<?, ?>) {
            for (Object value : ((Map<?, ?>) obj).values()) {
                replaceUrlInObject(value, visited);
            }
            return;
        }

        // 处理数组类型
        if (obj.getClass().isArray()) {
            if (obj instanceof Object[]) {
                for (Object item : (Object[]) obj) {
                    replaceUrlInObject(item, visited);
                }
            }
            return;
        }

        // 跳过基本类型和常见不可变类型
        if (isSimpleType(obj.getClass())) {
            return;
        }

        // 遍历对象的所有字段
        Class<?> clazz = obj.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(obj);

                    if (fieldValue == null) {
                        continue;
                    }

                    // 如果是String类型，直接替换
                    if (fieldValue instanceof String) {
                        String strValue = (String) fieldValue;
                        if (strValue.contains(oldUrl)) {
                            String newValue = strValue.replace(oldUrl, serverIp + "/");
                            field.set(obj, newValue);
                        }
                    } else {
                        // 递归处理其他对象
                        replaceUrlInObject(fieldValue, visited);
                    }
                } catch (Exception e) {
                    // 忽略无法访问的字段
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

    /**
     * 判断是否为简单类型（不需要递归遍历）
     */
    private boolean isSimpleType(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == String.class
                || Number.class.isAssignableFrom(clazz)
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz.isEnum();
    }

}