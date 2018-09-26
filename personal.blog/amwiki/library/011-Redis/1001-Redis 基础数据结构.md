### Redis 安装
具体操作如下：
#### Docker 方式
```Java
# 拉取 redis 镜像
> docker pull redis
# 运行 redis 容器
> docker run --name myredis -d -p6379:6379 redis
# 执行容器中的 redis-cli，可以直接使用命令行操作 redis
> docker exec -it myredis redis-cli...
```
#### Github 源码编译方式
```Java
# 下载源码
> git clone --branch 2.8 --depth 1 git@github.com:antirez/redis.git
> cd redis
# 编译
> make
> cd src
# 运行服务器，daemonize表示在后台运行
> ./redis-server --daemonize yes
# 运行命令行
> ./redis-cli...
```
#### 直接安装方式
```Java
# mac
> brew install redis
# ubuntu
> apt-get install redis
# redhat
> yum install redis
# 运行客户端
> redis-cli
```
### Redis 基础数据结构
Redis 有 5 种基础数据结构，分别为：string (字符串)、list (列表)、set (集合)、hash (哈希) 和 zset (有序集合)。熟练掌握这 5 种基本数据结构的使用是 Redis 知识最基础也最重要的部分.
#### string (字符串)
字符串 string 是 Redis 最简单的数据结构。Redis 所有的数据结构都是以唯一的 key 字符串作为名称，然后通过这个唯一 key 值来获取相应的 value 数据。不同类型的数据结构的差异就在于 value 的结构不一样。

字符串结构使用非常广泛，一个常见的用途就是缓存用户信息。我们将用户信息结构体使用 JSON 序列化成字符串，然后将序列化后的字符串塞进 Redis 来缓存。同样，取用户信息会经过一次反序列化的过程。

Redis 的字符串是动态字符串，是可以修改的字符串，内部结构实现上类似于 Java 的 ArrayList，采用预分配冗余空间的方式来减少内存的频繁分配，如图中所示，内部为当前字符串实际分配的空间 capacity 一般要高于实际字符串长度 len。当字符串长度小于 1M 时，扩容都是加倍现有的空间，如果超过 1M，扩容时一次只会多扩 1M 的空间。需要注意的是字符串最大长度为 512M。

##### 键值对
```shell
127.0.0.1:6379> set name codehole
OK
127.0.0.1:6379> get name
"codehole"
127.0.0.1:6379> exists name
(integer) 1
127.0.0.1:6379> del name
(integer) 1
127.0.0.1:6379> get name
(nil)
127.0.0.1:6379>
```
##### 批量键值对
可以批量对多个字符串进行读写，节省网络耗时开销。

```shell
127.0.0.1:6379> set name01 val01
OK
127.0.0.1:6379> set name02 val02
OK
127.0.0.1:6379> mget name01 name02
1) "val01"
2) "val02"
127.0.0.1:6379> mget name01 name02 name03
1) "val01"
2) "val02"
3) (nil)
127.0.0.1:6379> mset name01 val001 name02 val002 name03 val003
OK
127.0.0.1:6379> mget name01 name02 name03
1) "val001"
2) "val002"
3) "val003"
127.0.0.1:6379>

```
##### 过期和 set 命令扩展
可以对 key 设置过期时间，到点自动删除，这个功能常用来控制缓存的失效时间。不过这个「自动删除」的机制是比较复杂的，后续我们再考虑。
```shell
127.0.0.1:6379> set name04 val04
OK
127.0.0.1:6379> expire name04 5
(integer) 1
127.0.0.1:6379> # 五秒之后
127.0.0.1:6379> get name04
(nil)
127.0.0.1:6379> setex name04 5 val04
OK
127.0.0.1:6379> get name04
"val04"
127.0.0.1:6379> get name04
(nil)
127.0.0.1:6379> get name04
(nil)
127.0.0.1:6379> setnx name04 val04
(integer) 1
127.0.0.1:6379> setnx name04 val04
(integer) 0
127.0.0.1:6379> setnx name04 val05
(integer) 0
127.0.0.1:6379> get name04
"val04"
127.0.0.1:6379>

```

##### 计数
如果 value 值是一个整数，还可以对它进行自增操作。自增是有范围的，它的范围是 signed long 的最大最小值，超过了这个值，Redis 会报错。
```shell
127.0.0.1:6379> set value 03
OK
127.0.0.1:6379> incr value
(error) ERR value is not an integer or out of range   #此时
127.0.0.1:6379> set value 3
OK
127.0.0.1:6379> incr value
(integer) 4
127.0.0.1:6379> incr value
(integer) 5
127.0.0.1:6379> incr value
(integer) 6
127.0.0.1:6379> incrby value 5
(integer) 11
127.0.0.1:6379> incrby value 5
(integer) 16
127.0.0.1:6379> incrby value -5
(integer) 11
127.0.0.1:6379> incrby value -5
127.0.0.1:6379> incrby value -5
(integer) 6
127.0.0.1:6379> incrby value 5
(integer) 11
127.0.0.1:6379> incrby value 5
(integer) 16
127.0.0.1:6379> incrby value 5
(integer) 21
127.0.0.1:6379> incrby value 5
(integer) 26
127.0.0.1:6379> set value 9223372036854775807
OK
127.0.0.1:6379> incr value
(error) ERR increment or decrement would overflow
127.0.0.1:6379>

```

#### list (列表)
Redis 的列表相当于 Java 语言里面的 LinkedList，注意它是链表而不是数组。这意味着 list 的插入和删除操作非常快，时间复杂度为 O(1)，但是索引定位很慢，时间复杂度为 O(n)，这点让人非常意外。

当列表弹出了最后一个元素之后，该数据结构自动被删除，内存被回收。...

![](assets/010/1645918c2cdf772e)  

Redis 的列表结构常用来做异步队列使用。将需要延后处理的任务结构体序列化成字符串塞进 Redis 的列表，另一个线程从这个列表中轮询数据进行处理。

##### 右边进左边出：队列
```shell
127.0.0.1:6379> rpush books java python shell
(integer) 3
127.0.0.1:6379> LLEN books
(integer) 3
127.0.0.1:6379> LPOP books
"java"
127.0.0.1:6379> LPOP books
"python"
127.0.0.1:6379> LPOP books
"shell"
127.0.0.1:6379> LPOP books
(nil)
```

##### 右边进右边出：栈
```shell
127.0.0.1:6379> rpush books java python shell
(integer) 3
127.0.0.1:6379> RPOP books
"shell"
127.0.0.1:6379> RPOP books
"python"
127.0.0.1:6379> RPOP books
"java"
127.0.0.1:6379> RPOP books
(nil)

```
##### 慢操作
lindex 相当于 Java 链表的get(int index)方法，它需要对链表进行遍历，性能随着参数index增大而变差。

ltrim 和字面上的含义不太一样，个人觉得它叫 lretain(保留) 更合适一些，因为 ltrim 跟的两个参数start_index和end_index定义了一个区间，在这个区间内的值，ltrim 要保留，区间之外统统砍掉。我们可以通过ltrim来实现一个定长的链表，这一点非常有用。

index 可以为负数，index=-1表示倒数第一个元素，同样index=-2表示倒数第二个元素。...
```shell
127.0.0.1:6379> rpush books java python shell
(integer) 3
127.0.0.1:6379> LLEN books
(integer) 3
127.0.0.1:6379> LINDEX books 1
"python"
127.0.0.1:6379> LRANGE books 1 -1
1) "python"
2) "shell"
127.0.0.1:6379> LTRIM books 1 -1
OK
127.0.0.1:6379> LRANGE books 0 -1
1) "python"
2) "shell"
127.0.0.1:6379> LLEN books
(integer) 2
127.0.0.1:6379> LTRIM books 1 0
OK
127.0.0.1:6379> LLEN books
(integer) 0
127.0.0.1:6379>

```


##### 快速列表
![](assets/010/164d975cac9559c5)  
如果再深入一点，你会发现 Redis 底层存储的还不是一个简单的 linkedlist，而是称之为快速链表 quicklist 的一个结构。

首先在列表元素较少的情况下会使用一块连续的内存存储，这个结构是 ziplist，也即是压缩列表。它将所有的元素紧挨着一起存储，分配的是一块连续的内存。当数据量比较多的时候才会改成 quicklist。因为普通的链表需要的附加指针空间太大，会比较浪费空间，而且会加重内存的碎片化。比如这个列表里存的只是 int 类型的数据，结构上还需要两个额外的指针 prev 和 next 。所以 Redis 将链表和 ziplist 结合起来组成了 quicklist。也就是将多个 ziplist 使用双向指针串起来使用。这样既满足了快速的插入删除性能，又不会出现太大的空间冗余。...


#### hash (字典)

Redis 的字典相当于 Java 语言里面的 HashMap，它是无序字典。内部实现结构上同 Java 的 HashMap 也是一致的，同样的数组 + 链表二维结构。第一维 hash 的数组位置碰撞时，就会将碰撞的元素使用链表串接起来。

![](assets/010/1647e419af9e3a87)  

不同的是，Redis 的字典的值只能是字符串，另外它们 rehash 的方式不一样，因为 Java 的 HashMap 在字典很大时，rehash 是个耗时的操作，需要一次性全部 rehash。Redis 为了高性能，不能堵塞服务，所以采用了渐进式 rehash 策略。...

![](assets/010/164dc873b2a899a8)  

渐进式 rehash 会在 rehash 的同时，保留新旧两个 hash 结构，查询时会同时查询两个 hash 结构，然后在后续的定时任务中以及 hash 操作指令中，循序渐进地将旧 hash 的内容一点点迁移到新的 hash 结构中。当搬迁完成了，就会使用新的hash结构取而代之。

当 hash 移除了最后一个元素之后，该数据结构自动被删除，内存被回收。...

![](assets/010/16458ef82907e5e1)

hash 结构也可以用来存储用户信息，不同于字符串一次性需要全部序列化整个对象，hash 可以对用户结构中的每个字段单独存储。这样当我们需要获取用户信息时可以进行部分获取。而以整个字符串的形式去保存用户信息的话就只能一次性全部读取，这样就会比较浪费网络流量。

hash 也有缺点，hash 结构的存储消耗要高于单个字符串，到底该使用 hash 还是字符串，需要根据实际情况再三权衡。...

```shell
127.0.0.1:6379> HSET books java javaing
(integer) 1
127.0.0.1:6379> HSET books shell shelling
(integer) 1
127.0.0.1:6379> HSET books oracle oracleing
(integer) 1
127.0.0.1:6379> HGETALL books
1) "java"
2) "javaing"
3) "shell"
4) "shelling"
5) "oracle"
6) "oracleing"
127.0.0.1:6379> HSET books javascript javascripting is good language
(error) ERR wrong number of arguments for HMSET
127.0.0.1:6379> HSET books javascript "javascripting is good language"
(integer) 1
127.0.0.1:6379> HGETALL books
1) "java"
2) "javaing"
3) "shell"
4) "shelling"
5) "oracle"
6) "oracleing"
7) "javascript"
8) "javascripting is good language"
127.0.0.1:6379> HLEN books
(integer) 4
127.0.0.1:6379> HGET books java
"javaing"
127.0.0.1:6379> HSET books java "java is a very good language"
(integer) 0
127.0.0.1:6379> hget books java
"java is a very good language"
127.0.0.1:6379>

```

同字符串对象一样，hash 结构中的单个子 key 也可以进行计数，它对应的指令是 hincrby，和 incr 使用基本一样。

```shell
127.0.0.1:6379> HINCRBY beijishiqidu age 28
(integer) 28
127.0.0.1:6379> HGET beijishiqidu age
"28"
127.0.0.1:6379> HINCRBY beijishiqidu age 1
(integer) 29
127.0.0.1:6379> HGET beijishiqidu age
"29"
127.0.0.1:6379>

```

#### set (集合)
Redis 的集合相当于 Java 语言里面的 HashSet，它内部的键值对是无序的唯一的。它的内部实现相当于一个特殊的字典，字典中所有的 value 都是一个值NULL。

当集合中最后一个元素移除之后，数据结构自动删除，内存被回收。


![](assets/010/16458e2da04f1a2d)

set 结构可以用来存储活动中奖的用户 ID，因为有去重功能，可以保证同一个用户不会中奖两次。

```shell
127.0.0.1:6379> SADD music popular
(integer) 1
127.0.0.1:6379> SADD music popular  #重复
(integer) 0
127.0.0.1:6379> SADD music classic quiet
(integer) 2
127.0.0.1:6379> SCARD music #查看数量
(integer) 3
127.0.0.1:6379> SMEMBERS music  #查看内容，可以看到是无须的。
1) "quiet"
2) "popular"
3) "classic"
127.0.0.1:6379> SPOP music  # 弹出一个。也可以看到，是随机取出来一个。
"classic"
127.0.0.1:6379>

```

#### zset (有序集合)
zset 可能是 Redis 提供的最为特色的数据结构，它也是在面试中面试官最爱问的数据结构。它类似于 Java 的 SortedSet 和 HashMap 的结合体，一方面它是一个 set，保证了内部 value 的唯一性，另一方面它可以给每个 value 赋予一个 score，代表这个 value 的排序权重。它的内部实现用的是一种叫做「跳跃列表」的数据结构。

zset 中最后一个 value 被移除后，数据结构自动删除，内存被回收。...

![](assets/010/16458f9f679a8bb0)

zset 可以用来存粉丝列表，value 值是粉丝的用户 ID，score 是关注时间。我们可以对粉丝列表按关注时间进行排序。

zset 还可以用来存储学生的成绩，value 值是学生的 ID，score 是他的考试成绩。我们可以对成绩按分数进行排序就可以得到他的名次。...

```Java
127.0.0.1:6379> ZADD books 9.0 "think in java"
(integer) 1
127.0.0.1:6379> zadd books 8.9 "java concurrency"
(integer) 1
127.0.0.1:6379> zadd books 8.6 "java cookbook"
(integer) 1
127.0.0.1:6379> ZRANGE books 0 -1 # 按 score 排序列出，参数区间为排名范围
1) "java cookbook"
2) "java concurrency"
3) "think in java"
127.0.0.1:6379> ZREVRANGE books 0 -1 # 按 score 逆序列出，参数区间为排名范围
1) "think in java"
2) "java concurrency"
3) "java cookbook"
127.0.0.1:6379> ZCARD books # 相当于 count()
(integer) 3
127.0.0.1:6379> zscore books "java concurrency" # 获取指定 value 的 score
"8.9000000000000004"  # 内部 score 使用 double 类型进行存储，所以存在小数点精度问题
127.0.0.1:6379> zrangebyscore books 0 8.91  # 排名
1) "java cookbook"
2) "java concurrency"
127.0.0.1:6379> zrangebyscore books -inf 8.91 withscores  # 根据分值区间 (-∞, 8.91] 遍历 zset，同时返回分值。inf 代表 infinite，无穷大的意思。
1) "java cookbook"
2) "8.5999999999999996"
3) "java concurrency"
4) "8.9000000000000004"
127.0.0.1:6379> zrem books "java concurrency" # 删除 value
(integer) 1
127.0.0.1:6379> zrange books 0 -1
1) "java cookbook"
2) "think in java"
127.0.0.1:6379>

```

##### 跳跃列表

zset 内部的排序功能是通过「跳跃列表」数据结构来实现的，它的结构非常特殊，也比较复杂。

因为 zset 要支持随机的插入和删除，所以它不好使用数组来表示。我们先看一个普通的链表结构。

![](assets/010/164c5a90442cd51a)

我们需要这个链表按照 score 值进行排序。这意味着当有新元素需要插入时，要定位到特定位置的插入点，这样才可以继续保证链表是有序的。通常我们会通过二分查找来找到插入点，但是二分查找的对象必须是数组，只有数组才可以支持快速位置定位，链表做不到，那该怎么办？

想想一个创业公司，刚开始只有几个人，团队成员之间人人平等，都是联合创始人。随着公司的成长，人数渐渐变多，团队沟通成本随之增加。这时候就会引入组长制，对团队进行划分。每个团队会有一个组长。开会的时候分团队进行，多个组长之间还会有自己的会议安排。公司规模进一步扩展，需要再增加一个层级 —— 部门，每个部门会从组长列表中推选出一个代表来作为部长。部长们之间还会有自己的高层会议安排。

跳跃列表就是类似于这种层级制，最下面一层所有的元素都会串起来。然后每隔几个元素挑选出一个代表来，再将这几个代表使用另外一级指针串起来。然后在这些代表里再挑出二级代表，再串起来。最终就形成了金字塔结构。

想想你老家在世界地图中的位置：亚洲-->中国->安徽省->安庆市->枞阳县->汤沟镇->田间村->xxxx号，也是这样一个类似的结构。...

![](assets/010/164c5bb13c6da230)

「跳跃列表」之所以「跳跃」，是因为内部的元素可能「身兼数职」，比如上图中间的这个元素，同时处于 L0、L1 和 L2 层，可以快速在不同层次之间进行「跳跃」。

定位插入点时，先在顶层进行定位，然后下潜到下一级定位，一直下潜到最底层找到合适的位置，将新元素插进去。你也许会问，那新插入的元素如何才有机会「身兼数职」呢？

跳跃列表采取一个随机策略来决定新元素可以兼职到第几层。

首先 L0 层肯定是 100% 了，L1 层只有 50% 的概率，L2 层只有 25% 的概率，L3 层只有 12.5% 的概率，一直随机到最顶层 L31 层。绝大多数元素都过不了几层，只有极少数元素可以深入到顶层。列表中的元素越多，能够深入的层次就越深，能进入到顶层的概率就会越大。

这还挺公平的，能不能进入中央不是靠拼爹，而是看运气。...
