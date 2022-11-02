package com.example.android.findmybook.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.android.findmybook.model.Bookmarks
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object BookmarksSerializer : Serializer<Bookmarks> {

    override val defaultValue: Bookmarks
        get() = Bookmarks()

    override suspend fun readFrom(input: InputStream): Bookmarks {
        try {
            return Json.decodeFromString(
                deserializer = Bookmarks.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Bookmarks, output: OutputStream) =
        output.write(
            Json.encodeToString(
                serializer = Bookmarks.serializer(),
                value = t
            ).encodeToByteArray()
        )
}