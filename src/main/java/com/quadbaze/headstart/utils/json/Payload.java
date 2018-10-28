package com.quadbaze.headstart.utils.json;

import java.util.Map;

/**
 * Created by Olatunji O. Longe on 7/4/2017.
 */
@SuppressWarnings("unchecked")
public class Payload extends Jsonizable {

    public Payload(){
        super();
    }

    public Payload(Map<String, Object> map){
        super(map);
    }

    @Override
    public Payload set(String key, Object value) {
        return this.assign(key, value);
    }

}
