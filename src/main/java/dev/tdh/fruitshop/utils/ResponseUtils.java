package dev.tdh.fruitshop.utils;

import dev.tdh.fruitshop.model.response.ResponseDTO;

public class ResponseUtils {

    public static ResponseDTO makeResponse(Object data, String message, String detail) {
        ResponseDTO dto = new ResponseDTO();
        dto.setData(data);
        dto.setMessage(message);
        dto.setDetail(detail);
        return dto;
    }
}
