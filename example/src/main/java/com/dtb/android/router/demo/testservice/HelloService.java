package com.dtb.android.router.demo.testservice;


import com.dtb.router.facade.template.IProvider;

/**
 * TODO feature
 *
 * @author Alex <a href="mailto:zhilong.lzl@alibaba-inc.com">Contact me.</a>
 * @version 1.0
 * @since 2017/1/3 10:26
 */
public interface HelloService extends IProvider {
    void sayHello(String name);
}
