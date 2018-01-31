Java Rest 框架
=================
Spring Boot + Spring Security + MyBaits
-----------------------------

###IDEA 热启动:

```
Setting -->  Compiler  --> Build project automaticolly
Ctrl+Alt+Shift+/ --> Registry --> 勾选compiler.automake.allow.when.app.running 
program argument: mvn spring-boot:run
```

###Request Method
|     url    | method | params |remark|
| :---- | :-----: | :----: |:----|
|<http://host/api/ronaldo/**/{id}>|GET|id|无|
|<http://host/api/ronaldo/**>|GET|name,page,pagesize|pagesize 默认10, 非必传|
|<http://host/api/ronaldo/**>| POST|{}|无|
|<http://host/api/ronaldo/**/{id}>| PUT|{}|无|
|<http://host/api/ronaldo/**/{id}>| DELETE|id|无|


###使用方式
* 通用接口处理 BaseRestController
* 需要处理的接口创建相关 Controller
