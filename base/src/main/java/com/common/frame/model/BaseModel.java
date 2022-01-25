package com.common.frame.model;

import java.lang.ref.WeakReference;

/**
 * @author lst
 * @Description: BaseModel.java(类描述)
 * @date 2021/6/18
 */
public abstract class BaseModel<T> extends SuperBaseModel<T> {

    /**
     * 页面单一接口请求成功的回调
     *
     * @param data 返回数据
     */
    protected void loadSuccess(T data) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFinish(data);
                        }
                    }
                }
                /*
                如果我们需要缓存数据，加载成功，让我们保存他到preference
                 */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    /**
     * 页面多个接口请求成功的回调
     *
     * @param data 数据
     * @param type 接口类型
     */
    protected void loadSuccess(T data, int type) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFinish(data, type);
                        }
                    }
                }
                /*
                如果我们需要缓存数据，加载成功，让我们保存他到preference
                 */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    /**
     * 页面单一接口请求成功的回调--加载更多
     *
     * @param data 数据
     */
    protected void loadMoreSuccess(T data) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadMoreFinish(data);
                        }
                    }
                }
                /*
                如果我们需要缓存数据，加载成功，让我们保存他到preference
                 */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    /**
     * 页面多个接口请求成功的回调--加载更多
     *
     * @param data 数据
     * @param type 接口类型
     */
    protected void loadMoreSuccess(T data, int type) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadMoreFinish(data, type);
                        }
                    }
                }
                /*
                如果我们需要缓存数据，加载成功，让我们保存他到preference
                 */
                if (getCachedPreferenceKey() != null) {
                    saveDataToPreference(data);
                }
            }, 0);
        }
    }

    /**
     * 页面单一接口请求失败的回调
     *
     * @param prompt 错误信息
     */
    protected void loadFail(final String prompt) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFail(prompt);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 页面单一接口请求失败的回调--携带code
     *
     * @param prompt 错误信息
     * @param code   错误码
     */
    protected void loadFail(final String prompt, int code) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFail(prompt, code);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 页面多个接口请求失败的回调--携带code
     *
     * @param prompt 错误信息
     * @param code   错误码
     * @param type   接口类型
     */
    protected void loadFail(final String prompt, int code, int type) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadFail(prompt, code, type);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 页面单一接口请求失败的回调--加载更多
     *
     * @param prompt 错误信息
     */
    protected void loadMoreFail(final String prompt) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadMoreFail(prompt);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 页面单一接口请求失败的回调--携带code--加载更多
     *
     * @param prompt 错误信息
     * @param code   错误码
     */
    protected void loadMoreFail(final String prompt, int code) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadMoreFail(prompt, code);
                        }
                    }
                }
            }, 0);
        }
    }

    /**
     * 页面多个接口请求失败的回调--携带code--加载更多
     *
     * @param prompt 错误信息
     * @param code   错误码
     * @param type   接口类型
     */
    protected void loadMoreFail(final String prompt, int code, int type) {
        synchronized (this) {
            mUiHandler.postDelayed(() -> {
                for (WeakReference<IBaseModelListener<T>> weakListener : mWeakListenerArrayList) {
                    if (weakListener.get() != null) {
                        IBaseModelListener<T> listenerItem = weakListener.get();
                        if (listenerItem != null) {
                            listenerItem.onLoadMoreFail(prompt, code, type);
                        }
                    }
                }
            }, 0);
        }
    }

    @Override
    protected void notifyCachedData(T data) {
        loadSuccess(data);
    }

}
