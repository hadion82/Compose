

package com.example.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * An [androidx.datastore.core.Serializer] for the [UserPreferences] proto.
 */
class CharacterProtoSerializer @Inject constructor() : Serializer<CharacterProto> {
    override val defaultValue: CharacterProto = CharacterProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CharacterProto =
        try {
            // readFrom is already called on the data store background thread
            CharacterProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: CharacterProto, output: OutputStream) {
        // writeTo is already called on the data store background thread
        t.writeTo(output)
    }
}
