package com.lch.ssm.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author lch
 * @create 2020-07-31 16:31
 */
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return CrowdUtil.MD5(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String result = CrowdUtil.MD5(charSequence.toString());
        return Objects.equals(result,s);
    }
}
