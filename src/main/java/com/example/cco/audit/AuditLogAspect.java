package com.example.cco.audit;

import com.example.cco.member.MemberEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditLogAspect {
    private final AuditLogRepository auditLogRepository;
    @Around("execution(* com.example.cco..*Controller.*(..))") // 모든 컨트롤러에 적용
    public Object logUserAction(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();

        // 제외 대상 경로
        if (uri.equals("/auth/valid")) {
            return joinPoint.proceed(); // 로그 남기지 않고 바로 통과
        }

        String username = getCurrentUserId(); // SecurityContext에서 유저명 추출

        String method = request.getMethod();

        String clientIp = request.getRemoteAddr();
        String requestBody = extractRequestBody(joinPoint);

        AuditLogEntity log = new AuditLogEntity();
        log.setUsername(username);
        log.setAction(joinPoint.getSignature().getName());
        log.setMethod(method);
        log.setUri(uri);
        log.setClientIp(clientIp);
        log.setRequestBody(requestBody);
        log.setTimestamp(LocalDateTime.now());

        auditLogRepository.save(log); // DB에 저장

        return joinPoint.proceed(); // 원래 메서드 실행
    }
    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof MemberEntity userDetails) {
            return String.valueOf(userDetails.getUserId()); // 또는 .getId()
        }
        return "anonymous";
    }

    private String extractRequestBody(ProceedingJoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            ObjectMapper mapper = new ObjectMapper();
            return Arrays.stream(args)
                    .filter(arg -> !(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse))
                    .map(arg -> {
                        try {
                            return mapper.writeValueAsString(arg);
                        } catch (JsonProcessingException e) {
                            return "unserializable";
                        }
                    })
                    .collect(Collectors.joining(", "));
        } catch (Exception e) {
            return "failed to extract";
        }
    }
}
