<p align="center">
	<a href="https://hutool.cn/"><img src="https://cdn.jsdelivr.net/gh/looly/hutool-site/images/logo.jpg" width="45%"></a>
</p>
<p align="center">
	<strong>🍬A set of tools that keep Java sweet.</strong>
</p>
<p align="center">
	👉 <a href="https://hutool.cn">https://hutool.cn/</a> 👈
</p>

<p align="center">
	<a target="_blank" href="https://search.maven.org/artifact/cn.hutool/hutool-all">
		<img src="https://img.shields.io/maven-central/v/cn.hutool/hutool-all.svg?label=Maven%20Central" />
	</a>
	<a target="_blank" href="https://license.coscl.org.cn/MulanPSL2/">
		<img src="https://img.shields.io/:license-MulanPSL2-blue.svg" />
	</a>
	<a target="_blank" href="https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html">
		<img src="https://img.shields.io/badge/JDK-8+-green.svg" />
	</a>
	<a target="_blank" href="https://travis-ci.com/dromara/hutool">
		<img src="https://travis-ci.com/dromara/hutool.svg?branch=v4-master" />
	</a>
	<a href="https://www.codacy.com/gh/dromara/hutool/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=dromara/hutool&amp;utm_campaign=Badge_Grade">
		<img src="https://app.codacy.com/project/badge/Grade/8a6897d9de7440dd9de8804c28d2871d"/>
	</a>
	<a href="https://codecov.io/gh/dromara/hutool">
		<img src="https://codecov.io/gh/dromara/hutool/branch/v5-master/graph/badge.svg" />
	</a>
	<a target="_blank" href="https://gitter.im/hutool/Lobby?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge">
		<img src="https://badges.gitter.im/hutool/Lobby.svg" />
	</a>
	<a target="_blank" href='https://gitee.com/dromara/hutool/stargazers'>
		<img src='https://gitee.com/dromara/hutool/badge/star.svg?theme=gvp' alt='star'/>
	</a>
	<a target="_blank" href='https://github.com/dromara/hutool'>
		<img src="https://img.shields.io/github/stars/dromara/hutool.svg?style=social" alt="github star"/>
	</a>
</p>

<br/>
<p align="center">
	<a href="https://qm.qq.com/cgi-bin/qm/qr?k=jPq9DsjXs7GUWbXRZU3wygSJyMEy4pqr&jump_from=webapi">
	<img src="https://img.shields.io/badge/QQ%E7%BE%A4%E2%91%A5-610134978-orange"/></a>
</p>


-------------------------------------------------------------------------------

## 📚简介
Hutool是一个小而全的Java工具类库，通过静态方法封装，降低相关API的学习成本，提高工作效率，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。

Hutool中的工具方法来自每个用户的精雕细琢，它涵盖了Java开发底层代码中的方方面面，它既是大型项目开发中解决小问题的利器，也是小型项目中的效率担当；

Hutool是项目中“util”包友好的替代，它节省了开发人员对项目中公用类和公用工具方法的封装时间，使开发专注于业务，同时可以最大限度的避免封装不完善带来的bug。

### 🎁Hutool名称的由来

Hutool = Hu + tool，是原公司项目底层代码剥离后的开源库，“Hu”是公司名称的表示，tool表示工具。Hutool谐音“糊涂”，一方面简洁易懂，一方面寓意“难得糊涂”。

### 🍺Hutool如何改变我们的coding方式

Hutool的目标是使用一个工具方法代替一段复杂代码，从而最大限度的避免“复制粘贴”代码的问题，彻底改变我们写代码的方式。

以计算MD5为例：

- 👴【以前】打开搜索引擎 -> 搜“Java MD5加密” -> 打开某篇博客-> 复制粘贴 -> 改改好用
- 👦【现在】引入Hutool  -> SecureUtil.md5()

Hutool的存在就是为了减少代码搜索成本，避免网络上参差不齐的代码出现导致的bug。

-------------------------------------------------------------------------------

## 🛠️包含组件
一个Java基础工具类，对文件、流、加密解密、转码、正则、线程、XML等JDK方法进行封装，组成各种Util工具类，同时提供以下组件：

| 模块                |     介绍                                                                          |
| -------------------|---------------------------------------------------------------------------------- |
| hutool-aop         |     JDK动态代理封装，提供非IOC下的切面支持                                              |
| hutool-bloomFilter |     布隆过滤，提供一些Hash算法的布隆过滤                                                |
| hutool-cache       |     简单缓存实现                                                                     |
| hutool-core        |     核心，包括Bean操作、日期、各种Util等                                               |
| hutool-cron        |     定时任务模块，提供类Crontab表达式的定时任务                                          |
| hutool-crypto      |     加密解密模块，提供对称、非对称和摘要算法封装                                          |
| hutool-db          |     JDBC封装后的数据操作，基于ActiveRecord思想                                         |
| hutool-dfa         |     基于DFA模型的多关键字查找                                                         |
| hutool-extra       |     扩展模块，对第三方封装（模板引擎、邮件、Servlet、二维码、Emoji、FTP、分词等）            |
| hutool-http        |     基于HttpUrlConnection的Http客户端封装                                            |
| hutool-log         |     自动识别日志实现的日志门面                                                         |
| hutool-script      |     脚本执行封装，例如Javascript                                                      |
| hutool-setting     |     功能更强大的Setting配置文件和Properties封装                                        |
| hutool-system      |     系统参数调用封装（JVM信息等）                                                      |
| hutool-json        |     JSON实现                                                                       |
| hutool-captcha     |     图片验证码实现                                                                   |
| hutool-poi         |     针对POI中Excel和Word的封装                                                       |
| hutool-socket      |     基于Java的NIO和AIO的Socket封装                                                   |
| hutool-jwt         |     JSON Web Token (JWT)封装实现                                                    |

可以根据需求对每个模块单独引入，也可以通过引入`hutool-all`方式引入所有模块。