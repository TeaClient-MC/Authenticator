package tk.teaclient.auth

import com.google.gson.Gson
import org.apache.http.HttpEntity
import org.apache.http.NameValuePair
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder

object HTTPUtils {

    /**
     * Make POST request to server
     *
     * @param url to post on
     * @param obj content of post request
     * @param clazz Class to deserialize to
     */
    fun <T : Any> post(url: String, obj: Any, clazz: Class<T>): T {
        // create json serializer/deserializer
        val gson = Gson()

        // create post request
        val post = HttpPost(url)

        // set content of request
        if(obj is HttpEntity) {
            post.entity = obj
        } else {
            post.entity = StringEntity(gson.toJson(obj))
        }

        // create http client to make request
        val client = HttpClientBuilder.create().build()!!

        // make post to server
        val response = client.execute(post)

        // get content as input stream and convert it to string
        val content = response.entity.content.string()

        // close client
        client.close()

        // json to class
        return gson.fromJson(content, clazz)
    }
    /**
     * Make GET request to server
     *
     * @param url to get on
     * @param obj params of get req
     * @param clazz Class to deserialize to
     */
    fun <T : Any> get(url: String, params: List<NameValuePair>, clazz: Class<T>, headers: Map<String, String>): T {
        // create json serializer/deserializer
        val gson = Gson()

        // create get request
        val get = HttpGet(buildString {
            this.append(url)

            for((index, pair) in params.withIndex()) {
                if(index == 0)
                    this.append("?")
                else
                    this.append("&")

                this.append("${pair.name}=${pair.value}")
            }
        })


        // create http client to make request
        val client = HttpClientBuilder.create().build()!!

        // make get to server
        val response = client.execute(get)

        // get content as input stream and convert it to string
        val content = response.entity.content.string()

        // close client
        client.close()

        // json to class
        return gson.fromJson(content, clazz)
    }

}