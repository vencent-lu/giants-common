/**
 *
 */
package com.giants.common.codec.rsa;

import java.io.Serializable;

/**
 * @author vencent.lu
 *
 */
public abstract class Key implements Serializable {
    private static final long serialVersionUID = -2971365067502323403L;

    private int keysize;

    public Key(int keysize) {
        super();
        this.keysize = keysize;
    }

    public int getKeysize() {
        return keysize;
    }

}
