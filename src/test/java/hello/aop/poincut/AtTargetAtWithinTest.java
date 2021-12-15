package hello.aop.poincut;

import hello.aop.member.MemberServiceImpl;
import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;

import java.lang.reflect.Method;

@Slf4j
@Import({AtTargetAtWithinTest.Config.class})
@SpringBootTest
public class AtTargetAtWithinTest {

    @Autowired
    Child child;

    static class Parent {
        public void parentMethod(){}
    }

    @ClassAop
    static class Child extends Parent{
        public void childMethod(){}
    }

    @Slf4j
    @Aspect
    static class AtTargetAtWithinAspect{

        //@target : 인스턴스 기준으로 모든 메서드의 조인포인트를 선정, 부모 타입 메서드도 적용
        @Around("execution(* hello.aop..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        //@within : 인스턴스 내부의 모든 메서드만 조인포인트로 선정, 부모 타입 메서드는 적용X
        @Around("execution(* hello.aop..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
    static class Config{
        @Bean
        public Parent parent(){ return new Parent();}
        @Bean
        public Child child(){ return new Child();}
        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {return new AtTargetAtWithinAspect();}


    }
    @Test
    void success(){
        log.info("child proxy = {}", child.getClass());
        child.childMethod();
        child.parentMethod();
    }
}
