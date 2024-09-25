package com.password926.agijagi.record.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class RecordTypeConverter implements AttributeConverter<RecordType, String> {

    @Override
    public String convertToDatabaseColumn(RecordType attribute) {
        return attribute.getDesc();
    }

    @Override
    public RecordType convertToEntityAttribute(String dbData) {
        return RecordType.of(dbData);
    }
}
