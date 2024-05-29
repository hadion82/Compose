
package com.example.datastore.proto

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

class CharacterProtoSerializer @Inject constructor() : Serializer<CharacterProto> {
    override val defaultValue: CharacterProto = CharacterProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): CharacterProto =
        try {
            CharacterProto.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Invalid Protocol : \n", exception)
        }

    override suspend fun writeTo(t: CharacterProto, output: OutputStream) {
        t.writeTo(output)
    }
}
