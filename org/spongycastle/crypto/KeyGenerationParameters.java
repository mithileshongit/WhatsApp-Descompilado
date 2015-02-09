package org.spongycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters {
    private SecureRandom random;
    private int strength;

    public KeyGenerationParameters(SecureRandom secureRandom, int i) {
        this.random = secureRandom;
        this.strength = i;
    }

    public int getStrength() {
        return this.strength;
    }

    public SecureRandom getRandom() {
        return this.random;
    }
}
