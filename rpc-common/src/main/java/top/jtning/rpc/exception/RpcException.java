package top.jtning.rpc.exception;

import top.jtning.rpc.enumeration.RpcError;

public class RpcException extends RuntimeException {
    public RpcException(RpcError error) {
        super(error.getMessage());
    }

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
