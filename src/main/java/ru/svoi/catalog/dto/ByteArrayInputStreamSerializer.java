package ru.svoi.catalog.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.io.ByteArrayResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayInputStreamSerializer extends JsonSerializer<ByteArrayInputStream> {

    @Override
    public void serialize(ByteArrayInputStream value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        byte[] data = value.readAllBytes();
        ByteArrayResource resource = new ByteArrayResource(data);

        serializers.defaultSerializeValue(resource, gen);
    }
}

