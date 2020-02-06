package com.hamming.halbo.net;

import com.hamming.halbo.game.Protocol;

public interface CommandReceiver {

    public void receiveCommand(Protocol.Command cmd, String[] data);
}
