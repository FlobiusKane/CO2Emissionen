package com.example.emissionen.accessmanagement;

import com.example.emissionen.usermanagement.User;
import com.example.emissionen.usermanagement.UserRole;
import jakarta.faces.context.FacesContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@ReviewerOnly
@Interceptor
public class ReviewerOnlyInterceptor {

    @AroundInvoke
    public Object checkAccess(InvocationContext ctx) throws Exception {

        FacesContext faces = FacesContext.getCurrentInstance();

        User user = (User) faces.getExternalContext()
                .getSessionMap().get("user");

        if (user == null || user.getRole() != UserRole.REVIEWER) {
            throw new SecurityException("Keine Berechtigung");
        }

        return ctx.proceed();
    }
}

