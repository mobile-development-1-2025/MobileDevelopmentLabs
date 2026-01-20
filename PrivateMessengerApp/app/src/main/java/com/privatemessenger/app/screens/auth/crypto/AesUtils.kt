package com.privatemessenger.app.screens.auth.crypto

import android.util.Base64
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

object AesUtils {
    // Generate a secure AES-256 key
    fun generateAESKey(): String {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(256)
        val secretKey = keyGen.generateKey()
        return Base64.encodeToString(secretKey.encoded, Base64.NO_WRAP)
    }

    // Get a SecretKey object from the encoded key string
    private fun getSecretKeyFromString(key: String): SecretKey {
        val decodedKey = Base64.decode(key, Base64.NO_WRAP)
        return SecretKeySpec(decodedKey, "AES")
    }

    // Generate a random IV
    fun generateRandomIV(): ByteArray {
        return ByteArray(16) { Random.nextBytes(1)[0] }
    }

    // Convert IV to a Base64 string
    fun ivToString(iv: ByteArray): String {
        return Base64.encodeToString(iv, Base64.NO_WRAP)
    }

    // Convert IV from a Base64 string back to ByteArray
    fun ivFromString(ivString: String): ByteArray {
        return Base64.decode(ivString, Base64.NO_WRAP)
    }

    // Encrypt a message using AES-256
    fun encryptMessage(key: String, message: String, ivString: String): String {
        val secretKey = getSecretKeyFromString(key)
        val iv = ivFromString(ivString)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

        val encryptedBytes = cipher.doFinal(message.toByteArray(StandardCharsets.UTF_8))
        val encryptedMessage = Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)

        return encryptedMessage
    }

    // Decrypt a message using AES-256
    fun decryptMessage(key: String, encryptedMessage: String, ivString: String): String? {
        try {
            val secretKey = getSecretKeyFromString(key)
            val iv = ivFromString(ivString)
            val ivSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

            val encryptedBytes = Base64.decode(encryptedMessage, Base64.DEFAULT)
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            return String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun encryptFile(key: String, file: File, ivString: String): File {
        val outputFile = File(file.parentFile, "encrypted_${file.name}")

        val secretKey = getSecretKeyFromString(key)
        val iv = ivFromString(ivString)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec)

        val fis = FileInputStream(file)
        val fos = FileOutputStream(outputFile)

        val cos = CipherOutputStream(fos, cipher)
        fis.copyTo(cos)
        cos.flush()
        cos.close()
        fis.close()

        return outputFile
    }

    // Decrypt a message using AES-256
    fun decryptFile(key: String, file: File, ivString: String): File? {
        try {
            val outputFile = File(file.parentFile, file.name.replace("encrypted_", ""))

            val secretKey = getSecretKeyFromString(key)
            val iv = ivFromString(ivString)
            val ivSpec = IvParameterSpec(iv)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)

            val fis = FileInputStream(file)
            val fos = FileOutputStream(outputFile)

            val cis = CipherInputStream(fis, cipher)
            cis.copyTo(fos)
            fos.flush()
            fos.close()
            cis.close()

            return outputFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}