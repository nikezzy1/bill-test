package com.lingsi.unp.exception;


/** 
 * 
 * @author wuzu
 * @date 2018/11/05
 * @version 1.0 
 */
public class HttpProcessException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -2749168865492921426L;

    public HttpProcessException(Exception e){
        super(e);
    }

    /**
     * @param msg
     */
    public HttpProcessException(String msg) {
        super(msg);
    }
    
    /**
     * @param message
     * @param e
     */
    public HttpProcessException(String message, Exception e) {
        super(message, e);
    }
    
}
