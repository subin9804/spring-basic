package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        //AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        // 1. 코드에 InitializingBean, DisposableBean를 상속받아 오버라이드 메서드를 활용할 수 있다.
        // - 스프링이 제공하는 거라 매우 의존적이며 메서드 이름도 바꾸지 못함
        // - 외부라이브러리에 사용 불가

        // 2. @Bean(initMethod = "init", destroyMethod = "close")
        // - 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 적용할 수 있다.
        // - 스프링코드에 의존하지 않고 빈 라이프사이클에 관여할 수 있다.

        // 3. 코드에 @PostConstruct , @PreDestroy 를 붙여 사용할 수도 있다.
        // - 다만 외부라이브러리에는 적용하지 못한다.
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");

            return networkClient;
        }
    }
}
