package com.dtb.router.launcher;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import com.dtb.router.exception.InitException;
import com.dtb.router.facade.Postcard;
import com.dtb.router.facade.callback.NavigationCallback;
import com.dtb.router.facade.template.ILogger;
import com.dtb.router.utils.Consts;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Router facade
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/16 14:36
 */
public final class Router {
    // Key of raw uri
    public static final String RAW_URI = "NTeRQWvye18AkPd6G";
    public static final String AUTO_INJECT = "wmHzgD4lOj5o4241";

    private volatile static Router instance = null;
    private volatile static boolean hasInit = false;
    public static ILogger logger;

    private Router() {
    }

    /**
     * Init, it must be call before used router.
     */
    public static void init(Application application) {
        if (!hasInit) {
            logger = _Router.logger;
            _Router.logger.info(Consts.TAG, "Router init start.");
            hasInit = _Router.init(application);

            if (hasInit) {
                _Router.afterInit();
            }

            _Router.logger.info(Consts.TAG, "Router init over.");
        }
    }

    /**
     * Get instance of router. A
     * All feature U use, will be starts here.
     */
    public static Router getInstance() {
        if (!hasInit) {
            throw new InitException("Router::Init::Invoke init(context) first!");
        } else {
            if (instance == null) {
                synchronized (Router.class) {
                    if (instance == null) {
                        instance = new Router();
                    }
                }
            }
            return instance;
        }
    }

    public static synchronized void openDebug() {
        _Router.openDebug();
    }

    public static boolean debuggable() {
        return _Router.debuggable();
    }

    public static synchronized void openLog() {
        _Router.openLog();
    }

    public static synchronized void printStackTrace() {
        _Router.printStackTrace();
    }

    public static synchronized void setExecutor(ThreadPoolExecutor tpe) {
        _Router.setExecutor(tpe);
    }

    public synchronized void destroy() {
        _Router.destroy();
        hasInit = false;
    }


    public static synchronized void monitorMode() {
        _Router.monitorMode();
    }

    public static boolean isMonitorMode() {
        return _Router.isMonitorMode();
    }

    public static void setLogger(ILogger userLogger) {
        _Router.setLogger(userLogger);
    }

    /**
     * Inject params and services.
     */
    public void inject(Object thiz) {
        _Router.inject(thiz);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path Where you go.
     */
    public Postcard build(String path) {
        return _Router.getInstance().build(path);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param path  Where you go.
     * @param group The group of path.
     */
    @Deprecated
    public Postcard build(String path, String group) {
        return _Router.getInstance().build(path, group, false);
    }

    /**
     * Build the roadmap, draw a postcard.
     *
     * @param url the path
     */
    public Postcard build(Uri url) {
        return _Router.getInstance().build(url);
    }

    /**
     * Launch the navigation by type
     *
     * @param service interface of service
     * @param <T>     return type
     * @return instance of service
     */
    public <T> T navigation(Class<? extends T> service) {
        return _Router.getInstance().navigation(service);
    }

    /**
     * Launch the navigation.
     *
     * @param mContext    .
     * @param postcard    .
     * @param requestCode Set for startActivityForResult
     * @param callback    cb
     */
    public Object navigation(Context mContext, Postcard postcard, int requestCode, NavigationCallback callback) {
        return _Router.getInstance().navigation(mContext, postcard, requestCode, callback);
    }
}
