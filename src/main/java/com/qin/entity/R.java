package com.qin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    int code;
    String message;
    T data;

    public R(int code){
        this.code = code;
    }

    public static <T> R ok(){
        R r = new R(200, "成功！", null);
        return r;
    }

    public static <T> R ok(T t){
        R r = new R(200, "成功！", t);
        return r;
    }

    public static <T> R no(String message, T t){
        R r = new R(500, message, t);
        return r;
    }
    public static <T> R no(T t){
        R r = new R(500, "失败！", t);
        return r;
    }

    public static <T> R ok(String message, T t) {
        R r = new R(200, message, t);
        return r;
    }
}
