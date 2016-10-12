package org.menina.exception;

/**
 * Created by meninaChimp on 2016/10/12 0012.
 */
public class CodisNotSupportException extends Exception{

    public CodisNotSupportException(String message){
        super(message);
    }

    public CodisNotSupportException(Throwable t, String message){
        super(message, t);
    }
}
