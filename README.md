#Java Rest 框架
```
Spring Boot + Spring Security + MyBaits
```

###IDEA 热启动:

```
1、Setting -->  Compiler  --> Build project automaticolly
2、Ctrl+Alt+Shift+/ --> Registry --> 勾选compiler.automake.allow.when.app.running 
3、program argument: mvn spring-boot:run
```

###Request Method
* URL 
```
http://host/api/ronaldo/api
```
* GET
```
url/{id}
```
```
url?name=rtf&gender=男
```

* POST
```
url:url
params:{}
```

* PUT
```
url:url/{id}
params:{}
```

* DELETE
```
url:url/{id}
```

###使用方式
* 通用接口处理 BaseRestController
* 需要处理的接口创建相关 Controller
