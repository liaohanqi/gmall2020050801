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
                  User工程
                  	    ums_member
                  Manager工程
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
                  elastricSearch工程
                        PmsSearchCrumb
                        PmsSearchParam
                        PmsSearchSkuInfo
                  order工程
                  	    oms_order
                  	    oms_order_item
                  payment工程
                  	    payment_info
                  cart工程
                  	    oms_cart_item
                  passport工程
                        
                  item工程
                    
                  
                  	
             先这样。休息下。洗澡去！！！！！！！！！hh
day04

        第一次提交
        
        任务完成情况：
                创建了item工程的web模块，没有service模块，使用的是manager工程的service模块。
                
                《往深》
                ******item模块是重中之中，因为涉及到亿万级类（大流量级）的请求访问
                    需要考虑的问题：
                        1、mysql与es如何保持数据同步
                            方案一：每次发布商品的时候，调用es接口实现数据的同步
                            方案二：使用cannal解决，搭配kafka等消息中间件
                        1、大流量商品详情页面设计原理
                            主要使用了：1、缓存技术：nginx
                                        2、静态化页面
                                        3、cdn缓存
                                        4、消息中间件
                        2、为什么需要对我们商品详情页面实现静态化
                            前端页面渲染模板技术，一般有两种：jsp、Freemarker或者Thymeleaf(springBoot推荐)。
                            商品价格和库存经常变化，其他方面很少变化
                            所以实现静态化，放在ngnix服务器上，访问速度快
                                第一次访问web层，再转service和数据库，缓存到ngnix中
                                第二次请求过来后，直接在ngnix服务器上取就行了，不用到service层再到数据库了
                        3、利用FreeMarker生成静态html页面
                             生成静态页面的两个入口：第一：就是第一轮请求经过web、service和数据库后，将页面源代码进行nginx缓存
                                                     第二：就是在发布商品的时候，通过FreeMarker直接放入到ngnix中,这叫《缓存预热》
                        4、基于Nginx实现对我们页面缓存及原理
                            原理：直接将页面源代码缓存到nginx目录中，缓存的key是商品详情的Url路径地址
                            1、基于Nginx缓存静态页面存在那些缺陷
                                nginx与mysql数据保持不一致
                                    解决：1、直接清除nginx缓存，不靠谱
                                          2、在url后面加上最新更新商品的时间
                            2、openresty+nginx+lua实现大流量级别原理
                        5、在Nginx基础上，在进行商品详情页面的CDN部署，架构设计及原理
                            遵循就近原则访问，减少带宽距离的传输，从而提高整个页面访问的效率
                            CDNCDN的全称是Content Delivery Network，即内容分发网络
                                    也就是第三方存储服务器，例如七牛云，阿里云oss等
                        
                        《谈谈分布式缓存设计的核心问题》
                            1、缓存预热
                            2、缓存更新：定时更新/过期更新（设过期时间）/写请求更新（强一致）/读请求更新（读时判断）
                            3、缓存淘汰策略：FIFO(先进先出)/LRU(最近最少使用)/LFU(最不经常使用)
                            4、缓存雪崩：请求加锁/失效更新（设过期时间）/设置不同的失效时间（随机）
                            5、缓存穿透：布隆过滤器（将所有数据映射到足够大的Bitmap中，请求都先经过布隆过滤器拦截判断识别）/cache null策略（通常不超过5分钟）
                            6、缓存降级（访问剧增，优先保障核心业务进行，减少或关闭核心业务对资源的使用）：
                                        写降级（即：将写请求从数据库降级到缓存。然后数据进行异步<消息中间件>更新到数据库）/
                                        读降级（数据库过载或故障时，只从缓存读取数据并返回给用户）
                        
                        ①前端页面渲染模板技术，一般有两种：Freemarker或者Thymeleaf。当前使用Thymeleaf。
                                使用了thymeleaf前端动态模块技术，需要把前端页面添加到resource的static和template中
                                            static:存放着页面渲染的文件，如*.css/*.js/*.jpg(png)
                                                   同时，也存放着静态文件
                                            template:存放着页面文件，如*.html
                        ②中间还小小地使用了redis，对item数据的缓存
                                    启动redis：/usr/local/redis/bin/redis-server /usr/local/redis/redis.conf 
                                    查看进程：netstat -anp |grep 6379
                                    服务器连接redis：
                                        路径：cd /usr/local/redis/bin
                                        启动命令：./redis-cli -h 192.168.37.100 -p 6379
                                        
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
                ①item工程没有service模块，使用的是managet的service模块，因此启动此工程前，先启动manager的service工程。
                
                               
        第二次提交
            今天干不动了，明天再干！
            item残留了一个小bug没有解决，如下：
            ***************************
            APPLICATION FAILED TO START
            ***************************
            Description:
            Field redisUtil in com.liaohanqi.gmall.manager.service.Impl.SkuServiceImpl required a bean of type 'com.liaohanqi.gmall.util.RedisUtil' that could not be found.
            Action:
            Consider defining a bean of type 'com.liaohanqi.gmall.util.RedisUtil' in your configuration.
        
        以上问题没解决。过！！！！！！！！！！
        
day05

        第一次提交
            一、静态页面的作用和生成！
            二、网页显示一般格式是：域名+端口号+uri
                没配DNS前，我们一般是：localhost+端口号+uri
                配置DNS后，我们可以是：search.gmall.com===localhost.gmall.com。
                    比如浏览器输入gmall.search.com，那么DNS会查找出它的ip地址是localhost
                    DNS就是根据域名查找ip地址。
                1：DNS配置就是对域名就行配置：
                    路径：C:\Windows\System32\drivers\etc   hosts文件
                    配置后的样子：127.0.0.1 localhost search.gmall.com cart.gmall.com item.gmall.com。。。。
                2：端口号的配置在页面里：（重点在端口号）
                    如下：function item(skuid) {
                              console.log("skuid:"+skuid);
                              window.open("http://item.gmall.com:8082/"+skuid+".html");
                          }
                3：uri的配置在controller里的形参        
            三：search工程，关联着manager工程
            四：引入了elasticSearch，因此启动项目前，需要在服务器启动elasticSearch
                                                     如果使用kibana,则还需要启动kibana。
                启动elasticsearch
                路径：/opt/es/elasticsearch-6.3.1/bin/
                启动命令：首先，su es
                		  然后，./elasticsearch
                		  然后，curl http://localhost:9200
                			  （可能拒绝访问，再试试curl http://www.baidu.com/就可以了）
                		  最后，浏览器访问http://192.168.37.100:9200/
                启动kibana
                路径：/opt/es/kibana-6.3.1-linux-x86_64/bin/
                启动命令：首先，nohup ./kibana &
                		  然后，浏览器访问http://192.168.37.100:5601
                
                需要查看进程，并杀死进程的方法
                    查看命令：ps -ef|grep elasticsearch
                    返回结果：es         7741   7709 60 11:39 pts/0    00:02:11
                    杀死命令：kill -9 7741
            五：elasticSearch工程的业务流程
                 1、controller层根据入参，然后去service层调取dsl的api进行判断输入哪种搜索（三级id/关键字/平台属性），
                    并生成elasticSearchdsl的dsl语句（其中包含高亮/聚合函数等），相当于mysql的sql语句;
                 2、然后service层的elasticSearch的客户端会去执行dsl语句，并返回结果
                 3、controller层，会对service层经过搜索返回的结果进行判断和输入到页面。
                 4、还有一个面包屑功能也在其中完成。
                 
day06

            第一次提交：
                完成任务情况：
                一：cart工程的业务流程
                    1、加入购物车controller
                        1、判断是否登录
                            登录：操作DB和redis
                             没登录：操作cookie
                    2、购物车列表controller
                        1、查缓存
                        2、查cookie
                    
day07

            第一次提交：
                总结：
                      一：后台管理人：manager工程
                          用户：item工程---search工程---cart工程---（passport工程）---order工程(user工程)
                      二：各个工程的服务端口
                            user:8080/8090
                            manager:8081/8091
                            item:8083
                            search:8084/8094
                            cart:8085/8095
                            passport:8086
                            order:8087
                            payment:8088
                            seckill:8089
                            
day不知道过了多少天

            提交：
                完成了item模块和payment模块以及seckill模块。
                item模块：
                    创建了gmall-item-web
                        （入参：@PathVariable String skuId,@PathVariable String skuId）
                        业务流程：通过传入的skuId,查找商品详情
                                  通过商品详情拿到spuId
                                  然后通过skuId和spuId，拿取销售属性列表
                                  将相关的属性分别显示出item页面
                        
                payment模块：
                    创建了gmall-payment
                        业务流程：1、显示出index；
                                     根据http请求头，获取用户id和用户名。
                                     然后通过用户id和订单序列号，查找订单详情
                                     根据订单详情，拿取总金额
                                     最后，显示相关信息到页面
                                  2、选择支付宝支付或者微信支付
                                     支付宝支付：
                                        （入参是：订单序列号）
                                        通过订单序列号，查找订单详情；
                                            OmsOrder omsOrder = orderService.getOrderByOrderSn(orderSn);
                                        根据订单详情设置支付的信息，并保存
                                        //重点以下
                                        调取跳转支付宝支付页面
                                            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
                                        并设置公共参数/业务参数
                                        然后通过支付宝客户端，生产支付表单
                                            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
                                        最后，设置延迟检查订单支付状态的定时任务，发送一个消息队列的支付信息延迟队列
                                            （通俗点就是，提交订单后不一定支付，用户可能反悔/没电/没流量就退出了。于是需要通过消息队列定时任务给用户，提醒用户）
                                            paymentService.sendPayCheckQueue(paymentInfo, 8L);
                                        
                                  3、如支付宝，则进行支付处理
                                  消息队列；
                                      （入参：HttpServletRequest ）
                                      解析：HttpServletRequest对象代表客户端的请求，当客户端通过HTTP协议访问服务器时，HTTP请求头中的所有信息都封装在这个对象中，通过这个对象提供的方法，可以获得客户端请求的所有信息。
                                        根据http请求头，拿取客户端提交的数据
                                        然后根据支付宝支付平台进行支付信息
                                            boolean b = AlipaySignature.rsaCheckV1(map, AlipayConfig.alipay_public_key, AlipayConfig.charset);
                                        然后，更新支付信息业务，并进行幂等性校验（也就是防止重复提交）
                                        最后，将支付成功的消息发送到系统消息队列中，通知gmall某系统outTradeNo支付车功能。
                    
                seckill模块：
                    创建了gmall-seckill 
                        要点：1、选择redis分布式事务和分布式锁
                              2、WATCH 命令
                                可以为Redis事务提供 check-and-set （CAS）行为。
                                被WATCH的键会被监视，并会发觉这些键是否被改动过了。
                                如果有至少一个被监视的键在 EXEC 执行之前被修改了，
                                那么整个事务都会被取消， EXEC 返回nil-reply来表示事务已经失败。
                              3、MULTI命令
                                用于开启一个事务，它总是返回OK。MULTI执行之后,
                                客户端可以继续向服务器发送任意多条命令，
                                这些命令不会立即被执行，而是被放到一个队列中，
                                当 EXEC命令被调用时， 所有队列中的命令才会被执行。
                              4、EXEC命令
                                负责触发并执行事务中的所有命令：
                                需要特别注意的是：即使事务中有某条/某些命令执行失败了，
                                事务队列中的其他命令仍然会继续执行
                                ——Redis不会停止执行事务中的命令，而不会像我们通常使用的关系型数据库一样进行回滚。
                              5、setNX：
                                SETNX key value
                                将 key 的值设为 value ，当且仅当 key 不存在。
                                若给定的 key 已经存在，则 SETNX 不做任何动作。
                                SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。
                                    时间复杂度：
                                        O(1)
                                    返回值：
                                        设置成功，返回 1 。
                                        设置失败，返回 0 。
                                        
                        业务流程：1、首先，用setNX命令限制一个用户10秒内只能抢购一次；
                                     String OK = jedis.set("user:" + userId + ":seckill", "1", "nx", "px", 1000 * 10);
                                  2、然后，使用WATCH命令监控库存：
                                     jedis.watch("stock");；
                                  3、然后，使用MULTI命令开启一个事务：
                                     Transaction multi = jedis.multi();
                                  3、然后，进行事务处理：
                                     multi.incrBy("stock",-1)主要是库存处理-1操作；
                                  4、再然后，进行事务执行：
                                     multi.exec();
                                  5、最后，用setNX命令限制一个用户抢成功之后，15分钟内不能再次抢购；
                                     jedis.set("user:"+userId+":seckill","1","nx","px",1000*60*15);