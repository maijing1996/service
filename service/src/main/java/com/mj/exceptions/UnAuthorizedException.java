package com.mj.exceptions;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 */
@Data
@Service
@NoArgsConstructor
@AllArgsConstructor
public class UnAuthorizedException extends BusinessException{
    private ErrorCode errorCode;
    private ErrorMessage errorMessage;

}
