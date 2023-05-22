package edu.miu.eafinalproject.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ByteArrayDeserializer extends StdDeserializer<byte[]> {

    public ByteArrayDeserializer() {
        super(byte[].class);
    }

    @Override
    public byte[] deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return jsonParser.getBinaryValue();
    }
}

