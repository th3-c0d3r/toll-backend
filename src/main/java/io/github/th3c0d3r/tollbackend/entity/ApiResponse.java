package io.github.th3c0d3r.tollbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success = true;
    private String errorCode;
    private String errorMessage;
    private T data;
    private String code;
    private String message;
    private int status;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}

