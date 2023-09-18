package ru.svoi.catalog.dto;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public class ByteArrayResourceSerializer extends JsonSerializer<ByteArrayResource> {

    @Override
    public void serialize(ByteArrayResource value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        byte[] data = value.getByteArray();

        gen.writeBinary(data);
    }
}

