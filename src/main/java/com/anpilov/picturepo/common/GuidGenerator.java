package com.anpilov.picturepo.common;

import java.util.UUID;

public class GuidGenerator {

    public static String nextId() {
        return UUID.randomUUID().toString();
    }
}
