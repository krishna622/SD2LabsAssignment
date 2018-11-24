package com.krishna.sdlabs.service;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectRepository {
    private APIInterface apiInterface;
    private static ProjectRepository projectRepository;
    private String offset, limit;
    private GetUsersCallBack callBack;

    private ProjectRepository() {
        apiInterface = APIClient.getClient().create(APIInterface.class);

    }

    public synchronized static ProjectRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }


    public void getUsers(String offset,String limit,GetUsersCallBack callBack) {
        this.offset = offset;
        this.limit = limit;
        this.callBack = callBack;
        getObservable().subscribeWith(getObserver());

    }

    public Observable<JsonObject> getObservable(){
        return apiInterface
                .getUsers(offset,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public interface GetUsersCallBack{
        void onResult(JsonObject resultModel);
        void onFailure();
    }

    public DisposableObserver<JsonObject> getObserver() {
        return new DisposableObserver<JsonObject>() {

            @Override
            public void onNext(@NonNull JsonObject jsonObject) {
                Log.d("Krishna", "OnNext" + jsonObject);
                callBack.onResult(jsonObject);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d("Krishna", "Error" + e);
                e.printStackTrace();
                callBack.onFailure();
            }

            @Override
            public void onComplete() {
                Log.d("Krishna", "Completed");
            }
        };
    }
}
