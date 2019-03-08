package com.mj.exceptions;


import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Created by peng on 2017/8/14.
 */
@Data
@Service
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private ErrorCode errorCode;
    private ErrorMessage errorMessage;

}
