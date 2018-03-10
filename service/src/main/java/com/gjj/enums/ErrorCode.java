package com.gjj.enums;

/**
 * Created by peng on 2017/8/14.
 */

public enum ErrorCode {
    EMPTY_USERNAME("empty_username"),
    EMPTY_PASSWORD("empty_password"),
    USERNAME_NOT_EXIST("username_not_exist"),
    ERROR_PASSWORD("error_password"),
    JSON_TO_OBJECT_ERROR("json_to_object_error"),
    USERNAME_EXIST("username_exist"),
    IMG_NOT_EMPTY("img_not_empty"),
    IMG_FORMAT_ERROR("img_format_error"),
    SAVE_IMG_ERROE("save_img_error");



    private String code;
    ErrorCode(String code) {
            this.code = code;
        }
    public String getCode() {
        return this.code;
    }
}
