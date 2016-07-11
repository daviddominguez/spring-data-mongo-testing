package es.amplia.commons.mongodb.builders;

import es.amplia.commons.mongodb.model.Entity;
import org.bson.types.ObjectId;

public abstract class EntityBuilder {

    private final Entity entity;

    protected EntityBuilder() {
        entity = instantiateConcreteEntity();
    }

    protected Entity getEntity() {
        return entity;
    }

    protected abstract Entity instantiateConcreteEntity();

    public final Entity build() {
        return getEntity();
    }

    public EntityBuilder id(ObjectId id) {
        entity.setId(id);
        return this;
    }

    public EntityBuilder version(Long version) {
        entity.setVersion(version);
        return this;
    }
}
