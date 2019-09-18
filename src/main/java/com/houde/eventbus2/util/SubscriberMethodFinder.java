package com.houde.eventbus2.util;

import com.houde.eventbus2.Subscribe;
import com.houde.eventbus2.SubscriberMethod;
import com.houde.eventbus2.ThreadMode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 订阅方法查找类
 */
public class SubscriberMethodFinder {

    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC;

    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();

    /**
     * 查找方法
     *
     * @param subscriberClass
     * @return
     */
    public  List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (subscriberMethods != null) {
            return subscriberMethods;
        }

        subscriberMethods = findUsingReflection(subscriberClass);
        if (subscriberMethods.isEmpty()) {
            throw new EventBusException("Subscriber " + subscriberClass
                    + " and its super classes have no public methods with the @Subscribe annotation");
        } else {
            METHOD_CACHE.put(subscriberClass, subscriberMethods);
            return subscriberMethods;
        }
    }

    private List<SubscriberMethod> findUsingReflection(Class<?> subscriberClass) {

        List<SubscriberMethod> subscriberMethods = new ArrayList<>();
        Class<?> clazz = subscriberClass;
        boolean skipSuperClasses = false;
        while (clazz != null) {
            Method[] methods;
            try {
                methods = subscriberClass.getDeclaredMethods();
            } catch (Throwable th) {
                methods = subscriberClass.getMethods();
                skipSuperClasses = true;
            }
            for (Method method : methods) {
                int modifiers = method.getModifiers();
                if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    if (parameterTypes.length == 1) {

                        Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                        if (subscribeAnnotation != null) {
                            Class<?> eventType = parameterTypes[0];
                            ThreadMode threadMode = subscribeAnnotation.threadMode();
                            subscriberMethods.add(new SubscriberMethod(method, eventType, threadMode,
                                    subscribeAnnotation.priority(), subscribeAnnotation.sticky()));
                        }
                    } else if (method.isAnnotationPresent(Subscribe.class)) {
                        String methodName = method.getDeclaringClass().getName() + "." + method.getName();
                        throw new EventBusException("@Subscribe method " + methodName +
                                "must have exactly 1 parameter but has " + parameterTypes.length);
                    }
                } else if (method.isAnnotationPresent(Subscribe.class)) {
                    String methodName = method.getDeclaringClass().getName() + "." + method.getName();
                    throw new EventBusException(methodName +
                            " is a illegal @Subscribe method: must be public, non-static, and non-abstract");
                }
            }
            //moveToSuperclass
            if (skipSuperClasses) {
                clazz = null;
            } else {
                clazz = clazz.getSuperclass();
                String clazzName = clazz.getName();
                /** Skip system classes, this just degrades performance. */
                if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                    clazz = null;
                }
            }
        }
        return subscriberMethods;
    }
}
