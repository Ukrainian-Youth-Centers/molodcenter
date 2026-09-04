package com.katok.molodcenter.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class NanoIdGenerator {
    private static final char[] ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String generate(int length) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, ALPHABET, length);
    }
}