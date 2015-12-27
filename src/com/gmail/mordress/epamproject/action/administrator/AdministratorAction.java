package com.gmail.mordress.epamproject.action.administrator;

import com.gmail.mordress.epamproject.action.Action;
import com.gmail.mordress.epamproject.entities.Role;

public abstract class AdministratorAction extends Action{
    public AdministratorAction() {
        getAllowRoles().add(Role.ADMINISTRATOR);
    }
}
