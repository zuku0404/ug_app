package com.mateusz.zuk.currency.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.mateusz.zuk.currency.model.dto.OrderInfo;
import com.mateusz.zuk.currency.serializer.InvoiceSerializer;

import java.util.List;

@JacksonXmlRootElement(localName = "faktura")
@JsonSerialize(using = InvoiceSerializer.class)
public record Invoice(
        List<OrderInfo> orders) {
}
