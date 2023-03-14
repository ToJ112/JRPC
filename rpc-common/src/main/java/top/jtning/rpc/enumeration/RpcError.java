package top.jtning.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RpcError {
    CLIENT_CONNECT_SERVER_FAILURE("Cannot connect to server"),
    SERVICE_INVOCATION_FAILURE("Service Invocation Failed"),
    SERVICE_NOT_FOUND("Service Not Found"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("Service Not Implement Any Interface"),
    UNKNOWN_PROTOCOL("Unrecognized Protocol Package."),
    UNKNOWN_SERIALIZER("Unrecognized (De)serializer."),
    UNKNOWN_PACKAGE_TYPE("Unrecognized Package Type"),
    SERIALIZER_NOT_FOUND("Cannot Find Serializer"),
    RESPONSE_NOT_MATCH("Response not match request id");
    private final String message;
}
