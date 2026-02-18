package com.example.emissionen.accessmanagement;

import com.example.emissionen.usermanagement.User;
import jakarta.annotation.Priority;
import jakarta.faces.context.FacesContext;
import jakarta.interceptor.*;

@LoggedInOnly
@Interceptor
@Priority(Interceptor.Priority.APPLICATION) // WICHTIG: damit der Interceptor aktiv ist
public class LoggedInOnlyInterceptor {

    @AroundInvoke
    public Object checkAccess(InvocationContext ctx) throws Exception {

        FacesContext faces = FacesContext.getCurrentInstance();
        if (faces == null) {
            throw new SecurityException("Kein FacesContext vorhanden.");
        }

        User user = (User) faces.getExternalContext().getSessionMap().get("user");
        if (user == null) {
            throw new SecurityException("Nicht eingeloggt.");
        }

        return ctx.proceed();
    }
}
