package com.team14.cherrybnb.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse<T> {

    private final T message;
}
