package com.heroku.labshare.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Sets {

    private Sets() {
    }

    public static <T> Set<T> of(T ... data) {
        Set<T> set = new HashSet<>();
        Collections.addAll(set, data);
        return set;
    }

}
