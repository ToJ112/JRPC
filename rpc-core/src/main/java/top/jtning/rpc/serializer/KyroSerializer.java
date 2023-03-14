package top.jtning.rpc.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import top.jtning.rpc.entity.RpcRequest;
import top.jtning.rpc.entity.RpcResponse;
import top.jtning.rpc.enumeration.SerializerCode;
import top.jtning.rpc.exception.SerializeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class KyroSerializer implements CommonSerializer{
    private static final Logger logger = LoggerFactory.getLogger(KyroSerializer.class);
    /*
    这里 Kryo 可能存在线程安全问题，文档上是推荐放在 ThreadLocal 里，一个线程一个 Kryo。
    在序列化时，先创建一个 Output 对象（Kryo 框架的概念），
    接着使用 writeObject 方法将对象写入 Output 中，
    最后调用 Output 对象的 toByte() 方法即可获得对象的字节数组。
    反序列化则是从 Input 对象中直接 readObject，这里只需要传入对象的类型，而不需要具体传入每一个属性的类型信息。
     */
    private static final ThreadLocal<Kryo> kyroThrealLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcRequest.class);
        kryo.register(RpcResponse.class);
        /*
        在 Kryo 序列化过程中，如果序列化对象包含了多个引用相同的子对象，Kryo 默认会对这些相同引用的子对象进行多次序列化，导致序列化结果冗长且效率低下。
        为了解决这个问题，可通过调用 kryo.setReferences(true) 方法开启对象引用管理，
        Kryo 会在序列化过程中对相同引用的子对象只序列化一次，并在序列化时记录这些子对象的引用，从而在反序列化时能够正确地恢复对象引用关系。
         */
        kryo.setReferences(true);
        //指定在使用 Kryo 序列化对象时是否需要提前注册对象类型
        kryo.setRegistrationRequired(false);
        return kryo;
    });
    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            Output output = new Output(byteArrayOutputStream);
            Kryo kryo = kyroThrealLocal.get();
            kryo.writeObject(output, obj);
            kyroThrealLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            logger.error("serialize fail:",e);
            throw new SerializeException("serialize fail.");
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = kyroThrealLocal.get();
            Object o = kryo.readObject(input, clazz);
            kyroThrealLocal.remove();
            return o;
        } catch (IOException e) {
            logger.error("deserialize fail: ",e);
            throw new SerializeException("deserialize fail.");
        }
    }

    @Override
    public int getCode() {
        return SerializerCode.KRYO.getCode();
    }
}
