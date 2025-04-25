package com.mateusz.zuk.currency.exception.report;

import java.io.IOException;

public class ReportGenerationException extends RuntimeException {
    public ReportGenerationException(String message) {
        super(message);
    }
    public ReportGenerationException(String message, IOException cause) {
        super(message, cause);
    }
}
