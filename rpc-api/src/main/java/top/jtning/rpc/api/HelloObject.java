package top.jtning.rpc.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@ToString
public class HelloObject implements Serializable {
    private Integer id;
    private String message;
}
