package net.etum.shattered.builder;

import java.io.Serializable;
import java.util.Objects;

public class DamageSource implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum DamageType {
        PHYSICAL,
        FIRE,
        POISON,
        THUNDER,
        MAGIC,
        // Ajoutez d'autres types de dégâts si nécessaire
    }

    private final DamageType type;
    private final String source;

    public DamageSource(DamageType type, String source) {
        if (type == null || source == null || source.isEmpty()) {
            throw new IllegalArgumentException("Type and source cannot be null or empty");
        }
        this.type = type;
        this.source = source;
    }

    public DamageType getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public boolean isFire() {
        return this.type == DamageType.FIRE;
    }

    public boolean isPhysical() {
        return this.type == DamageType.PHYSICAL;
    }

    public boolean isPoison() {
        return this.type == DamageType.POISON;
    }

    public boolean isMagic() {
        return this.type == DamageType.MAGIC;
    }

    public boolean isThunder() {
        return this.type == DamageType.THUNDER;
    }

    public boolean isDamageType(DamageType damageType) {
        return this.type == damageType;
    }

    @Override
    public String toString() {
        return "DamageSource{" +
                "type=" + type +
                ", source='" + source + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DamageSource that = (DamageSource) o;
        return type == that.type && source.equals(that.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, source);
    }
}
