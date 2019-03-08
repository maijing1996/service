package com.mj.exceptions;

import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by mj on 2018-05-07.
 */
@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class ServiceUnavailableException extends BusinessException{
    private ErrorCode errorCode;
    private ErrorMessage errorMessage;

}
