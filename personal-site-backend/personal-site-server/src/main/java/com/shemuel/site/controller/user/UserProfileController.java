//package com.shemuel.site.controller.user;
//
//import cn.dev33.satoken.stp.StpUtil;
//import cn.dev33.satoken.util.SaResult;
//import com.shemuel.site.dto.UserPasswordUpdateDTO;
//import com.shemuel.site.service.AuthService;
//import com.shemuel.site.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * <p>
// *  前端控制器
// * </p>
// *
// * @author 邓绍祥
// * @since 2025-03-14
// */
//@RestController
//@RequestMapping("/api/user")
//@RequiredArgsConstructor
//public class UserProfileController {
//
//    private final UserService userService;
//    private final AuthService authService;
//    @PostMapping("/password/update")
//    public SaResult updatePassword(@RequestBody @Validated UserPasswordUpdateDTO userLoginDTO) {
//        UserProfile userProfile = authService.checkPassword(StpUtil.getTokenInfo().getLoginId().toString(), userLoginDTO.getPassword());
//        authService.updatePassword(userProfile, userLoginDTO.getNewPassword());
//        StpUtil.logout();
//        return SaResult.data("ok");
//    }
//}
