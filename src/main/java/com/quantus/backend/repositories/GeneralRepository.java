package com.quantus.backend.repositories;

import com.quantus.backend.models.GeneralModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import java.io.Serializable;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-24
 */

@NoRepositoryBean
public interface GeneralRepository<T extends GeneralModel, ID extends Serializable>
        extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     *
     * @param entity
     * Note: All operations in this application should use a logical delete for data integrity.
     * All junction tables use delete() even though they extend the GeneralModel entity and
     * will have fields that correspond to the abstract entity.
     */
    default void logicalDelete(T entity) {
        entity.setIsDeleted(true);
        this.save(entity);
    }

    /**
     *
     * @param entities
     */
    default void logicalDeleteAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            entity.setIsDeleted(true);
        }
        this.saveAll(entities);
    }

    /**
     * Retrieve an object T that is not deleted
     * Note: Always use the generalRepository.getOne() function otherwise unexpectred results will occur.
     */
    default T getOne(ID id) {
        return this.findById(id)
                .filter(entity -> !entity.getIsDeleted())
                .orElse(null);
    }

    /**
     * Retrieve a list of object T that is not deleted
     */
    default List<T> getAll() {
        return this.findAll().stream()
                .filter(entity -> !entity.getIsDeleted())
                .toList();
    }
}
