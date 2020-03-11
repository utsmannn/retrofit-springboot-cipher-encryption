package com.utsman.retrofitcipher

import android.annotation.SuppressLint
import android.util.Base64
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.ByteBuffer
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

private const val key = "utsman_gantenkkk" // 16 digit

@SuppressLint("GetInstance")
fun String.decrypt(): String {
    val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)

    val decrypted = cipher.doFinal(Base64.decode(this, Base64.DEFAULT))
    return String(decrypted)
}

fun Any.encrypt(): String {
    val objectMapper = ObjectMapper()
    objectMapper.registerModule(KotlinModule())
    val buf = ByteBuffer.wrap(objectMapper.writeValueAsString(this).toByteArray(Charsets.UTF_8))

    val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)

    val encrypted = cipher.doFinal(buf.array())
    return Base64.encodeToString(encrypted, Base64.DEFAULT)
}