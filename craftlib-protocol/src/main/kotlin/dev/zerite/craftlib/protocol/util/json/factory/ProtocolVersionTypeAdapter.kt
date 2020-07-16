package dev.zerite.craftlib.protocol.util.json.factory

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Converts protocol versions to and from their JSON representation.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ProtocolVersionTypeAdapter : TypeAdapter<ProtocolVersion>() {

    companion object : TypeAdapterFactory {
        /**
         * Registers our custom type adapter with the GSON instance.
         *
         * @param  gson     The GSON instance to assign this adapter to.
         * @param  type     The target class type we're reading from.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @Suppress("UNCHECKED_CAST")
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>) =
            if (ProtocolVersion::class.java.isAssignableFrom(type.rawType)) ProtocolVersionTypeAdapter() as TypeAdapter<T>
            else null
    }

    override fun write(writer: JsonWriter, version: ProtocolVersion) {
        writer.value(version.id)
    }

    override fun read(reader: JsonReader) = ProtocolVersion[reader.nextInt()]
}
