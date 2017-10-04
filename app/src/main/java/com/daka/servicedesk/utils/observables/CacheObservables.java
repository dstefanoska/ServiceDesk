package com.daka.servicedesk.utils.observables;

import com.daka.sdk.models.Building;
import com.daka.sdk.models.Location;
import com.daka.sdk.models.Servicer;
import com.daka.sdk.services.Daka;
import com.daka.servicedesk.utils.Store;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by Dana on 03-Oct-17.
 */

public class CacheObservables {

    public static void init() {
        getUsers();
        getLocations();
    }


    private static void getLocations () {
        getLocationsApi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                })
                .doOnTerminate(() -> {
                })
                .subscribe(response -> {
                    Store.locations(response);
                    mergeLocationsAndBuildings();
                }, throwable -> {
                    Timber.d("Users throwable", throwable);
                });
    }

    private static void getBuildings(int locationId) {
        getBuildingsApi(locationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                })
                .doOnTerminate(() -> {
                })
                .subscribe(response -> {
                    Store.buildings(response);
                }, throwable -> {
                    Timber.d("Users throwable", throwable);
                });
    }

    private static void mergeLocationsAndBuildings() {
        for(Location location : Store.locations()) {
            getBuildings(location.getId());
        }
    }

    private static void getUsers () {
        getUsersApi().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> {
                })
                .doOnTerminate(() -> {
                })
                .subscribe(response -> {
                    Store.users(response);
                }, throwable -> {
                    Timber.d("Users throwable", throwable);
                });
    }

    private static Observable<List<Building>> getBuildingsApi(int locationId) {
        return Daka.getApiService().getBuildings(locationId);
    }

    private static Observable<List<Location>> getLocationsApi() {
        return Daka.getApiService().getLocations();
    }

    private static Observable<List<Servicer>> getUsersApi () {
        return Daka.getApiService().getServicers(3);
    }
}
