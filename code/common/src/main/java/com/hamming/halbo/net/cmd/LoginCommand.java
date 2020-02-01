package com.hamming.halbo.net.cmd;

import com.hamming.halbo.util.StringUtils;

public class LoginCommand extends AbstractCommand {

    private String username;
    private String password;

    public LoginCommand(String username, String password) {
        super("LOGIN");
        this.password = password;
        this.username = username;
    }


    public static LoginCommand valueOf(String[] array) {
        LoginCommand cmd = null;
        if ( array.length == 2 ) {
            String username = array[0];
            String password = array[1];
            cmd = new LoginCommand(username, password);
        }
        return cmd;
    }

    @Override
    public String toNETCode() {
        return StringUtils.combineValuesToString(getShortName(),username, password);
    }
}
