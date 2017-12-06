package com.ejar.carpurchase.utils.conf;

/**
 * Created by wxd1 on 2017/5/15.
 */

public class UrlConf {
    public static String IMGHOST = "http://47.90.67.109:8083";

    public static String HOST = "http://47.90.67.109:8083/";


    public static String SHOP_IMGHOST = "http://47.90.67.109:8084";

    public static String SHOP_HOST = "http://47.90.67.109:8084/";


    //线下

    //    public static String IMGHOST = "http://47.90.67.109:8084/shopUnion";

//    public static String HOST = "http://47.90.67.109:8084/shopUnion/";


//    public static String SHOP_IMGHOST = "http://192.168.102.11:8010/sjshop";
//
//    public static String SHOP_HOST = "http://192.168.102.11:8010/sjshop/";





    public static String LoginUrl = HOST + "user/front/yjUser/Userlogin";//登录

    public static String LogoutUrl = HOST + "user/front/yjUser/Retirement";//注销

    public static String RegisterUrl = HOST + "user/front/yjUser/Registration";//注册

    public static String GetCodeUrl = HOST + "user/front/yjUser/Get_code";//注册(忘记密码)  获取验证码

    public static String ForgetPwdUrl = HOST + "user/front/yjUser/Forgot_password";//忘记密码

    public static String ViewShopUrl = HOST + "user/front/tsEntityshop/queryEntiyShop";//查看商家

    public static String UserDataUrl = HOST + "user/front/yjUser/User_data";//个人中心数据

    public static String ModifyUserDataUrl = HOST + "user/front/yjUser/Modify_user_data";//修改个人数据

    public static String GetModifyPhoneCodeUrl = HOST + "user/front/yjUser/Replace_cell_phone";//修改手机获取验证码

    public static String ModifyPhoneUrl = HOST + "user/front/yjUser/registered";//修改手机

    public static String BecomShopUrl = HOST + "user/front/tsEntityshop/Apply";//申请成为店铺

    public static String UpdaeShopInfoUrl = HOST + "user/front/tsEntityshop/update";//修改店铺信息

    public static String DeleteShopUrl = HOST + "user/front/tsEntityshop/RevokeApply";//撤销申请店铺

    public static String LocationPickUrl = HOST + "front/yjAddres/query";//选择省市县

    public static String ViewShareQRUrl = HOST + "user/front/yjUser/qrcode";//用户查看分享二维码

    public static String ViewShareRegistrationUrl = HOST + "user/front/yjUser/queryTopUser";//用户查看分享记录 查看下线

    public static String AddSharePeopleUrl = HOST + "user/front/yjUser/Modify_referrals";//用户推荐

    public static String GetAddSharePeopleCodeUrl = HOST + "user/front/yjUser/Replace_cell_phone";//用户推荐 获取验证码

    public static String ModifyPwdUrl = HOST + "user/front/yjUser/Modify_password";//修改密码

    public static String ModifyPayPwdUrl = HOST + "user/front/yjUser/Change_payment_password";//修改支付密码

    public static String GetModifyPwdCodeUrl = HOST + "user/front/yjUser/Change_payment_code";//修改密码 的验证码

    public static String ChargeRegistration = HOST + "user/front/uOfflineDeposit/Recharge_user_order";//充值记录

    public static String BalanceChargeUrl = HOST + "user/front/uOfflineDeposit/Recharge_application";//余额充值

    public static String WithdrawRegistration = HOST + "user/front/uCarryMoeny/Recharge_user_withdrawals";//提现记录

    public static String WithdrawRequest = HOST + "user/front/uCarryMoeny/User_present_application";//提现申请

    public static String WithdrawRule = HOST + "user/front/uCarryMoeny/User_interface";//提现规则

    public static String BindCardUrl = HOST + "user/front/uPaybinding/Bind_bank_card";//绑定银行卡

    public static String ViewBindCardUrl = HOST + "user/front/uPaybinding/User_bank_card";//查看银行卡

    public static String ChangeBindCardUrl = HOST + "user/front/uPaybinding/Change_bank_card";//修改银行卡

    public static String DeleteBindCardUrl = HOST + "user/front/uPaybinding/Delete_bank_card";//删除银行卡

    public static String TransferRecordUrl = HOST + "user/front/uRevenueRecord/Records_of_consumption";//用户消费记录（转账，等等）  转账记录

    public static String GotoBanlanceUrl = HOST + "user/front/yjUser/transfer";//转到余额

    public static String BillFormUrl = HOST + "user/FtsOrderReport/ftOrderReport";//商家报单业绩

    public static String ConsumeRegistrationUrl = HOST + "user/FtsOrderReport/Report";//商家订单报备

    public static String GetUserInfoUrl = HOST + "user/FtsOrderReport/queryUser";//商家订单报备  根据帐号查看用户信息

    public static String GetMoneyFeefoUrl = HOST + "user/FtsOrderReport/PayRatio";//商家订单报备  输入现金支付的钱得到系统手续费

    public static String GetRedFeefoUrl = HOST + "user/FtsOrderReport/payRedMoney";//商家订单报备  输入红包得到系统手续费


    public static String ViewAllShopUrl = HOST + "user/front/tsEntityshop/queryAllShop";//查看全部商家

    public static String ViewShopDetailUrl = HOST + "user/front/tsEntityshop/queryShop";//查看商家详情（根据id）

    public static String AddShopImglUrl = HOST + "user/front/tsEntiyShopImg/add";//添加轮播图

    public static String QueryShopImglUrl = HOST + "user/front/tsEntiyShopImg/query";//查看轮播图

    public static String UpdateShopImglUrl = HOST + "user/front/tsEntiyShopImg/update";//修改轮播图

    public static String UpdateLogUrl = HOST + "user/front/tsEntityshop/updateLog";//修改Log


    public static String QueryTypeUrl = HOST + "front/yjOperateType/query";//查看店铺的经营类别


    public static String OrderCenterUrl = HOST + "user/FtsOrderReport/ftUserOrderReport ";//订单中心


    public static String GetVesionNo_URL = HOST + "front/yjUpdate/query";//获取新的版本


    //代理商
    /**
     * 代理商收入
     */
    public static String AgentEarnings_URL = HOST + "user/front/uRevenueRecord/Records_of_consumption";

    /**
     * 查看用户
     */
    public static String AgentSeePeople_URL = HOST + "user/front/yjUser/queryAgentUser";


    /**
     * 查看我的下线个我带来的收益
     */
    public static String MyMemberIncome_URL = HOST + " user/front/uRevenueRecord/Records_of_consumption";


    //团队

    /**
     * 查看团队信息
     */

    public static String Team_member_URL = HOST + "user/front/uTim/View_team";


    /**
     * 查看团队下线
     */

    public static String Team_See_People_URL = HOST + "user/front/uTim/Team_Offline_users";


    //商城


    /**
     * 商城分类
     */
    public static String ShopCatalog_URL = SHOP_HOST + "front/catalog/list";


    /**
     * 商城商品列表
     */
    public static String ShopList_URL = SHOP_HOST + "front/product/list";


    /**
     * 商城商品详情
     */
    public static String GoodDetail_URL = SHOP_HOST + "front/product/queryById";


    /**
     * 商城商品规格
     */
    public static String GoodSpec_URL = SHOP_HOST + "front/product/queryParameter";


    /**
     * 添加到购物车
     */
    public static String AddToCart_URL = SHOP_HOST + "front/cart/insert";


    /**
     * 立即购买
     */
    public static String BuyNow_URL = SHOP_HOST + "front/order/buyNow";


    /**
     * 收货地址列表
     */
    public static String AdressList_URL = SHOP_HOST + "front/address/list";


    /**
     * 删除地址
     */
    public static String DeleteAdress_URL = SHOP_HOST + "front/address/deleteAddress";


    /**
     * 编辑地址
     */
    public static String EditAdress_URL = SHOP_HOST + "front/address/updateAddress";


    /**
     * 设置默认地址
     */
    public static String SetDefault_URL = SHOP_HOST + "front/address/setDefault";


    /**
     * 新增收货地址
     */
    public static String AddAdress_URL = SHOP_HOST + "front/address/insert";


    /**
     * 购物车列表
     */
    public static String CartList_URL = SHOP_HOST + "front/cart/list";


    /**
     * 批量修改购物车商品
     */
    public static String EditCarts_URL = SHOP_HOST + "front/cart/updateCarts";


    /**
     * 批量删除 购物车
     */
    public static String DeleteCarts_URL = SHOP_HOST + "front/cart/deleteByIds";


    /**
     * 修改、删除 购物车
     */
    public static String ChangeCart_URL = SHOP_HOST + "front/cart/updateOrdelete";


    /**
     * 购物车结算
     */
    public static String SettleCart_URL = SHOP_HOST + "front/cart/settleBuycar";


    /**
     * 提交订单
     */
    public static String SubmitOrder_URL = SHOP_HOST + "front/order/submitOrder";


    /**
     * 订单列表
     */
    public static String OrderList_URL = SHOP_HOST + "front/order/orderList";


    /**
     * 取消订单
     */
    public static String CancelOrder_URL = SHOP_HOST + "front/order/cancel";


    /**
     * 确认收货
     */
    public static String ConfirOrder_URL = SHOP_HOST + "front/order/confirm";

    /**
     * 立即支付
     */
    public static String PayNowOrder_URL = SHOP_HOST + "front/order/payNow";

    /**
     * 订单详情
     */
    public static String OrderDetail_URL = SHOP_HOST + "front/order/orderdetail";

    /**
     * 删除订单
     */
    public static String DeleteOrder_URL = SHOP_HOST + "front/order/delete";


}
