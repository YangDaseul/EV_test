package com.genesis.app.comm.net;


import java.util.List;

/**
 * @brief 네트워크 결과
 * @see #getCode() 상태 코드 {@link NetStatusCode}
 * @see #getMseeage() 특이사항에 대한 메시지
 * @see #getData() 요청 결과에 대한 데이터
 */
public class NetResult {
    private NetStatusCode code;
    private int mseeage;
    private Object data;

    public NetResult(NetStatusCode code, int message, Object data) {
        this.code = code;
        this.mseeage = message;
        this.data = data;
    }

    public NetStatusCode getCode() {
        return code;
    }

    public void setCode(NetStatusCode code) {
        this.code = code;
    }

    public int getMseeage() {
        return mseeage;
    }

    public void setMseeage(int mseeage) {
        this.mseeage = mseeage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}