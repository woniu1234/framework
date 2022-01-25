package com.common.frame.preference;

/**
 * @author lst
 * @desc desc
 * @date 2021/6/18
 */
public class SpUtil extends BasePreferences {
    private static final String BASIC_DATA_PREFERENCE_FILE_NAME = "basic_data_preference";
    private static SpUtil sInstance;

    public static SpUtil getInstance() {
        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getSPFileName() {
        return BASIC_DATA_PREFERENCE_FILE_NAME;
    }

}
