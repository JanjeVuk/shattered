package net.etum.shattered.builder

import java.io.Serializable
import java.util.*

class DamageSource(type: DamageType?, source: String?) : Serializable {
    enum class DamageType {
        PHYSICAL,
        FIRE,
        POISON,
        THUNDER,
        MAGIC,  // Ajoutez d'autres types de dégâts si nécessaire
    }

    private val type: DamageType
    private val source: String

    init {
        require(!(type == null || source.isNullOrEmpty())) { "Type and source cannot be null or empty" }
        this.type = type
        this.source = source
    }

    val isFire: Boolean
        get() = this.type == DamageType.FIRE

    val isPhysical: Boolean
        get() = this.type == DamageType.PHYSICAL

    val isPoison: Boolean
        get() = this.type == DamageType.POISON

    val isMagic: Boolean
        get() = this.type == DamageType.MAGIC

    val isThunder: Boolean
        get() = this.type == DamageType.THUNDER

    fun isDamageType(damageType: DamageType): Boolean {
        return this.type == damageType
    }

    override fun toString(): String {
        return "DamageSource{" +
                "type=" + type +
                ", source='" + source + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as DamageSource
        return type == that.type && source == that.source
    }

    override fun hashCode(): Int {
        return Objects.hash(type, source)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}