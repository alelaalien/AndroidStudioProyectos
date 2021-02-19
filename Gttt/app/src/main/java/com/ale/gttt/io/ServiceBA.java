package com.ale.gttt.io;

import android.util.Base64;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class ServiceBA {
     public static final String BASE_URL= "http://25.75.53.126:45455/";
     private static ServiceBA intance;
     private Retrofit retrofit;
     private HttpLoggingInterceptor loggingInterceptor;
     private OkHttpClient.Builder httpClientBuilder;
     private static final String AUTH_ADMIN= "Bearer "+ Base64.encodeToString(("admin:adminale").getBytes() , Base64.NO_WRAP);
     private static final String AUTH_USER= "Bearer "+ Base64.encodeToString(("user:user").getBytes() , Base64.NO_WRAP);

     private ServiceBA()  {
          loggingInterceptor= new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
          httpClientBuilder= new OkHttpClient.Builder().addInterceptor(new Interceptor() {
               @NotNull
               @Override
               public Response intercept(@NotNull Chain chain) throws IOException {
                    Request original= chain.request();
                    Request.Builder requestB= original.newBuilder()
                            .addHeader("Authorization",AUTH_USER)
                            .method(original.method(), original.body());
                    Request request=requestB.build();
                    return chain.proceed(request);
               }
          })
                  .connectTimeout(200, TimeUnit.SECONDS)
                  .writeTimeout(200, TimeUnit.SECONDS)
                  .readTimeout(300, TimeUnit.SECONDS);


          retrofit= new Retrofit.Builder()
                  .baseUrl(BASE_URL)
                  .addConverterFactory(GsonConverterFactory.create())
                  .build();

     }
     public static  synchronized ServiceBA getInstance() {
          if (intance==null){
               intance= new ServiceBA();}

          return intance;
     }
     public <S> S createService(Class<S> serviceClass){
          return  retrofit.create(serviceClass);
     }



}
