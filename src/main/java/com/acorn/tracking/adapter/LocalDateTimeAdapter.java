package com.acorn.tracking.adapter;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        return LocalDateTime.parse(in.nextString());
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        out.value(value.toString());
    }
}
