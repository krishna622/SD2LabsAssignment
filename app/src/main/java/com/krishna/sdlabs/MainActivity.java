package com.krishna.sdlabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.krishna.sdlabs.Constants.Constants;
import com.krishna.sdlabs.Constants.UrlConstants;
import com.krishna.sdlabs.Utils.CommonUtility;
import com.krishna.sdlabs.adapters.MainListAdapter;
import com.krishna.sdlabs.models.CustomResultModel;
import com.krishna.sdlabs.models.ResultModel;
import com.krishna.sdlabs.models.UserModel;
import com.krishna.sdlabs.service.ProjectRepository;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.mainList)
    RecyclerView mList;

    private LinearLayoutManager mLayoutManager;
    private MainListAdapter mListAdapter;
    ArrayList<CustomResultModel> userModelArrayList = new ArrayList<>();
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    int lastOffSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);

        mList.setLayoutManager(new LinearLayoutManager(this));
        mLayoutManager = (LinearLayoutManager) mList.getLayoutManager();
        mListAdapter = new MainListAdapter(this, userModelArrayList);
        mList.setHasFixedSize(false);
        mList.setAdapter(mListAdapter);

        if (CommonUtility.checkNetwork(this))
            loadMainList();
        else
            Toast.makeText(MainActivity.this,"Please check your internet connection!",Toast.LENGTH_SHORT).show();



        setLoadMoreListener();

    }

    private void loadMainList() {
        ProjectRepository.getInstance().getUsers(Constants.OFFSET, Constants.LIMIT, new ProjectRepository.GetUsersCallBack() {
            @Override
            public void onResult(JsonObject jsonObject) {
                Gson gson = new Gson();
                ResultModel result = gson.fromJson(jsonObject,ResultModel.class);
                if(result != null && result.getDataModel()!=null && result.getDataModel().getUserList()!=null) {
                    ArrayList<CustomResultModel> list = transFormResult(result);
                    mListAdapter.updateUserList(list);
                }
               findViewById(R.id.initialProgressBar).setVisibility(View.GONE);
                loading = false;
                lastOffSet = Integer.parseInt(Constants.OFFSET)+ Integer.parseInt(Constants.LIMIT);

            }
            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this,"Api Failed,Please retry!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ArrayList<CustomResultModel> transFormResult(ResultModel result) {
        ArrayList<CustomResultModel> list = new ArrayList<>();

        ArrayList<UserModel> userModels = result.getDataModel().getUserList();

        for (UserModel userModel : userModels){

            CustomResultModel customResultModel = new CustomResultModel();

            CustomResultModel.CustomUserModel customUserModel = new CustomResultModel().new CustomUserModel(userModel.getUserName(),userModel.getUserImage());
            customResultModel.setType(0);
            customResultModel.setCustomUserModel(customUserModel);
            list.add(customResultModel);

            ArrayList<String> itemImageList = userModel.getItemImageList();

            int size = itemImageList.size();

            boolean mIsEven = size % 2 == 0;

            int oddFactor = 0;
            if(!mIsEven)
                oddFactor = 1;

            int loopSize = (size+oddFactor)/2  ;
            for(int i=0;i<loopSize;i++){
                CustomResultModel customResultModel1 = new CustomResultModel();

                if(i ==0 && size%2 ==1){
                    customResultModel1.setSingleUrl(itemImageList.get(0));
                    customResultModel1.setType(1);
                    list.add(customResultModel1);
                }else {
                    ArrayList<String> smallLIst = new ArrayList<>();
                    smallLIst.add(itemImageList.get(i*2-oddFactor));
                    smallLIst.add(itemImageList.get(i*2+1 - oddFactor));
                    customResultModel1.setDoubleUrlList(smallLIst);
                    customResultModel1.setType(2);
                    list.add(customResultModel1);
                }

            }

        }

        return  list;
    }

    private void setLoadMoreListener() {
        mList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                if (!loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        Toast.makeText(MainActivity.this,"Loading Next Page ...",Toast.LENGTH_LONG).show();
                        loadMoreContent();
                    }
                }
            }
        });
    }

    private void loadMoreContent() {
        loading = true;

        ProjectRepository.getInstance().getUsers(String.valueOf(lastOffSet), Constants.LIMIT, new ProjectRepository.GetUsersCallBack() {
            @Override
            public void onResult(JsonObject jsonObject) {
                                Gson gson = new Gson();
                ResultModel result = gson.fromJson(jsonObject, ResultModel.class);
                if(result != null && result.getDataModel()!=null && result.getDataModel().getUserList()!=null) {
                    ArrayList<CustomResultModel> list = transFormResult(result);
                    mListAdapter.updateUserList(list);
                }
                if(result.getDataModel().isHasMore())
                    loading = false;
                else
                    loading = true;
                lastOffSet = lastOffSet + Integer.parseInt(Constants.LIMIT);
            }
            @Override
            public void onFailure() {
                Toast.makeText(MainActivity.this,"Api Failed,Please retry!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
