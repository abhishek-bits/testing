package service;

import converter.MyConverter;
import lombok.RequiredArgsConstructor;
import repository.MyRepository;

import java.util.UUID;

@RequiredArgsConstructor
public class MyService {

    private final MyRepository repository;
    private final MyConverter converter;

    public String getAsJsonString(UUID id) {
        Object object = repository.getById(id);
        return converter.toJson(object);
    }
}
