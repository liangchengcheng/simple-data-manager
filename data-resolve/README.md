# XDataResolver
轻量的数据 byte 序列化/反序列化工具

## 特点
- 简单易用的 API。`toBytes()`和`fromBytes()`轻松解决 byte 序列化/反序列化
- 高效的处理效率、极小的数据量
- 为 IoT 而生（或是任何对传输数据量有要求的场景）
    - 很多低功耗单片机解析 Json 开销太大，ProtoBuf/FlatBuf 引入成本太高
    - IoT 场景下为了通信效率，需要信息密度更高的字节流协议

## 快速开始

 
```

#### Sample

```java
Foo foo = new Foo();
// 序列化到 bytes
byte[] result = XdataResolver.DEFAULT.toBytes(foo)
// 反序列化到 Object
Foo receivedFoo = XdataResolver.DEFAULT.fromBytes(receivedBytes);
```

```java
/**
 * Model 定义
 */
class Foo implements XdataResolverData {
    @ParamField(order = 0)
    public int value;
}
```

## 应用实例

如下图在 IoT 场景下，客户端和硬件设备之间一条**设置用户信息协议**，协议内容如下
- Header：通用的数据包头部，包含分包标识 flag、协议类型 type、协议数据长度 length、协议数据内容 payload
- Payload：对应此类型协议的用户信息

![](https://raw.githubusercontent.com/BownX/XdataResolver/master/art/protocol.png)

## 详细使用

#### XDataResolver Data

- 实现这个接口的 data class 才允许序列化/反序列化，且实现类必须有**无参构造函数**

##### 支持的字段类型

- 基本类型：byte、boolean、char、short、int、float、long、double
- ByteArray 类型：String、byte[]
- 集合类型：List、Set、非 byte 的 Array
- 嵌套 XdataResolverData 类型

> 一些限制：

- 集合类型的成员类型只能为**基本类型、嵌套 XdataResolverData 类型**（但是不允许定义为 interface 和 abstract）

##### ParamField 注解

- order：定义 field 的顺序，用于所有类型字段
- bytes：定义 field 序列化使用的 byte 长度，可用在基本类型上时可以用于兼容其他平台的数据长度、节约传输数据量；也可用于定长类型用于限制长度
- length：定义 List、Set、数组的长度

> 一些限制：

- order 必须是从 0 递增的连续整数，任意两个 field 的 order 不能相同
- ByteArray 类型必须指定 bytes 值，但是在非嵌套 XdataResolverData 的最大 order 上可以不指定
- 集合类型 必须指定 length，但是在非嵌套 XdataResolverData 的最大 order 的字段上时可以省略

##### 字节序/字符集

- 默认可以直接使用 `XdataResolver.DEFAULT`，如果需要指定字节序或处理 String 时的字符集，可以使用 `XdataResolver(ByteOrder order, Charset charset)` 构造实例

##### 异常处理

- XdataResolverAnnotationException：序列化/反序列化之前会根据上述规则进行检查，违反规则的时候会抛出
- XdataResolverIllegalValueException：序列化的时候会检查实际数据是否满足定义长度，超出定义的 bytes/length 值的时候会抛出
- XdataResolverInternalException：内部错误，设置`XdataResolver.DEBUG = true`时会抛出




```
uintN_t 	bits 	bytes 	对应Java实现
uint8_t 	8 	1 	short
uint16_t 	16 	2 	int
uint32_t 	32 	4 	long
```
## License 

All assets and code are under the [![license](https://img.shields.io/github/license/GarageGames/Torque3D.svg)](https://github.com/Keep-Tech/XdataResolver/blob/master/LICENSE)