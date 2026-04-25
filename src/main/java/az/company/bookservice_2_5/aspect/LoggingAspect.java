package az.company.bookservice_2_5.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(az.company.bookservice_2_5.controller..*)")
    public void controllerMethods() {
    }

    @Pointcut("within(az.company.bookservice_2_5.service..*)")
    public void serviceMethods() {
    }

    @Pointcut("within(az.company.bookservice_2_5.scheduler..*)")
    public void schedulerMethods() {
    }

    @Around("controllerMethods()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Controller >> {}.{}() called with args: {}", className, methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.info("Controller << {}.{}() completed in {} ms", className, methodName, duration);

        return result;
    }

    @Around("serviceMethods()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.debug("Service >> {}.{}() called with args: {}", className, methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.debug("Service << {}.{}() completed in {} ms", className, methodName, duration);

        return result;
    }

    @Around("schedulerMethods()")
    public Object logScheduler(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Scheduler >> {}.{}() started", className, methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        log.info("Scheduler << {}.{}() completed in {} ms", className, methodName, duration);

        return result;
    }

    @AfterThrowing(pointcut = "controllerMethods() || serviceMethods() || schedulerMethods()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.error("Exception in {}.{}(): {} - {}", className, methodName,
                exception.getClass().getSimpleName(), exception.getMessage());
    }
}
