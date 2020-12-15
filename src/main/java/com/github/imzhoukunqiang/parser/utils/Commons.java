package com.github.imzhoukunqiang.parser.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Commons {
    private Commons() {
    }

    public static final char EMPTY_CHAR = ' ';

    public static void requireNonEmpty(Object... objects) {
        if (isEmpty(objects)) {
            throw new NullPointerException();
        }
        for (Object o : objects) {
            if (isEmpty(o)) {
                throw new NullPointerException();
            }
        }
    }

    public static void requireNonNull(Object... objects) {
        Objects.requireNonNull(objects);
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }

    public static <T> T nonNullOr(T obj, T other) {
        return isNull(obj) ? other : obj;
    }

    public static <T> T nonNullOr(T obj, Supplier<T> getter) {
        return isNull(obj) ? getter.get() : obj;
    }

    public static <T, E extends Throwable> T nonNullOrThrow(T obj, Supplier<? extends E> getter) throws E {
        if (!isNull(obj)) {
            return obj;
        } else {
            throw getter.get();
        }
    }


    public static boolean notNull(Object o) {
        return !isNull(o);
    }


    public static boolean notEmpty(Object o) {
        return !isEmpty(o);
    }

    public static <T, E> E nonNullThen(T obj, Function<T, ? extends E> getter) {
        return isNull(obj) ? null : getter.apply(obj);
    }

    public static <T, E> E nonNullThen(T obj, Function<T, ? extends E> getter, E defaultValue) {
        return isNull(obj) ? defaultValue : getter.apply(obj);
    }

    public static <T, E> E nonEmptyThen(T obj, Function<T, ? extends E> getter, E defaultValue) {
        return isEmpty(obj) ? defaultValue : getter.apply(obj);
    }

    public static <T, E> E nonEmptyThen(T obj, Function<T, ? extends E> getter) {

        return isEmpty(obj) ? null : getter.apply(obj);
    }

    public static <T extends Throwable> void assertCondition(boolean condition, T throwable) throws T {
        if (condition) {
            throw throwable;
        }
    }

    public static <T extends Throwable> void assertCondition(boolean condition, Supplier<T> throwableSupplier) throws
            T {
        if (condition) {
            throw throwableSupplier.get();
        }
    }

    public static <T extends Throwable> void assertCondition(BooleanSupplier tester,
                                                             Supplier<T> throwableSupplier) throws T {
        if (tester.getAsBoolean()) {
            throw throwableSupplier.get();
        }
    }

    public static boolean isNotNull(Object o) {
        return !Commons.isNull(o);
    }

    public static boolean isNull(Object o) {
        return o == null;
    }


    public static boolean isNotEmpty(Object o) {
        return !Commons.isEmpty(o);
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if (o instanceof CharSequence) {
            return ((CharSequence) o).length() == 0;
        }
        if (o instanceof Map) {
            return ((Map) o).size() == 0;
        }
        if (o instanceof Collection) {
            return ((Collection) o).isEmpty();
        }
        if (Commons.isArray(o)) {
            return Array.getLength(o) == 0;
        }
        if (o instanceof Optional) {
            return !((Optional) o).isPresent();
        }

        return false;
    }

    public static boolean isNotBlank(CharSequence s) {
        return !isBlank(s);
    }

    public static boolean isBlank(CharSequence s) {
        if (Commons.isEmpty(s)) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != EMPTY_CHAR) {
                return false;
            }
        }
        return true;
    }

    public static boolean isArray(Object o) {
        return o != null && o.getClass().isArray();
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        return o1.equals(o2);
    }


    public static <T, E extends Throwable> T requireNotEmpty(T o, Supplier<E> exception) throws E {
        Commons.assertCondition(() -> Commons.isEmpty(o), exception);
        return o;
    }

    public static <T, E extends Throwable> T requireNotNull(T o, Supplier<E> exception) throws E {
        Commons.assertCondition(() -> Commons.isNull(o), exception);
        return o;
    }

    public static boolean anyNull(Object... objects) {
        if (isNull(objects)) {
            return true;
        }
        for (Object o : objects) {
            if (isNull(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean anyEmpty(Object... objects) {
        if (isEmpty(objects)) {
            return true;
        }
        for (Object o : objects) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }


    public static void close(AutoCloseable... closeables) {
        if (isNull(closeables)) {
            return;
        }
        for (AutoCloseable closeable : closeables) {
            close(closeable);
        }

    }

    public static void close(AutoCloseable closeable) {
        if (isNull(closeable)) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception ignored) {

        }
    }}
