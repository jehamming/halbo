// Copyright 2012 Mitchell Kember. Subject to the MIT License.

package com.hamming.halbo.client.viewer;

import com.hamming.halbo.model.dto.UserLocationDto;

/**
 * A container for input that must be handled by ViewState. This creates a layer
 * of abstraction between the actual means of input (which key, which mouse button,
 * etc.) and what type of input is actual required for ViewState to perform
 * specific actions.
 * 
 * @author Mitchell Kember
 * @since 09/12/2011
 */
final class ViewStateInputData {
    
    private static final float DEFAULT_LOOK_SENSITIVITY = 1.f / 10.f;
    private static float lookSensitivity = DEFAULT_LOOK_SENSITIVITY;

    
    final boolean jump;
    
    final float lookDeltaX;
    final float lookDeltaY;
    
    final int cycleBlock;
    
    final boolean breakBlock;
    final boolean placeBlock;

    final UserLocationDto location;
    
    /**
     * Creates a new ViewStateInputData, initializing all fields.
     * 
     * @param jump if the Player should jump
     * @param lookDeltaX the distance along the x-axis the Player has shifted its gaze
     * @param lookDeltaY the distance along the y-axis the Player has shifted its gaze
     * @param cycleBlock how many times the Player should cycle the block being held (wraps around)
     * @param placeBlock if the Player should place a block
     */
    ViewStateInputData(UserLocationDto loc, boolean jump, float lookDeltaX, float lookDeltaY, int cycleBlock, boolean breakBlock, boolean placeBlock) {
        this.location = loc;
        this.jump = jump;
        this.lookDeltaX = lookDeltaX * lookSensitivity;
        this.lookDeltaY = lookDeltaY * lookSensitivity;
        this.cycleBlock = cycleBlock;
        
        // You can't break and place a block at the same time!
        if (breakBlock && placeBlock) {
            throw new IllegalArgumentException();
        }
        this.breakBlock = breakBlock;
        this.placeBlock = placeBlock;
    }
    
    /**
     * Changes the look sensitivity. Smaller values will cause the view to pan
     * around more slowly; for example if the mouse is used, a very small
     * value would require lifting the mouse several times to turn around. Larger
     * cause the view to pan around more quickly.
     * 
     * @param lookSensitivity the new look sensitivity
     */
    static void setLookSensitivity(float lookSensitivity) {
        ViewStateInputData.lookSensitivity = lookSensitivity * DEFAULT_LOOK_SENSITIVITY;
    }
}
