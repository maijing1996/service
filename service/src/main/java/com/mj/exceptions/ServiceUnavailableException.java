package com.mj.exceptions;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import org.springframework.stereotype.Service;

/**
 * Created by mj on 2018-05-07.
 */
@Service
public class ServiceUnavailableException extends BusinessException{
    private ErrorCode errorCode;
    private ErrorMessage errorMessage;

    public ServiceUnavailableException() {
        super();
    }

    public ServiceUnavailableException(ErrorCode errorCode, ErrorMessage errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode()
    {
        return errorCode.getCode();
    }
    public String getErrorMessage() {
        return errorMessage.getMessage();
    }
}
