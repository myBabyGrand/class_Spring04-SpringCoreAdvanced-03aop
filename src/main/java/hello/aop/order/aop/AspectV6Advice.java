package hello.aop.order.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

@Slf4j
@Aspect
public class AspectV6Advice {

//    @Around("hello.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
//        try{
//            //@Before
//            log.info("[트랜잭션 시작] {}",  joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[트랜잭션 커밋] {}",  joinPoint.getSignature());
//            return result;
//        }catch (Exception e){
//            //@AfterThrowing
//            log.info("[트랜잭션 롤백] {}",  joinPoint.getSignature());
//            throw e;
//        }finally {
//            //@After
//            log.info("[트랜잭션 릴리즈] {}",  joinPoint.getSignature());
//        }
//    }

    @Before("hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[Before] {}", joinPoint.getSignature());

    }

    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result){
        log.info("[AfterRetuning] {} return = {}", joinPoint.getSignature(), result);
    }

    //return 타입이 일치해야한다. 대신 상위 타입인 경우 받아진다 그러니 안전빵은 Object로 하자(?)
    @AfterReturning(value = "hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doAfterReturning2(JoinPoint joinPoint, String result){
        log.info("[AfterRetuning2] {} return = {}", joinPoint.getSignature(), result);
    }

    //AfterReturning과 같은 개념. 상위 exception으로만 받아진다. 그러니 안전빵은 Exception으로 하자
    @AfterThrowing(value = "hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[AfterThrowing] {} message = {} ", joinPoint.getSignature(), ex.getMessage() );

    }

    @After(value = "hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[After] {}", joinPoint.getSignature());
    }
}
