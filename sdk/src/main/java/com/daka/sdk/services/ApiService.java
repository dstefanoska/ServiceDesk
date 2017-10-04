package com.daka.sdk.services;

import com.daka.sdk.models.Building;
import com.daka.sdk.models.Elevator;
import com.daka.sdk.models.Location;
import com.daka.sdk.models.Servicer;
import com.daka.sdk.models.Sos;
import com.daka.sdk.models.Task;
import com.daka.sdk.models.User;
import com.daka.sdk.models.wrappers.TaskResponseModel;
import com.daka.sdk.models.wrappers.UserModel;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Dana on 22-Sep-17.
 */

public interface ApiService {

    @POST("auth/login")
    Observable<User> login(@Body UserModel userModel);

    @GET("task/getMyTasks")
    Observable<List<Task>> getAllTasks();

    @GET("task/taskDetails")
    Observable<Task> getTaskDetails(@Query("taskId") Integer taskId);

    @POST("task/responseToTask")
    Observable<Boolean> respondToTask(@Body TaskResponseModel responseModel);

    @POST("task/addTask")
    Observable<Task> createTask(@Body Task model);

    @POST("task/getUncompletedTasks")
    Observable<List<Task>> getUncompletedTasks(@Query("endDate") String endDate);

    @GET("building/getBuildings")
    Observable<List<Building>> getBuildings(@Query("locationId") Integer locationId);

    @GET("building/getBuilding")
    Observable<Building> getBuilding(@Query("buildingId") Integer buildingId);

    @GET("building/getElevators")
    Observable<List<Elevator>> getElevators(@Query("buildingId") Integer buildingId);

    @GET("building/getElevator")
    Observable<Elevator> getElevator(@Query("elevatorId") Integer elevatorId);

    @GET("location/getLocations")
    Observable<List<Location>> getLocations();

    @GET("sos/getSos")
    Observable<Sos> getSos(@Query("sosId") Integer sosId);

    @GET("user/getUsers")
    Observable<List<Servicer>> getServicers(@Query("roleId") Integer roleId);
}
