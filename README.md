# 项目目的

为了减少注释和swagger注解的重复定义, 通过规范注释,
让swagger可以通过javadoc来产生

做一个小插件, 将指定目录下的java注释转换为javadoc.json

# 上手指南

会用maven就行:D

# 安装要求

## jdk1.8

## 类代码注释规范

**注: 插件与注释配套, 注释格式@部分需要严格按照这个来,
否则可能丢失属性**

``` example
/**
 * @Title: PersonController
 * @Package: com/example/controller/PersonController.java
 * @Description: 用户信息接口
 * @author: zhaozhiwei
 * @date: 2022/10/25 下午8:23
 * @version: V1.0
 */

```

## 方法代码注释规范

**注: 插件与注释配套, 注释格式@部分需要严格按照这个来,
否则可能丢失属性**

``` example
/**
 * @date: 2022/10/25-上午10:19
 * @author: zhaozhiwei
 * @method: findByID
  * @param id : 唯一id
 * @return: com.example.domain.Person
 * @Description: 根据id获取用户信息
 * 获取十次， 只有第一次是读库，后续都是取缓存
 * 直接删掉redis缓存里的内容，仍然可以获取数据，并且走缓存，此时获取的是服务缓存ehcache中的信息
 * seq 10 |xargs -i curl -XGET 'http://localhost:8080/persons/2'
 */

```

# 安装步骤

## 构建一个自己的项目

### 参考

## 引入javadoc-json-maven-plugin插件

``` example
            <plugin>
                <groupId>com.example</groupId>
                <artifactId>javadoc-json-maven-plugin</artifactId>
                <version>2.0-SNAPSHOT</version>
                <configuration>
<!--            指定java注释扫描目录,  *根据实际情况自行调整* -->
                    <basePackage>com/example/web/rest</basePackage>
                </configuration>
            </plugin>

```

### 注:可下载代码自行打包安装

插件项目根目录下执行

mvn clean install

## 执行插件

idea双击javadoc-json:generate就会执行这个插件。

执行完毕后，可以看到项目根目录的target文件下多了个javadoc.json文件。

## 结果示例

```
{
  "com.example.web.rest.PersonResource.findByID(java.lang.Long)#date": "2022/10/25-上午10:19",
  "com.example.web.rest.Example1Resource#Title": "MapController",
  "com.example.web.rest.Example1Resource.update(java.util.Map)#method": "update",
  "com.example.web.rest.Example2Resource.findByID(java.lang.Long)#method": "findByID",
  "com.example.web.rest.PersonResource#Package": "com/example/springbootcache/controller/PersonController.java",
  "com.example.web.rest.Example1Resource.findByID(java.lang.Long)#Description": "根据id获取示例1信息",
  "com.example.web.rest.Example2Resource.save(java.util.Map)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource#version": "V1.0",
  "com.example.web.rest.PersonResource.save(Person)#date": "2022/10/25-上午10:14",
  "com.example.web.rest.Example1Resource.save(java.util.Map)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource.update(java.util.Map)#date": "2022/10/25-上午10:21",
  "com.example.web.rest.PersonResource.deleteByID(java.lang.Long)#method": "deleteByID",
  "com.example.web.rest.PersonResource.findByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource.save(java.util.Map)#Description": "示例2保存方法",
  "com.example.web.rest.PersonResource.findByID(java.lang.Long)#method": "findByID",
  "com.example.web.rest.Example1Resource.findByID(java.lang.Long)#method": "findByID",
  "com.example.web.rest.Example2Resource.save(java.util.Map)#date": "2022/10/25-上午10:14",
  "com.example.web.rest.Example1Resource.update(java.util.Map)#Description": "示例1修改内容",
  "com.example.web.rest.Example2Resource#Title": "MapController",
  "com.example.web.rest.PersonResource#version": "V1.0",
  "com.example.web.rest.Example1Resource#Package": "com/example/springbootcache/controller/MapController.java",
  "com.example.web.rest.Example2Resource.update(java.util.Map)#Description": "示例2修改内容",
  "com.example.web.rest.PersonResource.deleteByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.PersonResource.save(Person)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource.findByID(java.lang.Long)#Description": "根据id获取示例2信息",
  "com.example.web.rest.PersonResource.deleteByID(java.lang.Long)#date": "2022/10/25-上午10:25",
  "com.example.web.rest.Example1Resource.update(java.util.Map)#date": "2022/10/25-上午10:21",
  "com.example.web.rest.Example1Resource#version": "V1.0",
  "com.example.web.rest.Example1Resource.save(java.util.Map)#method": "save",
  "com.example.web.rest.Example2Resource#Description": "示例2信息接口",
  "com.example.web.rest.Example2Resource.findByID(java.lang.Long)#date": "2022/10/25-上午10:19",
  "com.example.web.rest.PersonResource.update(Person)#author": "zhaozhiwei",
  "com.example.web.rest.PersonResource.update(Person)#method": "update",
  "com.example.web.rest.Example2Resource.findByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.Example1Resource.update(java.util.Map)#author": "zhaozhiwei",
  "com.example.web.rest.Example1Resource.save(java.util.Map)#Description": "示例1保存方法",
  "com.example.web.rest.PersonResource.findByID(java.lang.Long)#Description": "根据id获取用户信息",
  "com.example.web.rest.PersonResource.save(Person)#method": "save",
  "com.example.web.rest.PersonResource#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource#date": "2022/10/25 下午8:23",
  "com.example.web.rest.Example1Resource#Description": "示例1信息接口",
  "com.example.web.rest.PersonResource.deleteByID(java.lang.Long)#Description": "根据id删除person信息",
  "com.example.web.rest.PersonResource#Title": "PersonController",
  "com.example.web.rest.Example1Resource.deleteByID(java.lang.Long)#date": "2022/10/25-上午10:25",
  "com.example.web.rest.Example2Resource.save(java.util.Map)#method": "save",
  "com.example.web.rest.Example2Resource#Package": "com/example/springbootcache/controller/MapController.java",
  "com.example.web.rest.Example1Resource.deleteByID(java.lang.Long)#Description": "根据id删除示例1信息",
  "com.example.web.rest.Example2Resource#author": "zhaozhiwei",
  "com.example.web.rest.PersonResource.update(Person)#date": "2022/10/25-上午10:21",
  "com.example.web.rest.Example2Resource.deleteByID(java.lang.Long)#Description": "根据id删除示例2信息",
  "com.example.web.rest.Example1Resource.findByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.Example1Resource.save(java.util.Map)#date": "2022/10/25-上午10:14",
  "com.example.web.rest.Example1Resource#date": "2022/10/25 下午8:23",
  "com.example.web.rest.Example2Resource.deleteByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.Example1Resource.deleteByID(java.lang.Long)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource.update(java.util.Map)#author": "zhaozhiwei",
  "com.example.web.rest.Example2Resource.update(java.util.Map)#method": "update",
  "com.example.web.rest.PersonResource.save(Person)#Description": "保存方法",
  "com.example.web.rest.Example2Resource.deleteByID(java.lang.Long)#date": "2022/10/25-上午10:25",
  "com.example.web.rest.PersonResource#Description": "用户信息接口",
  "com.example.web.rest.PersonResource#date": "2022/10/25 下午8:23",
  "com.example.web.rest.PersonResource.update(Person)#Description": "修改内容看是否会调整缓存",
  "com.example.web.rest.Example1Resource#author": "zhaozhiwei",
  "com.example.web.rest.Example1Resource.findByID(java.lang.Long)#date": "2022/10/25-上午10:19",
  "com.example.web.rest.Example1Resource.deleteByID(java.lang.Long)#method": "deleteByID",
  "com.example.web.rest.Example2Resource.deleteByID(java.lang.Long)#method": "deleteByID"
}

```

# 测试

## swagger扩展解析javadoc.json文件示例

<https://github.com/zhaozhiwei1992/demo/tree/master/springboot/springboot-swagger-javadoc/src/main/java/com/example/plugin>

plugin下的文件即解析上述插件生成javadoc.json文件，从而扩展swagger,实现通过注释来生成类及方法的标注(其它操作可自行扩展)

# 版本控制

该项目使用git进行版本管理。
