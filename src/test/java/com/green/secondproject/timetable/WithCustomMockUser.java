//package com.green.secondproject.timetable;
//
//import com.green.secondproject.config.security.model.MyUserDetails;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithSecurityContext;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.util.Arrays;
//
//@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
//public @interface WithCustomMockUser {
//    String userId() default "2000";
//    String email() default "cc@gmail.com";
//    String pw() default "123";
//    String nm() default "남노성석";
//    String schoolNm() default "오성고등학교";
//    String grade() default "1";
//    String classNum() default "1";
//    String pic() default "pic.jpg";
//
//}
//
//public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {
//    @Override
//    public SecurityContext createSecurityContext(WithCustomMockUser user) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
////        MyUserDetails mockStudentDetails = new MyUserDetails();
//        Authentication auth = new UsernamePasswordAuthenticationToken("", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_STD")));
//        context.setAuthentication(auth);
//        return context;
//    }
//
//    }
