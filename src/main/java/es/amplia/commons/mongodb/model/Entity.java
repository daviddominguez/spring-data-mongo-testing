package es.amplia.commons.mongodb.model;

import com.google.common.base.Objects;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Objects.equal;

public class Entity {

    @Id
    protected ObjectId id;

    @Version
    protected Long version;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;
        Entity entity = (Entity) o;
        return equal(id, entity.id) &&
                equal(version, entity.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .omitNullValues()
                .toString();
    }
}
