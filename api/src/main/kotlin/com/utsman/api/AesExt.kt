package com.utsman.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.apache.tomcat.util.codec.binary.Base64
import java.nio.ByteBuffer
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

private const val key = "utsman_gantenkkk" // 16 digit

fun Any.encrypt(): String {
    val objectMapper = ObjectMapper()
    objectMapper.registerModule(KotlinModule())
    val buf = ByteBuffer.wrap(objectMapper.writeValueAsString(this).toByteArray(Charsets.UTF_8))

    val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec)

    val encrypted = cipher.doFinal(buf.array())
    return Base64.encodeBase64String(encrypted)
}

fun String.decrypt(): String {
    val keySpec = SecretKeySpec(key.toByteArray(charset("UTF-8")), "AES")

    val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
    cipher.init(Cipher.DECRYPT_MODE, keySpec)

    val decrypted = cipher.doFinal(Base64.decodeBase64(this))
    return String(decrypted)
}