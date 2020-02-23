// Copyright 2012 Mitchell Kember. Subject to the MIT License.

package com.hamming.halbo.client.viewer;

/**
 * Chunk represents a chunk of 16 by 16 by 16 blocks in the Mycraft world.
 * Each block uses one byte to represent its type, totaling 4 kilobytes to
 * store the information for one Chunk.
 *
 * @author Mitchell Kember
 * @since 09/12/2011
 */
final class Chunk {


    final int width;
    final int length;
    final int height = 100;
    /**
     * This 3D array stores all the types of the blocks in this Chunk. It is in
     * the following format:
     * <p>
     * {@code data[x][y][z]}
     */
    private byte[][][] data;


    Chunk(int width, int length) {
        this.width = width;
        this.length = length;
        initChunck();
    }

    private void initChunck() {
        data = new byte[width][height][length];
        // Place a ground layer of blocks
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                data[x][0][z] = 1;
            }
        }
    }


    /**
     * Gets this Chunk's data array.
     *
     * @return the data
     */
    byte[][][] getData() {
        return data;
    }

    /**
     * Set a block's type.
     *
     * @param block the location of the block
     * @param type  its new type id
     */
    void setBlockType(Block block, byte type) {
        data[block.x][block.y][block.z] = type;
    }

    /**
     * Get a block's type.
     *
     * @param block the location of the block.
     * @return its type id
     */
    byte getBlockType(Block block) {
        return data[block.x][block.y][block.z];
    }
}
