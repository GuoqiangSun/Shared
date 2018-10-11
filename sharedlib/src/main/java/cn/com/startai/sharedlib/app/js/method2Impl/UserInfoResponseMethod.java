package cn.com.startai.sharedlib.app.js.method2Impl;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.startai.sharedlib.app.js.Utils.SharedCommonJsUtils;
import cn.com.swain.baselib.jsInterface.method.BaseResponseMethod2;

/**
 * author: Guoqiang_Sun
 * date : 2018/8/28 0028
 * desc :
 */
public class UserInfoResponseMethod extends BaseResponseMethod2 {

    public static UserInfoResponseMethod getUserInfoResponseMethod() {
        return new UserInfoResponseMethod();
    }

    public UserInfoResponseMethod() {
        super(SharedCommonJsUtils.TYPE_RESPONSE_USER_INFO);
    }

    @Override
    public void releaseCache() {
        super.releaseCache();
        this.email = this.mobile = this.userName = this.birthday = this.province = this.city = this.town
                = this.address = this.nickName = this.headPic = this.sex = this.firstName = this.lastName = null;
    }


    private String email;          // 邮箱
    private String mobile;         // 手机号

    private int isHavePwd = -1000;

    private String userName;    // 登录用户名
    private String birthday; // 生日

    private String province;  // 省
    private String city;// 市
    private String town; // 区

    private String address;// 详细地址
    private String nickName;  // 昵称
    private String headPic;  // 头像
    private String sex;// 性别
    private String firstName;// 名
    private String lastName;// 姓


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public int getIsHavePwd() {
        return isHavePwd;
    }

    public void setIsHavePwd(int isHavePwd) {
        this.isHavePwd = isHavePwd;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Override
    public String toMethod() {

        JSONObject data = new JSONObject();
        if (getResult()) {
            try {
                data.put("userName", userName);
                data.put("birthday", birthday);
                data.put("province", province);
                data.put("city", city);
                data.put("town", town);
                data.put("address", address);
                data.put("nickName", nickName);
                data.put("headPic", headPic);
                data.put("sex", sex);
                data.put("firstName", firstName);
                data.put("lastName", lastName);
                data.put("email", email);
                data.put("mobile", mobile);

                if (isHavePwd != -1000) { // 表示没有修改过这个值，所以不设置进来
                    data.put("isHavePwd", isHavePwd == 1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return toMethod(data);

    }
}
