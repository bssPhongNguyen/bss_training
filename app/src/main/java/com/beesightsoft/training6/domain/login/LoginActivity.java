package com.beesightsoft.training6.domain.login;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.widget.TextView;
import android.widget.Toast;

import com.beesightsoft.training6.R;
import com.beesightsoft.training6.factory.DaggerApplicationComponent;
import com.beesightsoft.training6.service.model.Comment;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

@SuppressLint("Registered")
@EActivity(R.layout.activity_login)
public class LoginActivity extends MvpActivity<LoginView, LoginPresenter> implements LoginView {

    @ViewById(R.id.activity_login_tv_content)
    protected TextView tvContent;

    @Inject
    protected LoginPresenter presenter;

    @AfterInject
    void afterInject() {
        DaggerApplicationComponent.builder()
                .build()
                .inject(this);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return presenter;
    }

    @SuppressLint("SetTextI18n")
    @AfterViews
    void afterView() {
        presenter.getComments();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "Show loading dialog ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideLoading() {
        Toast.makeText(this, "Hide loading dialog ...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetCommentsSuccessful(List<Comment> comments) {
        tvContent.setText("Get comment success: " + comments.size() + " items");
    }

    @Override
    public void onGetCommentsFailed(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        tvContent.setText("Get comment failed: " + throwable.getMessage());
    }


    /*IMPLEMENT PUT, POST, DELETE WITH RETROFIT IN OTHER ACTIVITY*/

    /*
    //Get Comments use Rx
    private void getCommentUseRx() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestCommentService restCommentService = retrofit.create(RestCommentService.class);

        restCommentService.getCommentsUseRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comments -> {
                    tvContent.setText("Rx success: " + comments.size() + " items");
                }, throwable -> {
                    tvContent.setText("Rx failed: " + throwable.getMessage());
                });
    }*/

    /*
    --> Use retrofit in default way
    @Background
    protected void getComments(Retrofit retrofit) {
        RestCommentService restCommentService = retrofit.create(RestCommentService.class);
        Call<List<Comment>> call = restCommentService.getComments();

        try {
            List<Comment> comments = call.execute().body();
            showComments(comments);
        } catch (IOException e) {
            e.printStackTrace();
            tvContent.setText("Error");
        }
    }

    @UiThread
    protected  void showComments(List<Comment> comments) {
        tvContent.setText("Success: " + comments.size() + " items");
    }*/
}
