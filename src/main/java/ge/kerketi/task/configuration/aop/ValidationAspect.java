package ge.kerketi.task.configuration.aop;

import ge.kerketi.task.exception.GeneralException;
import ge.kerketi.task.utils.CCY;
import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;

import static ge.kerketi.task.exception.ErrorMessage.*;


@Aspect
@Component
public class ValidationAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationAspect.class);

    /**
     * @param joinPoint trigger of any request on Controller package
     *                  log request info
     */
    @SneakyThrows
    @Before("execution(* ge.kerketi.task.controller.*.*(..))")
    public void preLoggerAndValidator(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        Object[] args = joinPoint.getArgs();

        if (request != null) {
            LOGGER.info("=============Received Http Request============");
            LOGGER.info("Http Method = {}", request.getMethod());
            LOGGER.info("URI = {}", request.getRequestURI());
            LOGGER.info("CLASS Method = {}", joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName());
            LOGGER.info("ARGS = {}", Arrays.toString(args));
            LOGGER.info("REQUESTER IP = {}", request.getRemoteAddr());
        }

        switch (joinPoint.getSignature().getName()) {
            case "transfer":
                validateTransfer((String) args[0], (String) args[1], (BigDecimal) args[2], (String) args[3]);
                LOGGER.info("validateTransfer Validated Successfully!");
                break;
            case "cashIn":
                validateCashIn((String) args[0], (String) args[1], (BigDecimal) args[2], (String) args[3]);
                LOGGER.info("validateCashIn Validated Successfully!");
                break;
        }

    }

    public void validateTransfer(String fromAccount, String toAccount, BigDecimal amount, String walletType) {

        if (StringUtils.isEmpty(fromAccount) || StringUtils.isEmpty(toAccount)) {
            throw new GeneralException(ACCOUNT_NUMBER_MUST_NOT_BE_EMPTY);
        }

        if (fromAccount.equals(toAccount)) {
            throw new GeneralException(SAME_ACCOUNTS_ERROR);
        }

        if (StringUtils.isEmpty(walletType) || !CCY.contains(walletType)) {
            throw new GeneralException(INCRRECT_CCY_ERROR);
        }

        if (StringUtils.isEmpty(amount) || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new GeneralException(AMOUNT_MUST_BE_MORE_THAN_ZERO);
        }

    }

    public void validateCashIn(String pid, String accountNumber, BigDecimal amount, String walletType) {

        if (StringUtils.isEmpty(walletType) || !CCY.contains(walletType)) {
            throw new GeneralException(INCRRECT_CCY_ERROR);
        }

        if (StringUtils.isEmpty(amount) || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new GeneralException(AMOUNT_MUST_BE_MORE_THAN_ZERO);
        }

        if (StringUtils.isEmpty(pid)) {
            throw new GeneralException(PID_MUST_NOT_BE_EMPTY);
        }

        if (StringUtils.isEmpty(accountNumber)) {
            throw new GeneralException(ACCOUNT_NUMBER_MUST_NOT_BE_EMPTY);
        }

    }


}