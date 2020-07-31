package com.lch.ssm.config;

import com.lch.ssm.util.CrowdUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author lch
 * @create 2020-08-01 1:15
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return CrowdUtil.MD5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String s1 = CrowdUtil.MD5(charSequence.toString());
        return Objects.equals(s1,s);
    }
}
