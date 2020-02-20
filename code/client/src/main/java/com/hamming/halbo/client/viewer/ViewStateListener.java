// Copyright 2012 Mitchell Kember. Subject to the MIT License.

package com.hamming.halbo.client.viewer;

/**
 * Classes that listen and respond to changes in the ViewState must implement
 * this interface. This allows them to be notified when Chunks are modified.
 * 
 * @author Michell Kember
 * @since 10/12/2011
 */
interface ViewStateListener {
    void viewStateChunkChanged(Chunk chunk);
}
