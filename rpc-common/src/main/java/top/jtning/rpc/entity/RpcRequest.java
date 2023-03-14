package top.jtning.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The request object sent by the customer to the provider.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
