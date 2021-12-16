package hello.aop.poincut;

import hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * #true = CGLIB, false = JDK proxy
 * spring.aop.proxy-target-class=true
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Aspect
    static class ThisTargetAspect{

        @Around("this(hello.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[doThisInterface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[doTargetInterface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(hello.aop.member.MemberServiceImpl)")
        public Object doThisInterfaceImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[doTargetInterfaceImpl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(hello.aop.member.MemberServiceImpl)")
        public Object doTargetInterfaceImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[doTargetInterfaceImpl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
