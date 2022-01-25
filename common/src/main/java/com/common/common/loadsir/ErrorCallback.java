package com.common.common.loadsir;

import com.common.common.R;
import com.kingja.loadsir.callback.Callback;

/**
 * @author lst
 * @Description: ErrorCallback(类描述)
 * @date 2021/6/18
 */
public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
