package top.jtning.rpc.entity;

import lombok.Data;
import top.jtning.rpc.enumeration.ResponseCode;

import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable {
    private Integer statusCode;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setStatusCode(ResponseCode.SUCCESS.getCode());

    }
}
