package com.serial;

import org.msgpack.annotation.MessagePackOrdinalEnum;

@MessagePackOrdinalEnum
public enum DspType {

    BAIDU(1, 1),

    /**
     * 未知（仅用于程序兼容性）
     */
    UNKNOWN(-1, -1);

    private int code;
    private int cpType;

    DspType(int code, int cpType) {
        this.code = code;
        this.cpType = cpType;
    }

    public Integer cpType() {
        return this.cpType;
    }

    public Integer code() {
        return this.code;
    }

    public static DspType fromCode(int code) {
        for (DspType dspType : DspType.values()) {
            if (dspType.code == code) {
                return dspType;
            }
        }
        return UNKNOWN;
    }

    public static DspType fromCpType(int cpType) {
        for (DspType dspType : DspType.values()) {
            if (dspType.cpType == cpType) {
                return dspType;
            }
        }
        return UNKNOWN;
    }
}
