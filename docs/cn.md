# RMT , 云 Web 终端

[English](en.md)

[![Build Status](https://travis-ci.org/Xarrow/RMT.svg?branch=master)](https://travis-ci.org/Xarrow/RMT)
[![OpenJDK Status](https://img.shields.io/badge/OpenJDK-1.8-brightgreen.svg)](https://openjdk.java.net/install/)

一款基于 Spring Boot , Pty4J , WebSocket 开发而来的云 Web 终端.

![img](../asserts/slogo.png)

你可以访问演示站点: [https://rmt-ns.herokuapp.com/](https://rmt-ns.herokuapp.com/)

## 快速开始

您可以从 [release](https://github.com/Xarrow/RMT/releases/) 下载已经编译好的 jar 包运行.

* docker 运行
    
     ```
         docker pull helixcs/rmt:latest
       
         docker run -itd --name rmt-app -e PORT=8081 -p 8080:8081 helixcs/rmt:latest
      
     ```
      
* 单 Jar 文件运行，仅支持 Java 1.8 以上版本运行环境.

    ```java
  
      java -Dfile.encoding=UTF-8 -Dserver.port=8080 -jar rmt-app.jar 
  
    ```

## 开发

1. 从仓库检出

    `git@github.com:Xarrow/RMT.git`

2. maven 构建 
    
    `mvn install -Dmaven.test.skip=true`
    
3. 运行
    
    `java -Dfile.encoding=UTF-8 -Dserver.port=8080 -jar rmt-app\target\rmt-app.jar`
    
## 简单的拓展

参考：[RMT 开发文档](dev.md)

## 最后

* 始于 [cloudterm](https://github.com/javaterminal/cloudterm) , 更胜于此.

## LICENSE

Apache2

Jetbrains 开源许可授权强力驱动

![img](../asserts/jetbrains.svg)