package com.example.emissionen.accessmanagement;

import com.example.emissionen.usermanagement.User;
import com.example.emissionen.usermanagement.UserRole;
import jakarta.annotation.Priority;
import jakarta.faces.context.FacesContext;
import jakarta.interceptor.*;

@ResearcherOnly
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class ResearcherOnlyInterceptor {

    @AroundInvoke
    public Object checkAccess(InvocationContext ctx) throws Exception {

        FacesContext faces = FacesContext.getCurrentInstance();
        if (faces == null) throw new SecurityException("Kein FacesContext vorhanden.");

        User user = (User) faces.getExternalContext().getSessionMap().get("user");
        if (user == null) throw new SecurityException("Nicht eingeloggt.");

        boolean allowed = user.getRole() == UserRole.RESEARCHER || user.getRole() == UserRole.ADMIN;
        if (!allowed) throw new SecurityException("Keine Berechtigung (Researcher/Admin).");

        return ctx.proceed();
    }
}
