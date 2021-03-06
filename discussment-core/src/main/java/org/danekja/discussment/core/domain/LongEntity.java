package org.danekja.discussment.core.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The class is a base entity for domain objects with Long PK.
 *
 * @author Jakub Danek
 */
@MappedSuperclass
public abstract class LongEntity extends BaseEntity<Long> {

    protected LongEntity(Long id) {
        super(id);
    }

    protected LongEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public Long getId() {
        return super.getId();
    }

}
