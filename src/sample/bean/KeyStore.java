package sample.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Bicycle on 2017/4/16.
 */
@XmlRootElement(name = "keystore")
public class KeyStore {

    private String aliasName;
    private String aliasPassword;
    private String storePassword;

    @XmlElement(name = "aliasName")
    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @XmlElement(name = "aliasPassword")
    public String getAliasPassword() {
        return aliasPassword;
    }

    public void setAliasPassword(String aliasPassword) {
        this.aliasPassword = aliasPassword;
    }

    @XmlElement(name = "storePassword")
    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }
}
