package com.rc.weatherforecastapplication.network

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Singleton

@Singleton
internal object CurlLoggingInterceptor : Interceptor {
    private var curlOptions: String? = null
    private val logger: HttpLoggingInterceptor.Logger = HttpLoggingInterceptor.Logger.DEFAULT

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var compressed = false
        var curlCmd = "curl"
        if (curlOptions != null) {
            curlCmd += " $curlOptions"
        }
        curlCmd += " -X " + request.method
        val headers = request.headers
        var i = 0
        val count = headers.size
        while (i < count) {
            val name = headers.name(i)
            val value = headers.value(i)
            if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(
                    value,
                    ignoreCase = true
                )
            ) {
                compressed = true
            }
            curlCmd += " -H \"$name: $value\""
            i++
        }
        val requestBody = request.body
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)!!
            }
            // try to keep to a single line and use a subshell to preserve any line breaks
            curlCmd += " --data $'" + buffer.readString(charset).replace("\n", "\\n") + "'"
        }
        curlCmd += (if (compressed) " --compressed " else " ") + request.url
        logger.log("╭--- cURL (" + request.url + ")")
        logger.log(curlCmd)
        logger.log("╰--- (copy and paste the above line to a terminal)")
        return chain.proceed(request)
    }

    private val UTF8 = Charset.forName("UTF-8")
}