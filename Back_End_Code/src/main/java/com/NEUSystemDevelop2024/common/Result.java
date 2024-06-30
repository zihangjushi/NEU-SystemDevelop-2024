package com.NEUSystemDevelop2024.common;

/**
 * 返回类型包装类
 * @param <T>
 */
public class Result<T> {
    private String code;
    private String msg;
    private T data;
    private String errorDetailMsg;
    private Integer httpCode;

    public String getErrorDetailMsg() {
        return errorDetailMsg;
    }

    public void setErrorDetailMsg(String errorDetailMsg) {
        this.errorDetailMsg = errorDetailMsg;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        result.setHttpCode(Constants.CODE_200);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        result.setData(data);
        result.setHttpCode(Constants.CODE_200);
        return result;
    }

    public static Result<String> successWithToken(String msg, String token) {
        Result<String> result = new Result<>();
        result.setCode("0");
        result.setMsg(msg);
        result.setData(token);
        result.setHttpCode(Constants.CODE_200);
        return result;
    }
    public static Result error(String code, String msg,int httpCode) {
        Result result = new Result();
        result.setCode(code);
        result.setErrorDetailMsg(msg);
        result.setHttpCode(httpCode);
        return result;
    }

}
