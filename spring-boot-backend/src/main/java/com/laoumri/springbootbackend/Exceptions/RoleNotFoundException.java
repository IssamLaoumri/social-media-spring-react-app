package com.laoumri.springbootbackend.Exceptions;

import com.laoumri.springbootbackend.enums.ERole;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super("Role " + role + " not found. The role name is misspelled or not available.");
    }
}
