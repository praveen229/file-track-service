package com.qcom.search.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ValidationError {

    private final List<ErrorResponse> fieldErrors = new ArrayList<>();

    public ValidationError() {

    }
    public void addFieldError(String path, String message) {
    	ErrorResponse error = new ErrorResponse(path, message);
        fieldErrors.add(error);
    }

    public List<ErrorResponse> getFieldErrors() {
        return Collections.unmodifiableList(fieldErrors);
    }
}