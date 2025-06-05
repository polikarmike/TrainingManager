package edu.epam.training.manager.utils.data;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Setter
public class JsonDataLoader {
    private ObjectMapper objectMapper;

    public <T> List<T> loadData(InputStream inputStream, Class<T> itemClass) throws IOException {
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, itemClass);
        return objectMapper.readValue(inputStream, type);
    }
}
