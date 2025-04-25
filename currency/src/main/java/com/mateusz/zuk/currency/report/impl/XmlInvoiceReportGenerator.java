package com.mateusz.zuk.currency.report.impl;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mateusz.zuk.currency.exception.report.ReportGenerationException;
import com.mateusz.zuk.currency.report.InvoiceReportGenerator;
import com.mateusz.zuk.currency.model.Invoice;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.mateusz.zuk.currency.exception.ErrorMessage.XML_REPORT_GENERATE;

@Component("xmlInvoiceReportGenerator")
public class XmlInvoiceReportGenerator extends InvoiceReportGenerator {

    @Override
    public void generateFile(List<OrderInfo> orders) throws ReportGenerationException {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
            Invoice invoice = new Invoice(orders);
            xmlMapper.writeValue(new File(super.reportPath), invoice);

        } catch (IOException e) {
            throw new ReportGenerationException(XML_REPORT_GENERATE, e);
        }
    }
}
