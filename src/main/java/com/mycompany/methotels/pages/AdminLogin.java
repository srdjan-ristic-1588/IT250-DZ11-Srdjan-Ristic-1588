/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.methotels.pages;

import com.mycompany.methotels.entities.Admin;
import com.mycompany.methotels.interfaces.AdminDAO;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 *
 * @author BASKETBALL IN HEART
 */
public class AdminLogin {

    @Inject
    private AdminDAO userDao;
    @Property
    private Admin userLogin;
    @SessionState
    private Admin loggedInUser;
    @Component
    private BeanEditForm form;

    Object onActivate() {
        if (loggedInUser.getUsername() != null) {
            return AdminPanel.class;
        }
        return null;
    }

    public String getMD5Hash(String yourString) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(yourString.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }

    Object onSuccess() {
        String password = getMD5Hash(userLogin.getPassword());
        System.out.println(password);
        Admin u = userDao.checkAdmin(userLogin.getUsername(), password);
        if (u != null) {
            loggedInUser = u;
            System.out.println("Logovan");
            return AdminPanel.class;
        } else {
            form.recordError("Uneli ste pogre�ne parametre");
            System.out.println("losi parametri");
            return null;
        }
    }
}
