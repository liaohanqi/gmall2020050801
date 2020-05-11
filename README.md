# gmall2020050801
电商项目每日代码更新

day01 
        
        第一次提交
            此次任务：搭建了单架构的user的服务。页面也可以查出结果

        第二次提交
            此次完成了：
            框架的基础部分的架构搭建（后面还有业务场景的架构）
            分别为gmall-parent/gmall-api/gmall-common-util/gmall-service-util/gmall-web-util
            各部分进行了引入依赖。
            并且能够运行，且浏览器能够读取和获取，完美！！！（20200508）

        第三次提交
            此次是试验git上存的准确性

        此处试验其他同时更改了代码
        
day02
        
        第一次提交
        
        第二次提交
            引入dubbbo+zookeeper框架
            那dubbo和zookeeper如何引入？
                dubbo其实是一组jar包，通过maven引入就可以。
                zookeeper是一个开源的服务软件，需要安装到linux中。
            dubbo的使用分为提供端和消费端。
            重点：使用起来非常方便只要记住两个注解@Reference和@Service《alibaba的依赖地址》
            加上application.properties的一段配置就可以了。
            
            然后使用服务器的ip地址对dubbo的监控中心进行查看，可以查看的服务的提供方和消费方的情况
            以及提及一下，就是dubbo的负载均衡策略有：随机/轮询和虽少并发及以上所有混用。四种方案。
            
            dubbo的基本概念
            服务的容器
            服务提供者（Provider）
            服务消费者（Consumer）
            注册中心（Registry）
            监控中心（Monitor）
            	dubbo调用关系说明
            	服务容器负责启动，加载，运行服务提供者。
            	服务提供者在启动时，向注册中心注册自己提供的服务。
            	服务消费者在启动时，向注册中心订阅自己所需的服务。
            	注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
            	服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
            	服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

        《试验别的同事对同一代码做出更新的情况》
        
        第三次提交
            原来user的dao层/service层与controller层为同一模块
            现将user分为了不同的两个模块，提供者（dao层/service层）和消费者（controller层），
            分别为gmall-user-web和gmall-user-service
            
            最后运行成功
            
         第四次提交
            -----正式开始电商项目-----
            new了两个新的module，分别为gmall-manager-web和gmall-managet-service。
                其中引入了以下几点：
                1、在manager-web里，引入了fastDFS对图片的上存和下载，搭配着nginx使用(此时nginx作为web服务器使用)
                2、在api里，引入了大量的相关bean（bean就是数据库字段的java化，就是封装？？？？？）
                3、引入了thmeleaf前端动态渲染模板技术，springBoot的指定搭配
                    前端的开发工具及技术介绍
                        ①vscode前端开发工具，相当于后端开发工具idea
                        ②Node.js 简单来说（相当于），就是运行在服务端的 JavaScript，是一个事件驱动I/O服务端JavaScript环境
                        ③NPM 是 Node.js的包管理工具，相当于前端的Maven 
                        ④Vue.js是一套用于构建用户界面的渐进式框架,核心库只关注视图层，
                                不仅易于上手，还便于与第三方库或既有项目整合,前端集成开发框架，
                                 就像springboot(一般通过axios远程访问数据服务器)
                    《前后端分离》之前端环境和工具的搭建：
                        首先，把node.js安装在linux服务器上，相当于安装了环境；
                        然后，安装npm，node.js默认集成npm
                        最后，找到前端包后，cmd文件，然后输入npm run dev即可。

day03
        
        第一次提交：
            完成了：两个controller，分别为AttrController和CatalogController
                    CatalogController：平台属性的目录(Catalog)
                    AttrController：平台属性的字段和属性（value）
                    以及他们的service层和dao层的实现
            胜利在望了！！！！！！！！！
            
        第二次提交：
            完美手工！！！！！！！！！！
            managet模块已经完成了。。。。。。。。。。爽！！！！！
                一：spu（平台属性）和sku（销售属性）的区别
                    spu平台属性和平台属性值主要用于商品的《检索》，每个三级分类对应的属性都不同。 
                    sku销售属性，就是商品详情页右边，可以通过销售属性来定位一组spu下的哪款sku
                二：引入了fastDFS对图片的上存和下载
                    FastDFS整合nginx服务器，Nginx此时，作为web服务器使用。需要启动nginx，才能根据服务地址和相关的url进行访问图片。
                三：电商项目各模块涉及的表
                  User模块
                  	    ums_member
                  Manager模块
                  		pms_base_attr_info
                  		pms_base_attr_value
                  		pms_base_catalog1
                  		pms_base_catalog2
                  		pms_base_catalog3
                  		pms_base_sale_attr
                  	spu类
                  		pms_product_info
                  		pms_product_sale_attr
                  		pms_product_sale_attr_value
                  		pms_product_vertify_record
                  	sku类
                  		pms_sku_attr_value
                  		pms_sku_image
                  		pms_sku_info
                  		pms_sku_sale_attr_value
                  order模块
                  	oms_order
                  	oms_order_item
                  payment模块
                  	payment_info
                  cart模块
                  	oms_cart_item
                  	
             先这样。休息下。洗澡去！！！！！！！！！hh！！
             先这样。休息下。洗澡去！！！！！！！！！hh
day04

        第一次提交
        总结：
            1、启动user工程：
                ①由于springBoot内置tomcat，所以无需启动tomcat，直接运行后，在浏览器根据域名和端口号以及uri就能打开相关的信息
                ②此部分也不涉及到web页面信息，所以没有前后端分离和使用thymeleaf页面渲染之说的。
                ③由于其中需要从数据库拿取数据，因此需要连接数据库。
            2、启动manager工程
                ①此处，使用了前后端分离。因此，需要命令（npm run dev）启动前端包。
                    前端和后端ip和端口不一致，因此涉及到跨域问题。需要加@CrossOrigin
                ②由于引入了dubbo+zookeeper分布式框架，
                    打开虚拟机，服务器会自动启动zookeeper
                    可以根据--服务器地址+8080/dubbo--查看dubbo服务的详细情况。
                ③使用了fastDFS对图片的上存下载，并且搭配着ngnix作为web服务器使用。因此，需要启动ngnix服务器。
            3、启动item工程
                ①使用了thymeleaf前端动态模块技术，需要把前端页面添加到resource的static和template中
                    static:存放着页面渲染的文件，如*.css/*.js/*.jpg(png)
                           同时，也存放着静态文件
                    template:存放着页面文件，如*.html
                ②item工程没有service模块，使用的是managet的service模块，因此启动此工程前，先启动manager的service工程。
                ③中间还小小地使用了redis，对item数据的缓存
                    启动redis：/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 
                    查看进程：netstat -anp |grep 6379
                    服务器连接redis：
                        路径：cd /usr/local/redis/bin
                        启动命令：./redis-cli -h 192.168.37.100 -p 6379
                
        任务完成情况：
            创建了item工程的web模块，没有service模块，使用的是manager工程的service模块。
        