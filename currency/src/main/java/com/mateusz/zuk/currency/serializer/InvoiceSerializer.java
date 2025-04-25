package com.mateusz.zuk.currency.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mateusz.zuk.currency.model.Invoice;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.List;

@JsonComponent
public class InvoiceSerializer extends StdSerializer<Invoice> {
    public InvoiceSerializer() {
        this(null);
    }

    public InvoiceSerializer(Class<Invoice> t) {
        super(t);
    }

    @Override
    public void serialize(Invoice value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        List<OrderInfo> entries = value.orders();
        for (OrderInfo entry : entries) {
            gen.writeObjectFieldStart(entry.product());
            gen.writeStringField("nazwa", entry.productName());
            gen.writeStringField("data_ksiegowania", entry.postDate());
            gen.writeNumberField("koszt_USD", entry.costUsd());
            gen.writeNumberField("koszt_PLN", entry.costPln());
            gen.writeEndObject();
        }
        gen.writeEndObject();
    }
}
