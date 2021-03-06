package net.gravitydevelopment.cnu.network;

import net.gravitydevelopment.cnu.modal.AlertItem;
import net.gravitydevelopment.cnu.modal.FeedItem;
import net.gravitydevelopment.cnu.modal.FeedbackItem;
import net.gravitydevelopment.cnu.modal.InfoItem;
import net.gravitydevelopment.cnu.modal.LocationCollection;
import net.gravitydevelopment.cnu.modal.MenuItem;
import net.gravitydevelopment.cnu.modal.UpdateItem;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Endpoint-defining class for the {@link net.gravitydevelopment.cnu.network.API}.
 */
public interface Service {

    String API_USER_AGENT = "DiningBuddy-Android";

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/alerts/")
    List<AlertItem> alertList();

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/locations/")
    LocationCollection locationList();

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/info/")
    List<InfoItem> infoList();

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/info/{location}/")
    InfoItem info(@Path("location") String location);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/menus/{location}/")
    List<MenuItem> menuList(@Path("location") String location);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @GET("/feed/{location}/")
    List<FeedItem> feedList(@Path("location") String location);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @POST("/update/")
    void update(@Body UpdateItem item, Callback<Response> callback);

    @Headers({
            "Content-Type: application/json",
            "User-Agent: " + API_USER_AGENT
    })
    @POST("/feedback/")
    void feedback(@Body FeedbackItem item, Callback<Response> callback);
}
