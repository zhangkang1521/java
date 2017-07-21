package dozer;

import org.dozer.Mapping;

/**
 * Created by zhangkang on 2017/7/21.
 */
public class A {
    private Long userId;
//    @Mapping("userName")
    private String username;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
