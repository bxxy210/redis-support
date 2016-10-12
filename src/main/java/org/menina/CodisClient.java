package org.menina;

import org.menina.exception.CodisNotSupportException;

import java.util.Set;

/**
 * Created by meninaChimp on 2016/10/12 0012.
 */
public class CodisClient extends AbstractRedisSupport{

    @Override
    public Set<String> keys(String pattern) throws CodisNotSupportException {
        throw new CodisNotSupportException("Codis not support this operation : keys");
    }
}
