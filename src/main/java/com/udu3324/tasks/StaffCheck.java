package com.udu3324.tasks;

import com.udu3324.main.Data;
import net.dv8tion.jda.api.entities.Member;

public class StaffCheck {
    public static boolean isStaffMember(Member author) {
        if (author == null) {
            return false;
        }
        String userRoles = String.valueOf(author.getRoles());
        return userRoles.contains(Data.staffRoleID);
    }
}
