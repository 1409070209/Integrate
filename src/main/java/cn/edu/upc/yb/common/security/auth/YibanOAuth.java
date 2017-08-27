package cn.edu.upc.yb.common.security.auth;

import cn.edu.upc.yb.common.dto.ErrorReporter;
import cn.edu.upc.yb.common.dto.YibanBasicUserInfo;
import cn.edu.upc.yb.common.security.model.App;
import cn.edu.upc.yb.common.security.model.UPCYbUserFactory;
import cn.edu.upc.yb.common.util.MCrypt;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Created by lylllcc on 17/6/3.
 */
public class YibanOAuth {

    private final Log logger = LogFactory.getLog(this.getClass());

    private String verify_request;
    private String appid;
    private String appkey;
    private YibanBasicUserInfo yibanBasicUserInfo;
    private boolean hasError = true;

    public YibanOAuth(String vq, App app){
        this.verify_request = vq;
        this.appid = app.getAppid();
        this.appkey = app.getAppkey();
    }

    public Object dealYibanOauth() {
        MCrypt mCrypt = new MCrypt(this.appid, this.appkey);
        logger.info("appid: " + this.appid );
        logger.info("appkey: " + this.appkey );
        String res = null;
        try {
            res = new String(mCrypt.decrypt(this.verify_request));
        } catch (Exception e) {
            e.printStackTrace();
            return new ErrorReporter(0, "error parse");
        }
        Gson gson = new Gson();
        try {
            logger.info("res: " + res);
            this.yibanBasicUserInfo = gson.fromJson(res, YibanBasicUserInfo.class);
            this.hasError = false;
            return this.yibanBasicUserInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ErrorReporter(0, "error parse");
        }
    }

    public String getVerify_request() {
        return verify_request;
    }

    public String getAppid() {
        return appid;
    }

    public String getAppkey() {
        return appkey;
    }

    public YibanBasicUserInfo getYibanBasicUserInfo() {
        return yibanBasicUserInfo;
    }

    public boolean isHasError() {
        return hasError;
    }
}
