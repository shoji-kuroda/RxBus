package com.github.shojikuroda.rxbus.components;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by shoji.kuroda on 2016/10/14.
 */

public class RxBus {

    private static final ConcurrentMap<Class<?>, Map<Class<?>, Set<Method>>> subscribers = new ConcurrentHashMap<>();
    private final Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus get() {
        return RxBusHolder.sInstance;
    }

    private static class RxBusHolder {
        private static final RxBus sInstance = new RxBus();
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public void register(final Object object) {

        Class<?> listenerClass = object.getClass();
        Map<Class<?>, Set<Method>> methods = subscribers.get(listenerClass);
        if (methods == null) {
            methods = new HashMap<>();
            parseSubscriberMethods(listenerClass, methods);
        }
        if (!methods.isEmpty()) {
            for (Map.Entry<Class<?>, Set<Method>> e : methods.entrySet()) {
                for (final Method m : e.getValue()) {
                    Observable toObserverable = bus.ofType(e.getKey());
                    if (toObserverable != null) {
                        bus.ofType(e.getKey()).subscribe(new Subscriber<Object>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Object o) {
                                try {
                                    m.invoke(object, o);
                                } catch (IllegalAccessException e1) {
                                } catch (InvocationTargetException e1) {
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private void parseSubscriberMethods(Class<?> listenerClass, Map<Class<?>, Set<Method>> subscriberMethods) {

        for (Method method : listenerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    return;
                }

                Class<?> eventType = parameterTypes[0];
                if (eventType.isInterface()) {
                    return;
                }

                if ((method.getModifiers() & Modifier.PUBLIC) == 0) {
                    return;
                }

                Set<Method> methods = subscriberMethods.get(eventType);
                if (methods == null) {
                    methods = new HashSet<>();
                    subscriberMethods.put(eventType, methods);
                }
                methods.add(method);
            }
        }
        subscribers.put(listenerClass, subscriberMethods);
    }

}
