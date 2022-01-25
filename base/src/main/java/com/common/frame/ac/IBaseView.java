package com.common.frame.ac;


import com.common.frame.utils.ToastUtil;

/**
 * @author lst
 * @desc desc
 * @date 2021/6/18
 */
public interface IBaseView {
    void showContent();

    void showLoading();

    void onRefreshEmpty();

    void onRefreshFailure(String message);

    default void onFailureToast(String message) {
        ToastUtil.show(message);
    }
}
