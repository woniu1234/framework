package com.common.frame.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Pair;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.ArrayList;

public class FormCheck {

    private final ArrayList<Pair<EditText, ETCheck>> mEtList = new ArrayList<>();
    private boolean mEtResult = true;
    private final ArrayList<Pair<Checkable, CACheck>> mCaList = new ArrayList<>();
    private boolean mCaResult = true;
    private PassCallBack passCallBack;
    private FailCallBack failCallBack;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mEtResult = true;
            for (Pair<EditText, ETCheck> pair : mEtList) {
                if (!pair.second.check(pair.first)) {
                    mEtResult = false;
                    break;
                }
            }
            if (mEtResult && mCaResult) {
                passCallBack.pass();
            } else {
                failCallBack.fail();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public FormCheck add(EditText et, ETCheck etCheck) {
        mEtList.add(new Pair<>(et, etCheck));
        et.addTextChangedListener(textWatcher);
        mEtResult = false;
        return this;
    }

    public FormCheck add(CheckBox cb, CACheck check, CompoundButton.OnCheckedChangeListener listener) {
        mCaList.add(new Pair<>(cb, check));
        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onCheckedChanged(buttonView, isChecked);
            }
            mCaResult = true;
            for (Pair<Checkable, CACheck> pair : mCaList) {
                if (!pair.second.check(pair.first)) {
                    mCaResult = false;
                    break;
                }
            }
            if (mEtResult && mCaResult) {
                passCallBack.pass();
            } else {
                failCallBack.fail();
            }
        });
        mCaResult = false;
        return this;
    }

    public FormCheck pass(PassCallBack passCallBack) {
        this.passCallBack = passCallBack;
        return this;
    }

    public FormCheck fail(FailCallBack failCallBack) {
        this.failCallBack = failCallBack;
        return this;
    }

    public static class EmptyCheck implements ETCheck {
        @Override
        public boolean check(EditText et) {
            return !TextUtils.isEmpty(et.getText().toString());
        }
    }


    public static class LengthCheck implements ETCheck {
        private final int min;

        public LengthCheck(int min) {
            this.min = min;
        }

        @Override
        public boolean check(EditText et) {
            if (TextUtils.isEmpty(et.getText().toString())) return false;
            return et.getText().toString().length() >= min;
        }
    }

    public static class CACheck {
        public boolean check(Checkable checkable) {
            return checkable.isChecked();
        }
    }


    public interface ETCheck {
        boolean check(EditText et);
    }

    public interface PassCallBack {
        void pass();
    }

    public interface FailCallBack {
        void fail();
    }


}
