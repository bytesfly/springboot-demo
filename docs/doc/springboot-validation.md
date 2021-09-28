
在`springboot`中使用`validation`，让参数校验更优雅。

## 快速上手

添加依赖：
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

添加参数校验：
```java
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class CreateReq {

    @NotNull(message = "年龄必填")
    @Min(value = 18, message = "年龄必须18周岁及以上")
    @Max(value = 36, message = "年龄必须36周岁及以下")
    private Integer age;

    @NotBlank(message = "手机号必填")
    @Pattern(regexp = "^1\\d{10}$", message = "手机号必须1开头，且11位")
    private String phone;

    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式不正确")
    @NotSystemEmail(message = "不允许填写系统保留邮箱")
    private String email;

    @NotBlank(message = "密码必填")
    @Length(min = 8, max = 20, message = "密码长度8~20")
    private String password;
}

@RestController
@RequestMapping("/api/validation")
@Validated
public class ValidationController {

    @PostMapping
    public ApiResponse create(@RequestBody @Validated CreateReq req) {
        return ApiResponse.successful();
    }

    @PutMapping
    public ApiResponse update(@RequestBody @Validated UpdateReq req) {
        return ApiResponse.successful();
    }

    @GetMapping
    public ApiResponse get(@RequestParam("id") @NotEqual(invalidValue = "1", message = "ID为1的数据不允许查询") Long id) {
        return ApiResponse.successful();
    }

    @DeleteMapping("{id}")
    public ApiResponse delete(@PathVariable("id") @NotEqual(invalidValue = "1", message = "ID为1的数据不允许删除") Long id) {
        return ApiResponse.successful();
    }
}
```
发送请求，看校验是否生效。

Request:
```bash
curl -X POST "http://localhost:8081/api/validation" \
-H "Content-Type: application/json" \
-d '{
  "age": 0,
  "email": "string",
  "password": "string",
  "phone": "string"
}'
```

Response:
```json
{
    "code":400,
    "msg":"email:邮箱格式不正确;age:年龄必须18周岁及以上;phone:手机号必须1开头，且11位;password:密码长度8~20",
    "total":0,
    "result":null
}
```

可见，参数校验已经生效。注意，这个项目里添加了全局的异常处理器，见 [GlobalExceptionHandler.java](https://github.com/bytesfly/springboot-demo/blob/master/springboot-validation/src/main/java/com/bytesfly/validation/component/GlobalExceptionHandler.java)

## 自定义ConstraintValidator

上面使用了内置的比如`@Email`、`@Pattern`、`@Length`等注解加在参数上，就能起到校验的效果，非常优雅。  

但实际项目的业务逻辑中，现有的注解可能无法满足一些特定的校验规则。此时，就需要我们自定义`ConstraintValidator`。

自定义`ConstraintValidator`也并非很难，我们可以照着已经内置的照葫芦画瓢。  

比如，我这里有个需求，不允许填写系统保留邮箱。我们可以抽象一下，其实就是`NotEqual`的含义，用户的输入不能是某些值。  

于是，我们可以先自定义一个`@NotEqual`的注解。
```java
/**
 * 用户的输入不能是某些值
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Documented
@Constraint(validatedBy = {NotEqualValidator.class})
@Repeatable(NotEqual.List.class)
public @interface NotEqual {

    String invalidValue();

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Defines several {@code @NotEqual} annotations on the same element.
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        NotEqual[] value();
    }
}
```
再实现相应的校验规则：
```java
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEqualValidator implements ConstraintValidator<NotEqual, Object> {

    private String invalidValue;

    @Override
    public void initialize(NotEqual annotation) {
        invalidValue = annotation.invalidValue();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            // 这里允许为null, 如果不允许为null，可以加@NotBlank或者@NotNull注解
            return true;
        }
        return !invalidValue.equals(value.toString());
    }
}
```
此时，这个校验注解就能生效了吗？不妨试试。
```java
@Getter
@Setter
@NoArgsConstructor
public class UpdateReq {

    @NotNull(message = "ID必填")
    @NotEqual(invalidValue = "0", message = "ID为0的数据不允许更新")
    @NotEqual(invalidValue = "1", message = "ID为1的数据不允许更新")
    private Long id;

    @NotBlank(message = "邮箱必填")
    @Email(message = "邮箱格式不正确")
    @NotEqual(invalidValue = "system@github.com", message = "不允许填写系统保留邮箱")
    @NotEqual(invalidValue = "admin@github.com", message = "不允许填写系统保留邮箱")
    private String email;
}
```

Request:
```bash
curl -X PUT "http://localhost:8081/api/validation" \
-H "Content-Type: application/json" \
-d '{
  "id": 1,
  "email": "admin@github.com"
}'
```

Response:
```json
{
  "code": 400,
  "msg": "id:ID为1的数据不允许更新;email:不允许填写系统保留邮箱",
  "total": 0,
  "result": null
}
```
可见，我们自定义的`@NotEqual`生效了。  

上面的参数是在`@RequestBody`中，下面试试`@RequestParam`中的参数校验。

Request:
```bash
curl -X GET "http://localhost:8081/api/validation?id=1"
```
Response:
```json
{
  "code": 400,
  "msg": "ID为1的数据不允许查询",
  "total": 0,
  "result": null
}
```

同理，再试下`@PathVariable`中的参数校验。

Request:
```bash
curl -X DELETE "http://localhost:8081/api/validation/1"
```

Response:
```json
{
    "code":400,
    "msg":"ID为1的数据不允许删除",
    "total":0,
    "result":null
}
```
都是可以的，这里需要注意在`Controller`上已经加了`@Validated`注解。

可能有些读者要问了，如果新增了系统保留邮箱怎么办呢，总不能像上面这样每多一个系统保留邮箱，就加一个`@NotEqual`注解啊。  

同样，我们自定义一个`@NotSystemEmail`注解：
```java
/**
 * 用户的输入不能是系统保留邮箱
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Documented
@Constraint(validatedBy = {NotSystemEmailValidator.class})
public @interface NotSystemEmail {

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```
校验逻辑：
```java
@Service
public class EmailService {

    /**
     * 是否是系统保留邮箱
     */
    public boolean isSystemEmail(String email) {
        // 模拟从缓存或db查询数据
        List<String> systemMails = CollUtil.toList("git@github.com", "root@github.com");
        return systemMails.contains(email);
    }
}

public class NotSystemEmailValidator implements ConstraintValidator<NotSystemEmail, Object> {

    @Autowired
    private EmailService emailService;

    @Override
    public boolean isValid(Object email, ConstraintValidatorContext context) {
        if (email == null) {
            // 这里允许为null, 如果不允许为null，可以加@NotBlank或者@NotNull注解
            return true;
        }
        return !emailService.isSystemEmail(email.toString());
    }
}
```
在前面的 [CreateReq.java](https://github.com/bytesfly/springboot-demo/blob/master/springboot-validation/src/main/java/com/bytesfly/validation/model/request/CreateReq.java) 中加上`@NotSystemEmail`注解：
```java
@NotSystemEmail(message = "不允许填写系统保留邮箱")
private String email;
```
发送`POST`请求：
```bash
curl -X POST "http://localhost:8081/api/validation" \
-H "Content-Type: application/json" \
-d '{
  "age": 20,
  "email": "git@github.com",
  "password": "1234567890",
  "phone": "19612341234"
}'
```
返回结果：
```json
{
    "code":400,
    "msg":"email:不允许填写系统保留邮箱",
    "total":0,
    "result":null
}
```
参数校验成功，实际项目中类似这样的需求还是比较常见的。