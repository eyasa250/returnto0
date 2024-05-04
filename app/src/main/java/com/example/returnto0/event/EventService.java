package com.example.returnto0.event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {
    @GET("/event")
    Call<List<Event>> getAllEvents();

    @GET("/event/{id}")
    Call<Event> getEventById(@Path("id") String id);

    @POST("/event/addevent")
    Call<Event> createEvent(@Body Event event);

    @PUT("/event/{id}")
    Call<Event> updateEvent(@Path("id") String id, @Body Event event);

    @DELETE("/event/{id}")
    Call<Void> deleteEvent(@Path("id") String id);
}
