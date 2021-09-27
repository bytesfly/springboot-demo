## 由来

数据库操作不外乎四门功课：增删改查，在Java的世界中，由于JDBC的存在，这项工作变得简单易用，但是也并没有做到使用上的简化。于是出现了JPA（Hibernate）、MyBatis、Jfinal、BeetlSQL等解决框架，或解决多数据库差异问题，或解决SQL维护问题。而Hutool对JDBC的封装，多数为在小型项目中对数据处理的简化，尤其只涉及单表操作时。OK，废话不多，来个Demo感受下。

## 使用

我们以MySQL为例

### 1、添加配置文件

Maven项目中在`src/main/resources`目录下添加`db.setting`文件（非Maven项目添加到ClassPath中即可）：

```java
## db.setting文件

url = jdbc:mysql://localhost:3306/test
user = root
pass = 123456

## 可选配置
# 是否在日志中显示执行的SQL
showSql = true
# 是否格式化显示的SQL
formatSql = false
# 是否显示SQL参数
showParams = true
# 打印SQL的日志等级，默认debug，可以是info、warn、error
sqlLevel = debug
```

### 2、引入MySQL JDBC驱动jar

```xml
<!--mysql数据库驱动 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>${mysql.version}</version>
</dependency>
```

> 注意
> 此处不定义MySQL版本，请参考官方文档使用匹配的驱动包版本。

### 3、增删改查

#### 增

```java
Db.use().insert(
    Entity.create("user")
    .set("name", "unitTestUser")
    .set("age", 66)
);
```

插入数据并返回自增主键：

```java
Db.use().insertForGeneratedKey(
    Entity.create("user")
    .set("name", "unitTestUser")
    .set("age", 66)
);
```

#### 删

```java
Db.use().del(
    Entity.create("user").set("name", "unitTestUser")//where条件
);
```

> 注意
> 考虑安全性，使用del方法时不允许使用空的where条件，防止全表删除，如有相关操作需要，请调用execute方法执行SQL实现。

#### 改

```java
Db.use().update(
    Entity.create().set("age", 88), //修改的数据
    Entity.create("user").set("name", "unitTestUser") //where条件
);
```

> 注意
> 条件语句除了可以用`=`精确匹配外，也可以范围条件匹配，例如表示 `age < 12` 可以这样构造Entity：`Entity.create("user").set("age", "< 12")`，但是通过Entity方式传入条件暂时不支持同字段多条件的情况。

#### 查

1. 查询全部字段

```java
//user为表名
Db.use().findAll("user");
```

2. 条件查询

```java
Db.use().findAll(Entity.create("user").set("name", "unitTestUser"));
```

3. 模糊查询

```java
Db.use().findLike("user", "name", "Test", LikeType.Contains);
```

或者：

```java
List<Entity> find = Db.use().find(Entity.create("user").set("name", "like 王%"));
```

4. 分页查询

```java
//Page对象通过传入页码和每页条目数达到分页目的
PageResult<Entity> result = Db.use().page(Entity.create("user").set("age", "> 30"), new Page(10, 20));
```

5. 执行SQL语句

```java
//查询
List<Entity> result = Db.use().query("select * from user where age < ?", 3);
```

```java
//模糊查询
List<Entity> result = Db.use().query("select * from user where name like ?", "王%");
```

```java
//新增
Db.use().execute("insert into user values (?, ?, ?)", "张三", 17, 1);
```

```java
//删除
Db.use().execute("delete from user where name = ?", "张三");
```

```java
//更新
Db.use().execute("update user set age = ? where name = ?", 3, "张三");
```

6. 事务

```java
Db.use().tx(new TxFunc() {
    @Override
    public void call(Db db) throws SQLException {
        db.insert(Entity.create("user").set("name", "unitTestUser"));
        db.update(Entity.create().set("age", 79), Entity.create("user").set("name", "unitTestUser"));
    }
});
```

JDK8中可以用lambda表达式（since：5.x）：

```java
Db.use().tx(db -> {
	db.insert(Entity.create("user").set("name", "unitTestUser2"));
	db.update(Entity.create().set("age", 79), Entity.create("user").set("name", "unitTestUser2"));
});
```

7. 支持命名占位符的SQL执行

有时候使用"?"占位符比较繁琐，且在复杂SQL中很容易出错，Hutool支持使用命名占位符来执行SQL。

```java
Map<String, Object> paramMap = MapUtil.builder("name1", (Object)"张三").put("age", 12).put("subName", "小豆豆").build();
Db.use().query("select * from table where id=@id and name = @name1 and nickName = @subName", paramMap);
```

在Hutool中，占位符支持以下几种形式：

- :name
- ?name
- @name

8. IN查询

我们在执行类似于`select * from user where id in 1,2,3`这类SQL的时候，Hutool封装如下：

```java
List<Entity> results = db.findAll(
    Entity.create("user")
        .set("id", "in 1,2,3"));
```

当然你也可以直接：

```java
List<Entity> results = db.findAll(
    Entity.create("user")
        .set("id", new long[]{1, 2, 3}));
```