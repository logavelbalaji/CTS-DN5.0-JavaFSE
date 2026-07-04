import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingAspect {
    @Before("execution(* BookService.printBookDetails(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before executing method: " + joinPoint.getSignature().getName());
    }

    @After("execution(* BookService.printBookDetails(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After executing method: " + joinPoint.getSignature().getName());
    }
}
