package com.github.xuyuansheng.xbdynamicdatasource.dynamic;

/**
 * @author xuyuansheng
 * @date 2019-12-10 14:53
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static synchronized void setDataSourceKey(String key) {
//        checkState(null == CONTEXT_HOLDER.get(), "ContextHolder 已经设置过值了,请先清除!.");
        CONTEXT_HOLDER.set(key);
    }

    /**
     * Get current DataSource
     *
     * @return data source key
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * To set DataSource as default
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    private static void checkState(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }
}
