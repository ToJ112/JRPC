package top.jtning.rpc.serializer;

public interface CommonSerializer {

    //序列化方法，把指定对象序列化成字节数组
    byte[] serialize(Object obj);

    //反序列化方法，将字节数组反序列化成指定Class类型
    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();
    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;
    Integer PROTOBUF_SERIALIZER = 3;
    Integer DEFAULT_SERIALIZER = KRYO_SERIALIZER;
    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KyroSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            case 3:
                return new ProtobufSerializer();
            default:
                return null;
        }
    }
}
