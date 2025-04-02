一. 介绍
Nacos 是一个集 服务注册发现、配置管理、负载均衡 于一体的动态服务治理平台，适用于微服务架构。
1 服务注册与发现
● 服务注册：微服务启动时，将自身信息（IP、端口、权重等）注册到 Nacos。
● 服务发现：其他服务可以通过 Nacos 查询可用的服务实例，实现动态调用。
● 健康检查：Nacos 定期检测服务状态，剔除不可用实例。
2 动态配置管理
● 配置集中管理：可以在 Nacos 控制台统一管理多个应用的配置文件。
● 配置动态刷新：当配置变更时，Nacos 会主动推送更新，服务无需重启即可生效。
● 多环境、多租户支持：适用于不同的环境（如开发、测试、生产）。
3 负载均衡 & 流量管理
● 权重调整：动态调整服务权重，实现流量倾斜。
● 路由策略：可以按标签、版本、区域等进行服务调用路由。
4 扩展支持
● 支持 K8s、Spring Cloud、Dubbo、gRPC 等生态，可以无缝集成到主流微服务框架中。
二.使用
背景：基于微服务项目的开发，因此多个服务都为独立模块，不同模块无法直接访问调用不同模块的API
1.发现服务
1）此时使用nacos进行服务的发现需要引入依赖
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
2）同时在程序启动类上加上注解
@EnableDiscoveryClient // 服务发现注解，需要nacos的模块都要添加
@SpringBootApplication
public class XXXApplication{
    ......
}
 3）通过Spring依赖注入（DI）的方式获取
@Autowired
private DiscoveryClient discoveryClient;
// 该DI由SpringCloud官方提供的对象，本质上就是统一封装调用nacos的服务发现
//二者选一即可
@Resource
private NacosDiscoveryClient nacosDiscoveryClient;
// 该DI由Nacos官方提供的对象
 4）使用discoveryClient获取服务实例
//商品查询的API 访问路径/product/{id}

//服务列表
List<String> services = nacosDiscoveryClient.getServices();

//根据服务列表获取第一个服务的实例列表
List<ServiceInstance> instances = nacosDiscoveryClient.getInstances(services.get(0));

//再取实例列表第一个实例对象，去除host和port进行拼接
String url = "http://" + instances.get(0).getHost() + ":" + instances.get(0).getPort() + "/product/" + productId;
 服务列表：即订单服务、商品服务、支付服务等等
 实例列表：如订单服务运行了不止一个端口8001，还有8002、8003等多个端口的实例服务，也是微服务集群构  建的一个缩影
2.远程调用API
使用RestTemplate方法进行服务的远程调用
@Configuration
public class OrderServiceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
@Autowired
private OrderServiceConfig instance;

//通过远程调用获取商品模块的信息
Product product = instance.restTemplate().getForObject(url, Product.class);

三.升级迭代
