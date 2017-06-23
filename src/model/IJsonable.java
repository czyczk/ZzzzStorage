package model;

import util.JsonUtil;

/**
 * Created by czyczk on 2017-6-23.
 */
public interface IJsonable {
    default String toJson() {
        return JsonUtil.getGson().toJson(this);
    }
}
