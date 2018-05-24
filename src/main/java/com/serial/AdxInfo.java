package com.serial;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.msgpack.annotation.Index;
import org.msgpack.annotation.Message;

@Message
public class AdxInfo implements Serializable {

    private static final long serialVersionUID = 1593557348340529841L;

    /**
     * 广告平台
     */
    @Index(0)
    private DspType dspType;

    /**
     * 应用ID
     */
    @Index(1)
    private Integer appId;

    public DspType getDspType() {
        return dspType;
    }

    public void setDspType(DspType dspType) {
        this.dspType = dspType;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
