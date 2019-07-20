package com.lingsi.unp.utils.auth;

import com.lingsi.unp.model.auth.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static AppUser getCurrentUser() {
        return (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
