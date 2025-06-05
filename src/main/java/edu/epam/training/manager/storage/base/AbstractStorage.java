package edu.epam.training.manager.storage.base;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import edu.epam.training.manager.utils.data.JsonDataLoader;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;

@Setter
public abstract class AbstractStorage<T extends BaseEntity<ID>, ID> implements
        BaseStorage<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStorage.class);

    protected final Map<ID, T> storage = new ConcurrentHashMap<>();
    protected JsonDataLoader jsonDataLoader;
    protected String initFilePath;
    protected Class<T> itemClass;

    @PostConstruct
    public void init() {
        if (initFilePath == null || initFilePath.trim().isEmpty()
                || (initFilePath.startsWith("${") && initFilePath.endsWith("}"))) {
            LOGGER.debug("initFilePath not set for {} – skipping JSON initialization",
                    getClass().getSimpleName());
            return;
        }

        if (itemClass == null) {
            throw new IllegalStateException("itemClass is not set for " + getClass().getSimpleName());
        }

        LOGGER.info("Starting initialization of {} from file {}", getClass().getSimpleName(), initFilePath);
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(initFilePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found: " + initFilePath);
            }

            List<T> items = jsonDataLoader.loadData(is, itemClass);
            for (T item : items) {
                storage.put(item.getId(), item);
                LOGGER.debug("Loaded {} item with ID={}", getClass().getSimpleName(), item.getId());
            }

            LOGGER.info("Successfully initialized {}: loaded {} records",
                    getClass().getSimpleName(), items.size());
        } catch (Exception e) {
            LOGGER.error("Failed to initialize {} from {}: {}",
                    getClass().getSimpleName(), initFilePath, e.getMessage(), e);

            throw new IllegalStateException("Failed to initialize storage from " + initFilePath, e);
        }
    }

    @Override
    public void create(T item) {
        storage.put(item.getId(), item);
    }

    @Override
    public T findById(ID id) {
        return storage.get(id);
    }

    @Override
    public void update(T item) {
        storage.put(item.getId(), item);
    }

    @Override
    public void delete(ID id) {
        storage.remove(id);
    }
}
