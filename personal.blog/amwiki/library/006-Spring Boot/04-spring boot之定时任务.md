#### pom包配置
pom包里面只需要引入springboot starter包即可
```java
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>
     <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
	</dependency>
</dependencies>
```
#### 启动类启用定时
在启动类上面加上<mark>@EnableScheduling</mark>即可开启定时
```java
@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```
#### 创建定时任务实现类
定时任务1：
```java
@Component
public class SchedulerTask {

    private int count=0;

    @Scheduled(cron="*/6 * * * * ?")
    private void process(){
        System.out.println("this is scheduler task runing  "+(count++));
    }

}
```
定时任务2：
```java
@Component
public class Scheduler2Task {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));
    }

}
```
结果如下：
```java
this is scheduler task runing  0
现在时间：09:44:17
this is scheduler task runing  1
现在时间：09:44:23
this is scheduler task runing  2
现在时间：09:44:29
this is scheduler task runing  3
现在时间：09:44:35
```
#### 参数说明
> @Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="\*/6 \* \* \* \* ?",一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。

fixedRate 说明
* @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
* @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
* @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次

#### 拓展说明
>加入一个定时任务中配置了两个方法都需要定时执行，而且是互相不干扰的，此时就需要用到@Async和@EnableAsync两个注解，如果不配置这两个注解的话，两个定时一定串行执行的。

>另外一种方法就是实现SchedulingConfigurer这个类，给重新设置个支持多个线程的调度器，默认是单个线程池newSingleThreadScheduledExecutor的。

#### 文章转自：
http://www.ityouknow.com/springboot/2017/05/09/springboot-deploy.html

#### 示例代码地址：
网址：https://github.com/beijishiqidu/spring-boot-examples

git地址：https://github.com/beijishiqidu/spring-boot-examples.git

工程名：spring-boot-scheduler

### 结束~~~
定时任务这块的配置因为你配置的周期和定时任务的类型以及是否是异步执行等三个主要因素，再加上实际执行过程中的，如果耗费的时间大于时间周期这个次要因素，此时就有好几种场景，需要根据业务的场景进行分析是否会有影响。
举个栗子：假如每隔6秒执行一个定时任务，但是实际这个周期任务执行就用了10秒钟，那么此时就要分情况了，是否支持异步执行，类型是fixedRate还是fixedDelay还是cron表达式等。
