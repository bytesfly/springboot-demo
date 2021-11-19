
一些通用工具和依赖。

- [FlexibleThreadPool](https://github.com/bytesfly/springboot-demo/blob/master/springboot-common/src/main/java/com/bytesfly/common/FlexibleThreadPool.java)

可伸缩的线程池，根据当前任务数动态调整`corePoolSize`。  
因为在实际场景中，有时很难准确估算出合理的线程数。  
参考美团技术团队博客而做此实现，详情见： [https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)  

当然，个人更推荐不同的业务处理放到不同的线程池中，避免某一个业务阻塞影响其他不相关的业务。

- [JsonUtil](https://github.com/bytesfly/springboot-demo/blob/master/springboot-common/src/main/java/com/bytesfly/common/JsonUtil.java)

对`jackson`的库简单封装，让序列化、反序列化`json`更方便一点。

- [JwtTool](https://github.com/bytesfly/springboot-demo/blob/master/springboot-common/src/main/java/com/bytesfly/common/JwtTool.java)

`Hutool`从`5.7.0`提供了零依赖的`JWT`（`JSON Web Token`）实现，很轻量级，源码也容易阅读，所以写了个简单的`Demo`(注意：实际的项目还需要根据具体的需求去做身份验证，这里仅是用于演示)，正好 [springboot-jwt-swagger](https://bytesfly.github.io/springboot-demo/#/doc/springboot-jwt-swagger) 模块也需要用到这个工具类。
