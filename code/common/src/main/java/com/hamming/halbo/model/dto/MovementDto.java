package com.hamming.halbo.model.dto;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class MovementDto implements DTO {
    private long sequence;
    private boolean forward, back, left, right, buildMode;


    public MovementDto(long sequence, boolean forward, boolean back, boolean left, boolean right, boolean buildMode) {
        this.sequence = sequence;
        this.forward = forward;
        this.back = back;
        this.left =left;
        this.right = right;
        this.buildMode = buildMode;
    }

    @Override
    public String toNetData() {
        String cmd = sequence+ StringUtils.delimiter
                + forward + StringUtils.delimiter
                + back + StringUtils.delimiter
                + left + StringUtils.delimiter
                + right + StringUtils.delimiter
                + buildMode;
        return cmd;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 6) {
            sequence = Long.valueOf(values[0]);
            forward = Boolean.valueOf(values[1]);
            back = Boolean.valueOf(values[2]);
            left = Boolean.valueOf(values[3]);
            right = Boolean.valueOf(values[4]);
            buildMode = Boolean.valueOf(values[5]);
        }
    }

    public long getSequence() {
        return sequence;
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isBack() {
        return back;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isBuildMode() {
        return buildMode;
    }
}
