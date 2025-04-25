package com.mateusz.zuk.currency.convereter.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusz.zuk.currency.convereter.InputDataConverter;
import com.mateusz.zuk.currency.exception.loader.DataLoadingException;
import com.mateusz.zuk.currency.model.dto.OrderInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mateusz.zuk.currency.exception.ErrorMessage.CONVERT_JSON_FROM_FILE;
import static com.mateusz.zuk.currency.exception.ErrorMessage.CONVERT_JSON_FROM_STRING;

@Component("jsonDataConverter")
@RequiredArgsConstructor
public class JsonDataConverter implements InputDataConverter {
    private final ObjectMapper objectMapper;

    @Override
    public List<OrderInput> convert(String content) throws DataLoadingException {
        try {
            return objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new DataLoadingException(CONVERT_JSON_FROM_STRING, e);
        }
    }

    @Override
    public List<OrderInput> convert(File file) throws DataLoadingException {
        try {
            return objectMapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new DataLoadingException(CONVERT_JSON_FROM_FILE, e);
        }
    }
}


