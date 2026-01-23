package com.think.base.storer

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

/**
 * author : Samuel
 * e-mail : xingtai.wei@icloud.com
 * time   : 2026/1/19 16:28
 * desc   : 基于DataStore Preferences的通用数据存储工具类，支持基本数据类型和复杂类型的存储与读取
 */
// DataStore初始化，增加文件损坏处理
val Context.dataStore by preferencesDataStore(
    name = "settings",
    corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() })
)

object Storer {

    private val gson by lazy {
        Gson().newBuilder().serializeNulls().disableHtmlEscaping().create()
    }

    private lateinit var dataStore: DataStore<Preferences>

    /**
     * 初始化Storer
     * @param context 建议传入Application Context，避免内存泄漏
     */
    fun init(context: Context): Storer {
        dataStore = context.applicationContext.dataStore
        return this
    }

    fun put(key: String, value: Int = 0) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: Int = 0) {
        dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    fun put(key: String, value: Double = 0.0) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: Double = 0.0) {
        dataStore.edit { it[doublePreferencesKey(key)] = value }
    }

    fun put(key: String, value: Float = 0.0f) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: Float = 0.0f) {
        dataStore.edit { it[floatPreferencesKey(key)] = value }
    }

    fun put(key: String, value: Long = 0L) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: Long = 0L) {
        dataStore.edit { it[longPreferencesKey(key)] = value }
    }

    fun put(key: String, value: Boolean = false) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: Boolean = false) {
        dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    fun put(key: String, value: String = "") = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: String = "") {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    fun put(key: String, value: ByteArray = byteArrayOf()) = runBlocking { putAsync(key, value) }
    suspend fun putAsync(key: String, value: ByteArray = byteArrayOf()) {
        dataStore.edit { it[byteArrayPreferencesKey(key)] = value }
    }

    fun <T> put(key: String, value: List<T>) = runBlocking { putAsync(key, value) }
    suspend fun <T> putAsync(key: String, value: List<T>) {
        val json = gson.toJson(value)
        dataStore.edit { it[stringPreferencesKey(key)] = json }
    }

    fun <T> put(key: String, value: Set<T>) = runBlocking { putAsync(key, value) }
    suspend fun <T> putAsync(key: String, value: Set<T>) {
        val json = gson.toJson(value)
        dataStore.edit { it[stringPreferencesKey(key)] = json }
    }

    fun <K, V> put(key: String, value: Map<K, V>) = runBlocking { putAsync(key, value) }
    suspend fun <K, V> putAsync(key: String, value: Map<K, V>) {
        val json = gson.toJson(value)
        dataStore.edit { it[stringPreferencesKey(key)] = json }
    }

    fun get(key: String, default: Int = 0): Int = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: Int = 0): Int {
        return dataStore.data.first()[intPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: Int = 0): Flow<Int> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[intPreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: Float = 0f): Float = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: Float = 0f): Float {
        return dataStore.data.first()[floatPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: Float = 0f): Flow<Float> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[floatPreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: Double = 0.0): Double = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: Double = 0.0): Double {
        return dataStore.data.first()[doublePreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: Double = 0.0): Flow<Double> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[doublePreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: Long = 0L): Long = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: Long = 0L): Long {
        return dataStore.data.first()[longPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: Long = 0L): Flow<Long> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[longPreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: Boolean = false): Boolean = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: Boolean = false): Boolean {
        return dataStore.data.first()[booleanPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: Boolean = false): Flow<Boolean> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[booleanPreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: String = ""): String = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: String = ""): String {
        return dataStore.data.first()[stringPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: String = ""): Flow<String> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[stringPreferencesKey(key)] ?: default
        }
    }

    fun get(key: String, default: ByteArray = byteArrayOf()): ByteArray = runBlocking { getAsync(key, default) }
    suspend fun getAsync(key: String, default: ByteArray = byteArrayOf()): ByteArray {
        return dataStore.data.first()[byteArrayPreferencesKey(key)] ?: default
    }
    fun getFlow(key: String, default: ByteArray = byteArrayOf()): Flow<ByteArray> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map {
            it[byteArrayPreferencesKey(key)] ?: default
        }
    }

    fun <T> get(key: String, default: List<T>): List<T> = runBlocking { getAsync(key, default) }
    suspend fun <T> getAsync(key: String, default: List<T>): List<T> {
        return try {
            val typeToken = object : TypeToken<List<T>>() {}
            val json = dataStore.data.first()[stringPreferencesKey(key)] ?: return default
            gson.fromJson(json, typeToken.type) ?: default
        } catch (_: Exception) {
            default
        }
    }
    fun <T> getFlow(key: String, default: List<T>): Flow<List<T>> {
        val typeToken = object : TypeToken<List<T>>() {}
        return dataStore.data.catch { e ->
                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }.map { preferences ->
                try {
                    val json = preferences[stringPreferencesKey(key)] ?: return@map default
                    gson.fromJson(json, typeToken.type) ?: default
                } catch (_: Exception) {
                    default
                }
            }
    }

    fun <T> get(key: String, default: Set<T>): Set<T> = runBlocking { getAsync(key, default) }
    suspend fun <T> getAsync(key: String, default: Set<T>): Set<T> {
        return try {
            val typeToken = object : TypeToken<Set<T>>() {}
            val json = dataStore.data.first()[stringPreferencesKey(key)] ?: return default
            gson.fromJson(json, typeToken.type) ?: default
        } catch (_: Exception) {
            default
        }
    }
    fun <T> getFlow(key: String, default: Set<T>): Flow<Set<T>> {
        val typeToken = object : TypeToken<Set<T>>() {}
        return dataStore.data.catch { e ->
                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }.map { preferences ->
                try {
                    val json = preferences[stringPreferencesKey(key)] ?: return@map default
                    gson.fromJson(json, typeToken.type) ?: default
                } catch (_: Exception) {
                    default
                }
            }
    }

    fun <K, V> get(key: String, default: Map<K, V>): Map<K, V> = runBlocking { getAsync(key, default) }
    suspend fun <K, V> getAsync(key: String, default: Map<K, V>): Map<K, V> {
        return try {
            val typeToken = object : TypeToken<Map<K, V>>() {}
            val json = dataStore.data.first()[stringPreferencesKey(key)] ?: return default
            gson.fromJson(json, typeToken.type) ?: default
        } catch (_: Exception) {
            default
        }
    }
    fun <K, V> getFlow(key: String, default: Map<K, V>): Flow<Map<K, V>> {
        val typeToken = object : TypeToken<Map<K, V>>() {}
        return dataStore.data.catch { e ->
                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }.map { preferences ->
                try {
                    val json = preferences[stringPreferencesKey(key)] ?: return@map default
                    gson.fromJson(json, typeToken.type) ?: default
                } catch (_: Exception) {
                    default
                }
            }
    }

    fun contains(key: String): Boolean = runBlocking { containsAsync(key) }
    suspend fun containsAsync(key: String): Boolean {
        return try {
            val preferences = dataStore.data.first()
            listOf(
                intPreferencesKey(key),
                floatPreferencesKey(key),
                doublePreferencesKey(key),
                longPreferencesKey(key),
                booleanPreferencesKey(key),
                stringPreferencesKey(key),
                byteArrayPreferencesKey(key)
            ).any { preferences.contains(it) }
        } catch (_: Exception) {
            false
        }
    }

    fun delete(key: String): Boolean = runBlocking { deleteAsync(key) }
    suspend fun deleteAsync(key: String): Boolean {
        var isDeleted = false
        dataStore.edit { preferences ->
            val keys = listOf(
                intPreferencesKey(key),
                floatPreferencesKey(key),
                doublePreferencesKey(key),
                longPreferencesKey(key),
                booleanPreferencesKey(key),
                stringPreferencesKey(key),
                byteArrayPreferencesKey(key)
            )
            keys.forEach { prefKey ->
                if (preferences.contains(prefKey)) {
                    preferences.remove(prefKey)
                    isDeleted = true
                }
            }
        }
        return isDeleted
    }

    fun deleteAll() = runBlocking { deleteAllAsync() }
    suspend fun deleteAllAsync() {
        try {
            dataStore.edit { it.clear() }
        } catch (_: Exception) {
        }
    }

    fun count(): Int = runBlocking { countAsync() }
    suspend fun countAsync(): Int {
        return try {
            dataStore.data.first().asMap().size
        } catch (_: Exception) {
            0
        }
    }

}