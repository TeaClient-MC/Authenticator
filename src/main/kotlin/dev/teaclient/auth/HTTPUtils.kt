package dev.teaclient.auth

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

/**
 * A utility object for making HTTP requests.
 */
object HTTPUtils {

    val client = OkHttpClient()

    val gson = Gson()

    @Throws(IOException::class)
    inline fun <reified T : Any> post(url: String, obj: Any): T? {
        val json = gson.toJson(obj)
        val body = json.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder().url(url).post(body).build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return gson.fromJson(response.body?.string(), T::class.java)
    }

    @Throws(IOException::class)
    inline fun <reified T : Any> post(url: String, params: Map<String, String>): T? {
        val formBodyBuilder = FormBody.Builder()
        params.forEach { (key, value) -> formBodyBuilder.add(key, value) }
        val formBody = formBodyBuilder.build()

        val request = Request.Builder().url(url).post(formBody).build()

        val response = client.newCall(request).execute()

        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return gson.fromJson(response.body?.string(), T::class.java)
    }


    @Throws(IOException::class)
    inline fun <reified T : Any> get(url: String, headers: Map<String, String> = mapOf(), params: Map<String, String> = mapOf()): T? {
        val urlBuilder = StringBuilder(url)

        if (params.isNotEmpty()) {
            urlBuilder.append("?")
            params.entries.forEachIndexed { index, (key, value) ->
                urlBuilder.append("$key=$value")
                if (index < params.size - 1) {
                    urlBuilder.append("&")
                }
            }
        }

        val requestBuilder = Request.Builder().url(urlBuilder.toString())
        headers.forEach { (key, value) -> requestBuilder.addHeader(key, value) }
        val request = requestBuilder.build()

        val response = client.newCall(request).execute()

        val body = response.body?.string()


        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        }

        return gson.fromJson(body, T::class.java)
    }

}
