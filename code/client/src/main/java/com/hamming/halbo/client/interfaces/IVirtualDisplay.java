package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.client.controllers.MoveController;

public interface IVirtualDisplay extends IMovementListener, IConnectionListener {

    public void setMovementController(MoveController controller);
}
