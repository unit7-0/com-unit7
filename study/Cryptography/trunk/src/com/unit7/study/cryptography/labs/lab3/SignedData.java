package com.unit7.study.cryptography.labs.lab3;

import java.io.Serializable;

public interface SignedData extends Serializable {
    byte[] getSignature();

    void setSignature(byte[] sign);

    byte[] getData();

    void setData(byte[] data);

    String getHash();

    void setHash(String hash);

    String getCypher();

    void setCypher(String cypher);
}
