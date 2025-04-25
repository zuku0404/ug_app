package com.mateusz.zuk.currency.report;

import com.mateusz.zuk.currency.exception.report.ReportGenerationException;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;


public abstract class InvoiceReportGenerator {
    @Value("${path.report.file}")
    protected String reportPath;

    public abstract void generateFile(List<OrderInfo> orders) throws ReportGenerationException;
}
