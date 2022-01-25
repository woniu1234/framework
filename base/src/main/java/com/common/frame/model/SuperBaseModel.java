package com.common.frame.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.CallSuper;

import com.common.frame.preference.SpUtil;
import com.common.frame.utils.GsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author lst
 * @desc desc
 * @date 2021/6/18
 */
public abstract class SuperBaseModel<T> {

    protected Handler mUiHandler = new Handler(Looper.getMainLooper());
    private CompositeDisposable compositeDisposable;
    protected ReferenceQueue<IBaseModelListener<T>> mReferenceQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener<T>>> mWeakListenerArrayList;
    private BaseCachedData<T> mData;

    public SuperBaseModel() {
        mReferenceQueue = new ReferenceQueue<>();
        mWeakListenerArrayList = new ConcurrentLinkedQueue<>();
        if (getCachedPreferenceKey() != null) {
            mData = new BaseCachedData<T>();
        }
    }

    /**
     * 注册监听
     */
    public void register(IBaseModelListener<T> listener) {
        if (listener == null) {
            return;
        }

        synchronized (this) {
            // 每次注册的时候清理已经被系统回收的对象
            Reference<? extends IBaseModelListener<T>> releaseListener;
            while ((releaseListener = mReferenceQueue.poll()) != null) {
                mWeakListenerArrayList.remove(releaseListener);
            }

            for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                IBaseModelListener<T> listenerItem = weakListener.get();
                if (listenerItem == listener) {
                    return;
                }
            }
            WeakReference<IBaseModelListener<T>> weakListener = new WeakReference<>(listener, mReferenceQueue);
            mWeakListenerArrayList.add(weakListener);
        }

    }

    /**
     * 取消监听
     */
    public void unRegister(IBaseModelListener<T> listener) {

        if (listener == null) {
            return;
        }
        synchronized (this) {
            for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                IBaseModelListener<T> listenerItem = weakListener.get();
                if (listener == listenerItem) {
                    mWeakListenerArrayList.remove(weakListener);
                    break;
                }
            }
        }
    }

    /**
     * 缓存
     */
    protected void saveDataToPreference(T data) {
        mData.data = data;
        mData.updateTimeInMills = System.currentTimeMillis();
        SpUtil.getInstance().setString(getCachedPreferenceKey(), GsonUtils.toJson(mData));
    }

    public abstract void refresh();

    public abstract void load();

    protected abstract void notifyCachedData(T data);


    /**
     * 该model的数据是否需要缓存，如果需要请返回缓存的key
     */
    protected String getCachedPreferenceKey() {
        return null;
    }

    /**
     * 缓存的数据的类型
     */
    protected Type getTClass() {
        return null;
    }


    /**
     * 该model的数据是否有apk预制的数据，如果有请返回，默认没有
     */
    protected String getApkString() {
        return null;
    }

    /**
     * 是否更新数据，可以在这里设计策略，可以是一天一次，一月一次等等，
     * 默认是每次请求都更新
     */
    protected boolean isNeedToUpdate() {
        return true;
    }

    @CallSuper
    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(d);
    }

    public void getCachedDataAndLoad() {
        if (getCachedPreferenceKey() != null) {
            String saveDataString = SpUtil.getInstance().getString(getCachedPreferenceKey());
            if (!TextUtils.isEmpty(saveDataString)) {
                try {
                    T savedData = GsonUtils.fromLocalJson(new JSONObject(saveDataString).getString("data"), getTClass());
                    if (savedData != null) {
                        notifyCachedData(savedData);
                        if (isNeedToUpdate()) {
                            load();
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (getApkString() != null) {
                notifyCachedData(GsonUtils.fromLocalJson(getApkString(), getTClass()));
            }
        }
        load();
    }

    public interface IBaseModelListener<T> {
        default void onLoadFinish(T data) {

        }

        default void onLoadFinish(T data, int type) {

        }

        default void onLoadMoreFinish(T data) {

        }

        default void onLoadMoreFinish(T data, int type) {

        }

        default void onLoadFail(String prompt) {

        }

        default void onLoadFail(String prompt, int code) {

        }

        default void onLoadFail(String prompt, int code, int type) {

        }

        default void onLoadMoreFail(String prompt) {

        }

        default void onLoadMoreFail(String prompt, int type) {

        }

        default void onLoadMoreFail(String prompt, int code, int type) {

        }
    }

}
