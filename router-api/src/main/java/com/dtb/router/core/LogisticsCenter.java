package com.dtb.router.core;

import android.content.Context;
import android.net.Uri;

import com.dtb.router.exception.HandlerException;
import com.dtb.router.exception.NoRouteFoundException;
import com.dtb.router.facade.Postcard;
import com.dtb.router.facade.enums.TypeKind;
import com.dtb.router.facade.model.RouteMeta;
import com.dtb.router.facade.template.IInterceptorGroup;
import com.dtb.router.facade.template.IProvider;
import com.dtb.router.facade.template.IProviderGroup;
import com.dtb.router.facade.template.IRouteGroup;
import com.dtb.router.facade.template.IRouteRoot;
import com.dtb.router.launcher.Router;
import com.dtb.router.utils.ClassUtils;
import com.dtb.router.utils.Consts;
import com.dtb.router.utils.MapUtils;
import com.dtb.router.utils.PackageUtils;
import com.dtb.router.utils.TextUtils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * LogisticsCenter contains all of the map.
 * <p>
 * 1. Creates instance when it is first used.
 * 2. Handler Multi-Module relationship map(*)
 * 3. Complex logic to solve duplicate group definition
 *
 * @author Alex <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 16/8/23 15:02
 */
public class LogisticsCenter {
    private static Context mContext;
    static ThreadPoolExecutor executor;
    private static boolean registerByPlugin;

    /**
     * router-auto-register plugin will generate code inside this method
     * call this method to register all Routers, Interceptors and Providers
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @since 2017-12-06
     */
    private static void loadRouterMap() {
        registerByPlugin = false;
        //auto generate register code by gradle plugin: router-auto-register
        // looks like below:
        // registerRouteRoot(new Router..Root..modulejava());
        // registerRouteRoot(new Router..Root..modulekotlin());
    }

    /**
     * register by class name
     * Sacrificing a bit of efficiency to solve
     * the problem that the main dex file size is too large
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @param className class name
     */
    private static void register(String className) {
        if (!TextUtils.isEmpty(className)) {
            try {
                Class<?> clazz = Class.forName(className);
                Object obj = clazz.getConstructor().newInstance();
                if (obj instanceof IRouteRoot) {
                    registerRouteRoot((IRouteRoot) obj);
                } else if (obj instanceof IProviderGroup) {
                    registerProvider((IProviderGroup) obj);
                } else if (obj instanceof IInterceptorGroup) {
                    registerInterceptor((IInterceptorGroup) obj);
                } else {
                    Router.logger.info(Consts.TAG, "register failed, class name: " + className
                            + " should implements one of IRouteRoot/IProviderGroup/IInterceptorGroup.");
                }
            } catch (Exception e) {
                Router.logger.error(Consts.TAG,"register class error:" + className);
            }
        }
    }

    /**
     * method for router-auto-register plugin to register Routers
     * @param routeRoot IRouteRoot implementation class in the package: com.alibaba.android.router.core.routers
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @since 2017-12-06
     */
    private static void registerRouteRoot(IRouteRoot routeRoot) {
        markRegisteredByPlugin();
        if (routeRoot != null) {
            routeRoot.loadInto(Warehouse.groupsIndex);
        }
    }

    /**
     * method for router-auto-register plugin to register Interceptors
     * @param interceptorGroup IInterceptorGroup implementation class in the package: com.alibaba.android.router.core.routers
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @since 2017-12-06
     */
    private static void registerInterceptor(IInterceptorGroup interceptorGroup) {
        markRegisteredByPlugin();
        if (interceptorGroup != null) {
            interceptorGroup.loadInto(Warehouse.interceptorsIndex);
        }
    }

    /**
     * method for router-auto-register plugin to register Providers
     * @param providerGroup IProviderGroup implementation class in the package: com.alibaba.android.router.core.routers
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @since 2017-12-06
     */
    private static void registerProvider(IProviderGroup providerGroup) {
        markRegisteredByPlugin();
        if (providerGroup != null) {
            providerGroup.loadInto(Warehouse.providersIndex);
        }
    }

    /**
     * mark already registered by router-auto-register plugin
     * @author billy.qi <a href="mailto:qiyilike@163.com">Contact me.</a>
     * @since 2017-12-06
     */
    private static void markRegisteredByPlugin() {
        if (!registerByPlugin) {
            registerByPlugin = true;
        }
    }

    /**
     * LogisticsCenter init, load all metas in memory. Demand initialization
     */
    public synchronized static void init(Context context, ThreadPoolExecutor tpe) throws HandlerException {
        mContext = context;
        executor = tpe;

        try {
            long startInit = System.currentTimeMillis();
            //billy.qi modified at 2017-12-06
            //load by plugin first
            loadRouterMap();
            if (registerByPlugin) {
                Router.logger.info(Consts.TAG, "Load router map by router-auto-register plugin.");
            } else {
                Set<String> routerMap;

                // It will rebuild router map every times when debuggable.
                if (Router.debuggable() || PackageUtils.isNewVersion(context)) {
                    Router.logger.info(Consts.TAG, "Run with debug mode or new install, rebuild router map.");
                    // These class was generated by router-compiler.
                    routerMap = ClassUtils.getFileNameByPackageName(mContext, Consts.ROUTE_ROOT_PAKCAGE);
                    if (!routerMap.isEmpty()) {
                        context.getSharedPreferences(Consts.Router_SP_CACHE_KEY, Context.MODE_PRIVATE).edit().putStringSet(Consts.Router_SP_KEY_MAP, routerMap).apply();
                    }

                    PackageUtils.updateVersion(context);    // Save new version name when router map update finishes.
                } else {
                    Router.logger.info(Consts.TAG, "Load router map from cache.");
                    routerMap = new HashSet<>(context.getSharedPreferences(Consts.Router_SP_CACHE_KEY, Context.MODE_PRIVATE).getStringSet(Consts.Router_SP_KEY_MAP, new HashSet<String>()));
                }

                Router.logger.info(Consts.TAG, "Find router map finished, map size = " + routerMap.size() + ", cost " + (System.currentTimeMillis() - startInit) + " ms.");
                startInit = System.currentTimeMillis();

                for (String className : routerMap) {
                    if (className.startsWith(Consts.ROUTE_ROOT_PAKCAGE + Consts.DOT + Consts.SDK_NAME + Consts.SEPARATOR + Consts.SUFFIX_ROOT)) {
                        // This one of root elements, load root.
                        ((IRouteRoot) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.groupsIndex);
                    } else if (className.startsWith(Consts.ROUTE_ROOT_PAKCAGE + Consts.DOT + Consts.SDK_NAME + Consts.SEPARATOR + Consts.SUFFIX_INTERCEPTORS)) {
                        // Load interceptorMeta
                        ((IInterceptorGroup) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.interceptorsIndex);
                    } else if (className.startsWith(Consts.ROUTE_ROOT_PAKCAGE + Consts.DOT + Consts.SDK_NAME + Consts.SEPARATOR + Consts.SUFFIX_PROVIDERS)) {
                        // Load providerIndex
                        ((IProviderGroup) (Class.forName(className).getConstructor().newInstance())).loadInto(Warehouse.providersIndex);
                    }
                }
            }

            Router.logger.info(Consts.TAG, "Load root element finished, cost " + (System.currentTimeMillis() - startInit) + " ms.");

            if (Warehouse.groupsIndex.size() == 0) {
                Router.logger.error(Consts.TAG, "No mapping files were found, check your configuration please!");
            }

            if (Router.debuggable()) {
                Router.logger.debug(Consts.TAG, String.format(Locale.getDefault(), "LogisticsCenter has already been loaded, GroupIndex[%d], InterceptorIndex[%d], ProviderIndex[%d]", Warehouse.groupsIndex.size(), Warehouse.interceptorsIndex.size(), Warehouse.providersIndex.size()));
            }
        } catch (Exception e) {
            throw new HandlerException(Consts.TAG + "Router init logistics center exception! [" + e.getMessage() + "]");
        }
    }

    /**
     * Build postcard by serviceName
     *
     * @param serviceName interfaceName
     * @return postcard
     */
    public static Postcard buildProvider(String serviceName) {
        RouteMeta meta = Warehouse.providersIndex.get(serviceName);

        if (null == meta) {
            return null;
        } else {
            return new Postcard(meta.getPath(), meta.getGroup());
        }
    }

    /**
     * Completion the postcard by route metas
     *
     * @param postcard Incomplete postcard, should complete by this method.
     */
    public synchronized static void completion(Postcard postcard) {
        if (null == postcard) {
            throw new NoRouteFoundException(Consts.TAG + "No postcard!");
        }

        RouteMeta routeMeta = Warehouse.routes.get(postcard.getPath());
        if (null == routeMeta) {    // Maybe its does't exist, or didn't load.
            Class<? extends IRouteGroup> groupMeta = Warehouse.groupsIndex.get(postcard.getGroup());  // Load route meta.
            if (null == groupMeta) {
                throw new NoRouteFoundException(Consts.TAG + "There is no route match the path [" + postcard.getPath() + "], in group [" + postcard.getGroup() + "]");
            } else {
                // Load route and cache it into memory, then delete from metas.
                try {
                    if (Router.debuggable()) {
                        Router.logger.debug(Consts.TAG, String.format(Locale.getDefault(), "The group [%s] starts loading, trigger by [%s]", postcard.getGroup(), postcard.getPath()));
                    }

                    IRouteGroup iGroupInstance = groupMeta.getConstructor().newInstance();
                    iGroupInstance.loadInto(Warehouse.routes);
                    Warehouse.groupsIndex.remove(postcard.getGroup());

                    if (Router.debuggable()) {
                        Router.logger.debug(Consts.TAG, String.format(Locale.getDefault(), "The group [%s] has already been loaded, trigger by [%s]", postcard.getGroup(), postcard.getPath()));
                    }
                } catch (Exception e) {
                    throw new HandlerException(Consts.TAG + "Fatal exception when loading group meta. [" + e.getMessage() + "]");
                }

                completion(postcard);   // Reload
            }
        } else {
            postcard.setDestination(routeMeta.getDestination());
            postcard.setType(routeMeta.getType());
            postcard.setPriority(routeMeta.getPriority());
            postcard.setExtra(routeMeta.getExtra());

            Uri rawUri = postcard.getUri();
            if (null != rawUri) {   // Try to set params into bundle.
                Map<String, String> resultMap = TextUtils.splitQueryParameters(rawUri);
                Map<String, Integer> paramsType = routeMeta.getParamsType();

                if (MapUtils.isNotEmpty(paramsType)) {
                    // Set value by its type, just for params which annotation by @Param
                    for (Map.Entry<String, Integer> params : paramsType.entrySet()) {
                        setValue(postcard,
                                params.getValue(),
                                params.getKey(),
                                resultMap.get(params.getKey()));
                    }

                    // Save params name which need auto inject.
                    postcard.getExtras().putStringArray(Router.AUTO_INJECT, paramsType.keySet().toArray(new String[]{}));
                }

                // Save raw uri
                postcard.withString(Router.RAW_URI, rawUri.toString());
            }

            switch (routeMeta.getType()) {
                case PROVIDER:  // if the route is provider, should find its instance
                    // Its provider, so it must implement IProvider
                    Class<? extends IProvider> providerMeta = (Class<? extends IProvider>) routeMeta.getDestination();
                    IProvider instance = Warehouse.providers.get(providerMeta);
                    if (null == instance) { // There's no instance of this provider
                        IProvider provider;
                        try {
                            provider = providerMeta.getConstructor().newInstance();
                            provider.init(mContext);
                            Warehouse.providers.put(providerMeta, provider);
                            instance = provider;
                        } catch (Exception e) {
                            throw new HandlerException("Init provider failed! " + e.getMessage());
                        }
                    }
                    postcard.setProvider(instance);
                    postcard.greenChannel();    // Provider should skip all of interceptors
                    break;
                case FRAGMENT:
                    postcard.greenChannel();    // Fragment needn't interceptors
                default:
                    break;
            }
        }
    }

    /**
     * Set value by known type
     *
     * @param postcard postcard
     * @param typeDef  type
     * @param key      key
     * @param value    value
     */
    private static void setValue(Postcard postcard, Integer typeDef, String key, String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return;
        }

        try {
            if (null != typeDef) {
                if (typeDef == TypeKind.BOOLEAN.ordinal()) {
                    postcard.withBoolean(key, Boolean.parseBoolean(value));
                } else if (typeDef == TypeKind.BYTE.ordinal()) {
                    postcard.withByte(key, Byte.valueOf(value));
                } else if (typeDef == TypeKind.SHORT.ordinal()) {
                    postcard.withShort(key, Short.valueOf(value));
                } else if (typeDef == TypeKind.INT.ordinal()) {
                    postcard.withInt(key, Integer.valueOf(value));
                } else if (typeDef == TypeKind.LONG.ordinal()) {
                    postcard.withLong(key, Long.valueOf(value));
                } else if (typeDef == TypeKind.FLOAT.ordinal()) {
                    postcard.withFloat(key, Float.valueOf(value));
                } else if (typeDef == TypeKind.DOUBLE.ordinal()) {
                    postcard.withDouble(key, Double.valueOf(value));
                } else if (typeDef == TypeKind.STRING.ordinal()) {
                    postcard.withString(key, value);
                } else if (typeDef == TypeKind.PARCELABLE.ordinal()) {
                    // TODO : How to description parcelable value with string?
                } else if (typeDef == TypeKind.OBJECT.ordinal()) {
                    postcard.withString(key, value);
                } else {    // Compatible compiler sdk 1.0.3, in that version, the string type = 18
                    postcard.withString(key, value);
                }
            } else {
                postcard.withString(key, value);
            }
        } catch (Throwable ex) {
            Router.logger.warning(Consts.TAG, "LogisticsCenter setValue failed! " + ex.getMessage());
        }
    }

    /**
     * Suspend bussiness, clear cache.
     */
    public static void suspend() {
        Warehouse.clear();
    }
}
