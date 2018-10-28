package com.quadbaze.headstart.utils.xml;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 2:11 AM
 */
public class XmlHandler {

    /**
     * Converts xml to Json Object
     * @param xml
     * @return json object representation of the input xml
     * @throws JSONException
     */
    public static JSONObject toJson(String xml) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if(StringUtils.isNotBlank(xml)){
            jsonObject = XML.toJSONObject(xml);
        }
        return jsonObject;
    }

}
