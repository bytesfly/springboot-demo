## 介绍

URL（Uniform Resource Locator）中文名为统一资源定位符，有时也被俗称为网页地址。表示为互联网上的资源，如网页或者FTP地址。在Java中，也可以使用URL表示Classpath中的资源（Resource）地址。

## 方法

### 获取URL对象

- `URLUtil.url` 通过一个字符串形式的URL地址创建对象 
- `URLUtil.getURL` 主要获得ClassPath下资源的URL，方便读取Classpath下的配置文件等信息。

### 其它

- `URLUtil.normalize` 标准化化URL链接。对于不带http://头的地址做简单补全。

```java
String url = "http://www.hutool.cn//aaa/bbb";
// 结果为：http://www.hutool.cn/aaa/bbb
String normalize = URLUtil.normalize(url);

url = "http://www.hutool.cn//aaa/\\bbb?a=1&b=2";
// 结果为：http://www.hutool.cn/aaa/bbb?a=1&b=2
normalize = URLUtil.normalize(url);
```

- `URLUtil.encode` 封装`URLEncoder.encode`，将需要转换的内容（ASCII码形式之外的内容），用十六进制表示法转换出来，并在之前加上%开头。

```java
String body = "366466 - 副本.jpg";
// 结果为：366466%20-%20%E5%89%AF%E6%9C%AC.jpg
String encode = URLUtil.encode(body);
```

- `URLUtil.decode` 封装`URLDecoder.decode`，将%开头的16进制表示的内容解码。
- `URLUtil.getPath` 获得path部分 URI -> http://www.aaa.bbb/search?scope=ccc&q=ddd PATH -> /search
- `URLUtil.toURI` 转URL或URL字符串为URI。
