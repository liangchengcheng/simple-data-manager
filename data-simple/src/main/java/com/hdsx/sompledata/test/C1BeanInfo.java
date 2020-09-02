package com.hdsx.sompledata.test;

import com.hdsx.simpledata.XResolverData;
import com.hdsx.simpledata.annotation.ParamField;

public class C1BeanInfo implements XResolverData {
    @ParamField(order = 0) private MessageHeaderBean messageHeaderBean;
    @ParamField(order = 1) private MessageBodyC1Bean messageBodyC1Bean;

    public MessageHeaderBean getMessageHeaderBean() {
        return messageHeaderBean;
    }

    public void setMessageHeaderBean(MessageHeaderBean messageHeaderBean) {
        this.messageHeaderBean = messageHeaderBean;
    }

    public MessageBodyC1Bean getMessageBodyC1Bean() {
        return messageBodyC1Bean;
    }

    public void setMessageBodyC1Bean(MessageBodyC1Bean messageBodyC1Bean) {
        this.messageBodyC1Bean = messageBodyC1Bean;
    }
}
