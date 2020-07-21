@file:JvmName("JsonUtil")
package dev.zerite.craftlib.protocol.util.ext

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dev.zerite.craftlib.chat.component.ChatComponentTypeAdapter
import dev.zerite.craftlib.protocol.util.json.factory.ProtocolVersionTypeAdapter

/**
 * Reference to the singular gson instance which we use.
 */
val gson: Gson = GsonBuilder()
    .registerTypeAdapterFactory(ChatComponentTypeAdapter)
    .registerTypeAdapterFactory(ProtocolVersionTypeAdapter)
    .create()
