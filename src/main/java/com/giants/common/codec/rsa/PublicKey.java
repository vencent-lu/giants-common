/**
 *
 */
package com.giants.common.codec.rsa;

import java.security.interfaces.RSAPublicKey;

/**
 * @author vencent.lu
 *
 */
public class PublicKey extends Key {
    private static final long serialVersionUID = -4401649461688365395L;

    private RSAPublicKey rsaPublicKey;

    public PublicKey(int keysize, RSAPublicKey rsaPublicKey) {
        super(keysize);
        this.rsaPublicKey = rsaPublicKey;
    }

    public RSAPublicKey getRsaPublicKey() {
        return rsaPublicKey;
    }


}
