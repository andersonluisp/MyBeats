package com.example.mybeats.data.local.utils

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.MasterKey

object AndroidKeyGenerator {

    private const val KEY_SIZE = 256
    private const val KEY_ALIAS = "_androidx_security_master_key_"

    private fun generateKey(): KeyGenParameterSpec =
        KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(KEY_SIZE)
            .build()

    fun masterKeyProvide(context: Context): MasterKey =
        MasterKey.Builder(context)
            .setKeyGenParameterSpec(generateKey())
            .build()
}
