package com.lingsi.unp.exception;

/**
 * 数据库增删改没有得到预期的结果时，抛出该异常
 */
public class DMException extends RuntimeException {
    public DMException(){
        super();
    }
    public DMException(String mesg){
        super(mesg);
    }
    public DMException(String message, Throwable cause) {
        super(message, cause);
    }
    public DMException(Throwable cause) {
        super(cause);
    }

}
