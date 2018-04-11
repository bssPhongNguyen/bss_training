package com.beesightsoft.training6.domain.login;

import com.beesightsoft.training6.service.comment.RestCommentService;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends MvpBasePresenter<LoginView> {

    @Inject
    protected RestCommentService restCommentService;

    @Inject
    public LoginPresenter() {

    }

    public void getComments() {
        getView().showLoading();
        restCommentService.getCommentsUseRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    if (isViewAttached()) {
                        getView().hideLoading();
                    }
                })
                .subscribe(comments -> {
                    if (isViewAttached()) {
                        getView().onGetCommentsSuccessful(comments);
                    }
                }, throwable -> {
                    if (isViewAttached()) {
                        getView().onGetCommentsFailed(throwable);
                    }
                });
    }
}
