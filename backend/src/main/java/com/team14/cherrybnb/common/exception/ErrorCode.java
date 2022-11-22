package com.team14.cherrybnb.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    NO_SUCH_MEMBER(HttpStatus.NOT_FOUND, "사용자 정보가 존재하지 않습니다."),
    NO_SUCH_ROOM(HttpStatus.NOT_FOUND, "해당하는 숙소 정보가 존재하지 않습니다."),
    NO_SUCH_RESERVATION(HttpStatus.NOT_FOUND, "예약 정보를 찾을 수가 없습니다."),
    NO_SUCH_WISH(HttpStatus.NOT_FOUND, "위시 정보를 찾을 수가 없습니다.");

    final HttpStatus status;
    final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
