package com.mateusz.zuk.currency.loader;

import com.mateusz.zuk.currency.convereter.InputDataConverter;
import com.mateusz.zuk.currency.exception.loader.DataLoadingException;
import com.mateusz.zuk.currency.model.dto.OrderInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.mateusz.zuk.currency.exception.ErrorMessage.FLE_NOT_FOUND;
import static com.mateusz.zuk.currency.exception.ErrorMessage.LOAD_DATA_ERROR;


@Component
public class DataLoader {
    private final String path;
    private final ResourceLoader resourceLoader;
    private final InputDataConverter inputDataConverter;

    @Autowired
    public DataLoader(@Value("${path.init.file}") String path, ResourceLoader resourceLoader,
                      @Qualifier("jsonDataConverter") InputDataConverter inputDataConverter) {
        this.path = path;
        this.resourceLoader = resourceLoader;
        this.inputDataConverter = inputDataConverter;
    }

    public List<OrderInput> loadData() throws DataLoadingException {
        Resource classpathResource = resourceLoader.getResource("classpath:" + path);
        List<OrderInput> results = new ArrayList<>();
        try {
            if (classpathResource.exists()) {
                try (InputStream inputStream = classpathResource.getInputStream()) {
                    String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                    results.addAll(inputDataConverter.convert(content));
                }
            } else {
                File file = new File(path);
                if (file.exists()) {
                    results.addAll(inputDataConverter.convert(file));
                } else {
                    throw new FileNotFoundException(String.format(FLE_NOT_FOUND, path));
                }
            }
        } catch (IOException e) {
            throw new DataLoadingException(String.format(LOAD_DATA_ERROR, path), e);
        }
        return results;
    }
}
