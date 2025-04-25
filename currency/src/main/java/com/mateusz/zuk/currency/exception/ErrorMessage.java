package com.mateusz.zuk.currency.exception;

public final class ErrorMessage {
    private ErrorMessage() {}
    public static final String XML_REPORT_GENERATE = "Error generating XML report";
    public static final String FLE_NOT_FOUND = "File not found '%s'";
    public static final String LOAD_DATA_ERROR = "Error loading data from '%s'";
    public static final String CONVERT_JSON_FROM_STRING = "Error converting JSON string";
    public static final String CONVERT_JSON_FROM_FILE = "Error converting JSON file";
    public static final String EXTERNAL_SERVER_ERROR = "NBP server error: '%s'";
    public static final String EXTERNAL_CLIENT_ERROR = "NBP client error: '%s'";
}
