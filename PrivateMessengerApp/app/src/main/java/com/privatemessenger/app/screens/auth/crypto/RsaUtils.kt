package com.privatemessenger.app.screens.auth.crypto

import android.util.Base64
import java.security.*
import java.security.spec.*
import javax.crypto.Cipher

object RSAUtils {

    private const val RSA_ALGORITHM = "RSA"

    /**
     * 1. Generate RSA public and private keys, returning them as Base64 strings.
     */
    fun generateKeyPair(): Pair<String, String> {
        val keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM)
        keyPairGenerator.initialize(2048)
        val keyPair = keyPairGenerator.generateKeyPair()

        val publicKeyString = Base64.encodeToString(keyPair.public.encoded, Base64.NO_WRAP)
        val privateKeyString = Base64.encodeToString(keyPair.private.encoded, Base64.NO_WRAP)

        return Pair(publicKeyString, privateKeyString)
    }

    /**
     * 2. Sign text using the public key string and return the encrypted (signed) string.
     *    If the key is invalid, return null.
     */
    fun encryptText(text: String, publicKeyString: String): String? {
        return try {
            val publicKey = getPublicKeyFromString(publicKeyString)
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            val encryptedBytes = cipher.doFinal(text.toByteArray(Charsets.UTF_8))

            Base64.encodeToString(encryptedBytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 3. Decrypt text using the private key string and return the original text.
     *    If the key is invalid, return null.
     */
    fun decryptText(encryptedText: String, privateKeyString: String): String? {
        return try {
            val privateKey = getPrivateKeyFromString(privateKeyString)
            val cipher = Cipher.getInstance(RSA_ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, privateKey)

            val encryptedBytes = Base64.decode(encryptedText, Base64.NO_WRAP)
            val decryptedBytes = cipher.doFinal(encryptedBytes)

            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Helper to get PublicKey from Base64 string
     */
    private fun getPublicKeyFromString(publicKeyString: String): PublicKey {
        val keyBytes = Base64.decode(publicKeyString, Base64.NO_WRAP)
        val keySpec = X509EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
        return keyFactory.generatePublic(keySpec)
    }

    /**
     * Helper to get PrivateKey from Base64 string
     */
    private fun getPrivateKeyFromString(privateKeyString: String): PrivateKey {
        val keyBytes = Base64.decode(privateKeyString, Base64.NO_WRAP)
        val keySpec = PKCS8EncodedKeySpec(keyBytes)
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)
        return keyFactory.generatePrivate(keySpec)
    }
}