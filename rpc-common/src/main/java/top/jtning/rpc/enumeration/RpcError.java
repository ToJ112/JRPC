package top.jtning.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcError {
    SERVICE_INVOCATION_FAILURE("Service Invocation Failed"),
    SERVICE_NOT_FOUND("Service Not Found"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("Service Not Implement Any Interface");

    private final String message;
}
