package fitwf.log;

import fitwf.log.annotation.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AOPLogger {
    private final Logger logger = LoggerFactory.getLogger(AOPLogger.class);

    @Around("@annotation(annotationLog)")
    public Object around(ProceedingJoinPoint point, Log annotationLog) throws Throwable {
        String methodName = point.getSignature().toShortString();
        Object proceedingObject = null;
        switch (annotationLog.value()) {
            case ERROR:
                logError(methodName, getException(point));
                break;
            case WARN:
                logWarn(methodName, getException(point));
                break;
            case DEBUG:
                long startTime = System.currentTimeMillis();
                proceedingObject = point.proceed();
                long passedTime = System.currentTimeMillis() - startTime;
                logDebug(methodName, point.getArgs(), passedTime);
                break;
            case INFO:
                logInfo(methodName, point.getArgs());
                break;
        }
        return proceedingObject == null ? point.proceed() : proceedingObject;
    }

    private void logInfo(String methodName, Object[] args) {
        logger.info("Method = {} executed with args = {}", methodName, args);
    }

    private void logError(String methodName, Exception ex) {
        if (ex != null) {
            logger.error("Handler = {} got exception with message = {}", methodName, ex.getMessage());
        } else {
            logger.error("Some exception in method = {}", methodName);
        }
    }

    private void logWarn(String methodName, Exception ex) {
        if (ex != null) {
            logger.warn("Handler = {} got exception in method = {} with message = {}", methodName, ex.getStackTrace()[0], ex.getMessage());
        } else {
            logger.warn("Some exception in method = {}", methodName);
        }
    }

    private void logDebug(String methodName, Object[] args, long passedTime) {
        logger.debug("Method = {} executed in = {}ms with args = {}", methodName, passedTime, args);
    }

    private Exception getException(ProceedingJoinPoint point) {
        for (Object arg : point.getArgs()) {
            if (arg instanceof Exception) {
                return (Exception) arg;
            }
        }
        return null;
    }

}