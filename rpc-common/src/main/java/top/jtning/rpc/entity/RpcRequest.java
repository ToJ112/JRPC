package top.jtning.rpc.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * The request object sent by the customer to the provider.
 */
@Data
@Builder
public class RpcRequest implements Serializable {
    /**
     * Interface name to be called.
     */
    private String interfaceName;
    /**
     * Method name to be called.
     */
    private String methodName;
    /**
     * Parameters for calling method.
     */
    private Object[] parameters;
    /**
     * Parameters types for calling method.
     */
    private Class<?>[] paramTypes;
}
