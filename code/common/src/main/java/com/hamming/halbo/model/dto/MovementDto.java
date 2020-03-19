package com.hamming.halbo.model.dto;

import com.hamming.halbo.game.Protocol;
import com.hamming.halbo.util.StringUtils;

public class MovementDto implements DTO {
    private long sequence;
    private boolean forward, back, left, right;


    public MovementDto(long sequence, boolean forward, boolean back, boolean left, boolean right) {
        this.sequence = sequence;
        this.forward = forward;
        this.back = back;
        this.left =left;
        this.right = right;
    }

    @Override
    public String toNetData() {
        String cmd = sequence+ StringUtils.delimiter
                + forward + StringUtils.delimiter
                + back + StringUtils.delimiter
                + left + StringUtils.delimiter
                + right ;
        return cmd;
    }

    @Override
    public void setValues(String... values) {
        if ( values.length == 5) {
            sequence = Long.valueOf(values[0]);
            forward = Boolean.valueOf(values[1]);
            back = Boolean.valueOf(values[2]);
            left = Boolean.valueOf(values[3]);
            right = Boolean.valueOf(values[4]);
        }
    }

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public boolean isForward() {
        return forward;
    }

    public void setForward(boolean forward) {
        this.forward = forward;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
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
}
