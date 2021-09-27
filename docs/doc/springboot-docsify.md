
在`springboot`中使用`docsify`，让项目文档更优雅。  

前面我介绍过用`docsify`搭建个人博客，有兴趣可以访问： [https://bytesfly.github.io/blog](https://bytesfly.github.io/blog)  

搭建非常简单，可参考我之前写过的文章： [使用GitHub Pages + docsify快速搭建一个站点](https://www.cnblogs.com/bytesfly/p/github-pages-docsify.html) 

## 上手体验

启动`springboot-docsify`项目，端口默认是`8080`，浏览器访问： [http://localhost:8080/](http://localhost:8080/) ，效果如下图所示：  
![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210926150010015-298987815.png)
这里是用`hutool`的文档来做演示。

## 简单介绍

`docsify`官方文档：[https://docsify.js.org/#/zh-cn/](https://docsify.js.org/#/zh-cn/)

> `docsify`可以快速帮你生成文档网站。不同于 GitBook、Hexo 的地方是它不会生成静态的 .html 文件，所有转换工作都是在运行时。如果你想要开始使用它，只需要创建一个 index.html 就可以开始编写文档并直接部署在`GitHub Pages`。

所以，你只需要专注写你的项目文档(`Markdown`)。  

使用`Git`+`Markdown`来维护你的项目文档，就像维护代码一样，清晰明了。 这对于只懂后端的程序员非常友好。  

当然，如果你了解前端的话，可以改`css`进一步美化，添加其他`js`插件让网站更加酷炫。

## 快速部署

部署非常简单，`上手体验`就是一种部署方式，可以直接放到`springboot`项目的静态资源目录，启动后端程序就能访问。  

或者也可以部署到比如`GitHub Pages`，参考：[https://docsify.js.org/#/zh-cn/deploy](https://docsify.js.org/#/zh-cn/deploy)

注意：当前站点的内容放在 [https://github.com/bytesfly/springboot-demo](https://github.com/bytesfly/springboot-demo) 的`docs`目录下，所以在设置页面开启`GitHub Pages`功能并选择`master`分支、`/docs`目录，如下：  

![](https://img2020.cnblogs.com/blog/1546632/202109/1546632-20210927100649672-1039112134.png)

