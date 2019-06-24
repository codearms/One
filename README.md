# One




# Go语言学习

## 目录

* [概述](#概述)
* [搭建开发环境](#搭建开发环境)

## 概述


### 什么是程序？

程序：为了让计算机执行某些操作或解决某个问题而编写的一些列有序指令的结合。

### Google创造Golang的原因

* 计算机硬件技术更新频繁，性能提高很快。目前主流的编程语言发展明显落后于硬件，不能合理利用多核多CPU的优势提升软件系统性能。
* 软件系统复杂度越来越高，维护成本越来越高，目前缺乏一个足够简洁高效的编程语言。（现有的编程语言：1.风格不统一 2.计算能力不够 3.处理大并发不够好）
* 企业运行维护很多C/C++项目，C/C++程序运行速度虽然很快，但是编译速度却很慢，同时还存在内存泄漏的一些列的困扰需要解决。

### Golang的特点

Golang保证了既能到达静态编译语言的安全和性能，又达到了动态语言开发维护的高效率，使用一个表达式来形容Go语言: Go = C + Python。
说明Go语言既有C静态语言程序的运行速度，又能达到Python动态语言的快速开发。

* 从C语言中继承了很多理念,包括表达式语法,控制结构,基础数据类型,调用参数传值,指针等 等,也保留了和C语言一样的编译执行方式及弱化的指针。
* 引入包的概念,用于组织程序结构,Go语言的一个文件都要归属于一个包,而不能单独存在。
* 垃圾回收机制,内存自动回收,不需开发人员管理。
* 天然并发 (重要特点)
  * 从语言层面支持并发,实现简单。
  * goroutine,轻量级线程,可实现大并发处理,高效利用多核。
  * 基于 CPS 并发模型(Communicating Sequential Processes)实现。
* 吸收了管道通信机制,形成Go语言特有的管道channel，通过管道channel, 可以实现不同的goroutine之间的相互通信。
* 函数可以返回多个值。
* 新的创新:比如切片slice、延时执行defer。


## 搭建开发环境

### Windows

### Mac

### Linux