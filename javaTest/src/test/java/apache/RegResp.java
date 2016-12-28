package apache;

/**
 * Created by Administrator on 12/25/2016.
 */
public class RegResp {

    private Boolean isOk;
    private String message;
    private String CASCheckKey;
    private String CASCheckVale;

    public Boolean getIsOk() {
        return isOk;
    }

    public void setIsOk(Boolean ok) {
        isOk = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCASCheckKey() {
        return CASCheckKey;
    }

    public void setCASCheckKey(String CASCheckKey) {
        this.CASCheckKey = CASCheckKey;
    }

    public String getCASCheckVale() {
        return CASCheckVale;
    }

    public void setCASCheckVale(String CASCheckVale) {
        this.CASCheckVale = CASCheckVale;
    }
}
