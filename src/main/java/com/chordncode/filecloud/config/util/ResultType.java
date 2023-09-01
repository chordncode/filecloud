package com.chordncode.filecloud.config.util;

public enum ResultType {
    SUCCESS(1),
    FAILED(0),
    ERROR(-1);
 
    private int result;
    ResultType(int result) {
        this.result = result;
    }

    public int getResult() {
        return this.result;
    }
}
