# README


## 组件说明

这是一个自动集成[sensitive-plus](https://gitee.com/cchanghui/sensitive-plus)插件的Spring Boot Starter组件，用于在Spring Boot项目中实现接口返回JSON数据的自动脱敏处理。


## 如何使用

第一步，下载[sensitive-plus](https://gitee.com/cchanghui/sensitive-plus)源码，执行`mvn clean install`安装到本地Maven仓库。
第二步，下载[sensitive-spring-boot-starter](https://github.com/nuccch/sensitive-spring-boot-starter.git)源码，执行`mvn clean install`安装到本地Maven仓库。
第三步，在Spring Boot项目中引入相关依赖配置：
```xml
<dependencies>
    <dependency>
        <groupId>com.yhq</groupId>
        <artifactId>sensitive-plus</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>

    <dependency>
        <groupId>org.chench.extra.spring.boot</groupId>
        <artifactId>sensitive-spring-boot-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
```

如果希望接口返回JSON数据时实现对象属性自动脱敏，需要在实体对象属性上应用相应注解。
```java
// 在username和password应用脱敏注解实现接口返回JSOn数据时自动脱敏
public class Account {
    private Long id = 0L;
    @SensitiveLengthChineseName
    private String username = "";
    @SensitiveLengthPassword
    private String password = "";
    private Integer age = 0;
    private Short sex = 0;
    private Date createTime = null;
    private Date updateTime = null;
}
```

接口响应脱敏数据示例：
```json
{
  "id": 1,
  "username": "张**",
  "password": "******",
  "age": 23,
  "sex": 1,
  "createTime": null,
  "updateTime": null
}
```

如果希望关闭脱敏自动处理，可以通过参数`sensitive.mode=false`设置。
yaml格式配置如下：
```yml
sensitive:
    mode: false
```

