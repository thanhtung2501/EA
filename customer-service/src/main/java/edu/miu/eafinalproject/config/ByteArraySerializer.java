package edu.miu.eafinalproject.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ByteArraySerializer extends StdSerializer<byte[]> {

    public ByteArraySerializer() {
        super(byte[].class);
    }

    @Override
    public void serialize(byte[] value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
        jsonGenerator.writeBinary(value);
    }
}

