package tk.teaclient.auth

import com.google.gson.Gson
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

/**
 * A utility object for making HTTP requests.
 */
object HTTPUtils {

    /**
     * Makes a POST request to the server.
     *
     * @param url The URL to post on.
     * @param obj The content of the post request.
     * @param clazz The class to deserialize the response to.
     * @return The deserialized response.
     */
    fun <T : Any> post(url: String, obj: Any, clazz: Class<T>): T {
        // Create a JSON serializer/deserializer.
        val gson = Gson()

        // Create a POST request.
        val post = HttpPost(url)

        // Set the content of the request.
        if (obj is HttpEntity) {
            post.entity = obj
        } else {
            post.entity = StringEntity(gson.toJson(obj))
        }

        // Create an HTTP client to make the request.
        val client = HttpClientBuilder.create().build()!!

        // Make a POST request to the server.
        val response = client.execute(post)

        // Get the content as an input stream and convert it to a string.
        val content = response.entity.content.string()

        // Close the HTTP client.
        client.close()

        // Deserialize the response from JSON to the specified class.
        return gson.fromJson(content, clazz)
    }

    /**
     * Makes a GET request to the server.
     *
     * @param url The URL to get on.
     * @param params The parameters of the GET request.
     * @param clazz The class to deserialize the response to.
     * @param headers The headers to include in the GET request.
     * @return The deserialized response.
     */
    fun <T : Any> get(
        url: String,
        params: List<NameValuePair>,
        clazz: Class<T>,
        headers: Map<String, String>
    ): T {
        // Create a JSON serializer/deserializer.
        val gson = Gson()

        // Create a GET request.
        val get = HttpGet(buildString {
            this.append(url)

            for ((index, pair) in params.withIndex()) {
                if (index == 0)
                    this.append("?")
                else
                    this.append("&")

                this.append("${pair.name}=${pair.value}")
            }
        })

        // Set the headers of the request.
        for (header in headers) {
            get.setHeader(header.key, header.value)
        }

        // Create an HTTP client to make the request.
        val client = HttpClientBuilder.create().build()!!

        // Make a GET request to the server.
        val response = client.execute(get)

        // Get the content as an input stream and convert it to a string.
        val content = response.entity.content.string()

        // Close the HTTP client.
        client.close()

        // Deserialize the response from JSON to the specified class.
        return gson.fromJson(content, clazz)
    }
}
