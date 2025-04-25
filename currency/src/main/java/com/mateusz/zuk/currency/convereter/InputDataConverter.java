package com.mateusz.zuk.currency.convereter;

import com.mateusz.zuk.currency.exception.loader.DataLoadingException;
import com.mateusz.zuk.currency.model.dto.OrderInput;

import java.io.File;
import java.util.List;

public interface InputDataConverter {
    List<OrderInput> convert(String string) throws DataLoadingException;
    List<OrderInput> convert(File file) throws DataLoadingException;
}
