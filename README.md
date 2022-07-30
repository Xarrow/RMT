# RMT , A Cloud Web Terminal

[中文简体](docs/cn.md)

![Github Action Java Build CI](https://github.com/Xarrow/RMT/workflows/Github%20Action%20Java%20Build%20CI/badge.svg)
[![Build Status](https://travis-ci.org/Xarrow/RMT.svg?branch=master)](https://travis-ci.org/Xarrow/RMT)
[![OpenJDK Status](https://img.shields.io/badge/OpenJDK-1.8-brightgreen.svg)](https://openjdk.java.net/install/)

<a href="https://ssh.cloud.google.com/cloudshell/editor?cloudshell_git_repo=https://github.com/Xarrow/RMT.git"><img style="height:45px" alt="Open in Cloud Shell" src ="https://gstatic.com/cloudssh/images/open-btn.svg"></a>

A cloud web terminal based on Spring Boot , Pty4J and WebSocket.

![img](asserts/slogo.png)

## Quick Start

you can download standalone jar from [release](https://github.com/Xarrow/RMT/releases/).

* for docker running

  ```
    docker pull helixcs/rmt:latest
  
    docker run -itd --name rmt-app -e PORT=8081 -p 8080:8081 helixcs/rmt:latest
 
  ```
  
* for jar running, required jre 1.8+

    ```java
  
      java -Dfile.encoding=UTF-8 -Dserver.port=8080 -jar rmt-app.jar 
  
    ```

## For Developer

1. checkout from github

    `git@github.com:Xarrow/RMT.git`

2. maven install 
    
    `mvn install -Dmaven.test.skip=true`
    
3. run with jar
    
    `java -Dfile.encoding=UTF-8 -Dserver.port=8080 -jar rmt-app\target\rmt-app.jar`
    
## Just Expand

Reference at：[RMT Docs](docs/dev.md)


## Finally
* For [RuiMei](https://yuruimei.com) .

* Started from [cloudterm](https://github.com/javaterminal/cloudterm) ,stronger with RMT.

## LICENSE

Apache2

This Project Powered By Jetbrains OpenSource License

![img](asserts/jetbrains.svg)