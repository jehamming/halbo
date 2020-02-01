package com.hamming.test.net.cmd;

import com.hamming.halbo.net.cmd.LoginCommand;
import com.hamming.halbo.util.StringUtils;
import org.junit.Test;

public class TestCommands {

    @Test
    public void testLoginCommand() {
        LoginCommand cmd = new LoginCommand("username1", "password1");
        assert cmd.toNETCode().equals("LOGIN:username1:password1");
        LoginCommand cmd2 = LoginCommand.valueOf("username1:password1".split(StringUtils.delimiter));
        assert cmd2 != null;
    }
}
