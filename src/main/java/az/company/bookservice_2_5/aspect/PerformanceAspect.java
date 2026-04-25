package az.company.bookservice_2_5.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    private static final long SLOW_EXECUTION_THRESHOLD_MS = 500;

    @Around("within(az.company.bookservice_2_5.controller..*) || within(az.company.bookservice_2_5.service..*)")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        if (duration > SLOW_EXECUTION_THRESHOLD_MS) {
            log.warn("SLOW EXECUTION: {}.{}() took {} ms (threshold: {} ms)",
                    joinPoint.getSignature().getDeclaringType().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    duration,
                    SLOW_EXECUTION_THRESHOLD_MS);
        }

        return result;
    }
}
