package org.common.domain;

/**返回数据对象
 * Created by paul on 2017/8/22.
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
