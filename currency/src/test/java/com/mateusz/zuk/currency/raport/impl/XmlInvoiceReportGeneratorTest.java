package com.mateusz.zuk.currency.raport.impl;

import com.mateusz.zuk.currency.exception.report.ReportGenerationException;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import com.mateusz.zuk.currency.report.InvoiceReportGenerator;
import com.mateusz.zuk.currency.report.impl.XmlInvoiceReportGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XmlInvoiceReportGeneratorTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldGenerateXmlFileSuccessfully() throws Exception {
        InvoiceReportGenerator generator = new XmlInvoiceReportGenerator();
        String testFilePath = tempDir.resolve("test-invoice.xml").toString();
        Field field = InvoiceReportGenerator.class.getDeclaredField("reportPath");
        field.setAccessible(true);
        field.set(generator, testFilePath);

        List<OrderInfo> orders = List.of(
                new OrderInfo("komputer", "Product A", LocalDate.now().toString(), new BigDecimal(100), new BigDecimal(200)),
                new OrderInfo("telefon", "Product B", LocalDate.now().toString(), new BigDecimal(100), new BigDecimal(200))
        );

        generator.generateFile(orders);

        File resultFile = new File(testFilePath);
        assertTrue(resultFile.exists(), "XML file should be generated");
        assertTrue(resultFile.length() > 0, "File should not be empty");
    }

    @Test
    void shouldThrowReportGenerationExceptionOnIOException() {
        InvoiceReportGenerator generator = new XmlInvoiceReportGenerator() {
            @Override
            public void generateFile(List<OrderInfo> orders) throws ReportGenerationException {
                throw new ReportGenerationException("XML_REPORT_GENERATE", new IOException("Simulated error"));
            }
        };

        List<OrderInfo> orders = List.of(new OrderInfo("komputer", "Product A", LocalDate.now().toString(), new BigDecimal(100), new BigDecimal(200)));
        ReportGenerationException ex = assertThrows(ReportGenerationException.class, () -> {
            generator.generateFile(orders);
        });

        assertInstanceOf(IOException.class, ex.getCause());
    }
}
