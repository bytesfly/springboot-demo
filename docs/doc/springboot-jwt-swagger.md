
`springboot`加了`JWT`验证，如何在`swagger-ui`上访问后端接口呢？

## 界面预览

启动`springboot-jwt-swagger`项目，端口默认是`8082`，浏览器访问： [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929161113646-576819220.png)

此时调用一个接口看看情况（或者直接在`swagger-ui`上点一个接口）：
```bash
curl -X GET "http://localhost:8082/api/jwt/me"
```
接口返回：
```json
{
    "code":401,
    "msg":"身份验证失败",
    "total":0,
    "result":null
}
```
看来接口成功加了身份验证，此时如何在`swagger`界面上方便地测试后端呢？  

## 动手操作

- 第一步：登录获取token
  
![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929100204711-608928403.png)

点击上图中划线的登录接口，填写`uid`和`password`，注意：这里模拟登录，`password`的值只要是`uid`的值后面拼接上`0123`即可登录成功。  
举例来说： `{"uid":"admin","password":"admin0123"}`或者`{"uid":"bytesfly","password":"bytesfly0123"}`都是可以的。

或者命令行直接调用如下：
```bash
curl -X POST "http://localhost:8082/api/jwt/login" \
-H "Content-Type: application/json" \
-d '{
    "uid":"admin",
    "password":"admin0123"
}'
```
接口返回：
```json
{
    "code":200,
    "msg":"OK",
    "total":0,
    "result":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzI5NjcxMDYzNDgsInVpZCI6ImFkbWluIn0.LJSMaiRhn2nmALuH-SS9DuNKpRu9UZqvJPC70oHuc00"
}
```
其中`result`里的值就是登录接口返回的`token`。  

- 第二步：在`swagger-ui`上填写`token`

![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929101448406-994164705.png)  
![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929101629833-1395137849.png)  
![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929102102499-1485862031.png)

- 第三步：在`swagger-ui`上访问其他后端接口

此时，需要身份验证的接口在`swagger-ui`上已经可以直接调用了。可以看到，调用时自动带上了`Authorization`的`Header`，即：
```bash
curl -X GET "http://localhost:8082/api/jwt/me" \
-H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MzI5NjcxMDYzNDgsInVpZCI6ImFkbWluIn0.LJSMaiRhn2nmALuH-SS9DuNKpRu9UZqvJPC70oHuc00"
```
注意：操作过程不要刷新 `http://localhost:8082/swagger-ui/index.html` ，如果后端接口有更新必须刷新，那就需要重新在`swagger-ui`上填写`token`。  

另外，操作过程中不知道你是否注意到每个接口下都多出了一个`App-Name`的`Header`，这是由于项目中可能需要设置自己的`Http Header`，在`swagger-ui`上显示出来是方便你平时测试用的。

## 实现细节

引入依赖:
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

详情见: [SwaggerConfig.java](https://github.com/bytesfly/springboot-demo/blob/master/springboot-jwt-swagger/src/main/java/com/bytesfly/jwt/config/SwaggerConfig.java)  

注意： 线上生产环境建议关闭`swagger-ui`
```yaml
springfox:
  documentation:
    enabled: true
    swagger-ui:
      enabled: true
      # 默认/swagger-ui/index.html
      base-url: ""
```

## 生成离线Html接口文档

之前我写过一篇 [Swagger接口如何生成Html离线文档](https://www.cnblogs.com/bytesfly/p/swagger-to-html.html)  

既然这里与swagger相关，也顺带提一下。  

注意： 此时应该把 [SwaggerConfig.java](https://github.com/bytesfly/springboot-demo/blob/master/springboot-jwt-swagger/src/main/java/com/bytesfly/jwt/config/SwaggerConfig.java) 中的`@Configuration`临时注释掉。即：
```java
//@Configuration
@ConditionalOnExpression("${springfox.documentation.enabled}")
public class SwaggerConfig {
    
}
```

这样，重启`springboot-jwt-swagger`项目， 确保 [http://localhost:8082/v2/api-docs](http://localhost:8082/v2/api-docs) 能访问的通。

然后执行下面的命令：
```bash
# 下载Swagger2Html工具源码
git clone https://github.com/bytesfly/Swagger2Html.git

# 编译打包
mvn clean package

cd target

# 使用target目录下的jar包, 参数是swagger接口数据
java -jar Swagger2Html-1.0-SNAPSHOT-jar-with-dependencies.jar http://localhost:8082/v2/api-docs
```
此时，就能看到当前目录下有`api.html`文件，浏览器打开效果如下：

![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210929161510250-975780171.png)
