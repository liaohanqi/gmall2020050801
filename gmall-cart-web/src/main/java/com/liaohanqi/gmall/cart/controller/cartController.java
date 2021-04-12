package com.liaohanqi.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.liaohanqi.gmall.bean.OmsCartItem;
import com.liaohanqi.gmall.bean.PmsSkuInfo;
import com.liaohanqi.gmall.service.CartService;
import com.liaohanqi.gmall.service.SkuService;
import com.liaohanqi.gmall.util.CookieUtil;
import com.liaohanqi.gmall.util.PriceUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class cartController {


    @Reference
    CartService cartService;

    @Reference
    SkuService skuService;

    //加入购物车
    @RequestMapping("addToCart")
    public String addToCart(HttpServletRequest request, HttpServletResponse response, OmsCartItem omsCartItem){

        //通过传入的参数，去销售属性表中查找出销售属性值
        PmsSkuInfo pmsSkuInfo = skuService.getSkuInfoById(omsCartItem.getProductSkuId());
        omsCartItem.setIsChecked("1");
        omsCartItem.setPrice(pmsSkuInfo.getPrice());
        omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        omsCartItem.setProductId(pmsSkuInfo.getProductId());
        omsCartItem.setProductName(pmsSkuInfo.getSkuName());
        omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());

        //模拟用户的情况,可以切换0/1，实现用户登录与未登录两种情况
        String userId = "1";

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        if (StringUtils.isNotBlank(userId)){

            //用户已登录
            omsCartItem.setMemberId(userId);
            omsCartItem.setMemberNickname("用户昵称");//模拟用户的名称

            //根据返回值判断，购物车是否为空，空则添加。不空，则更新。
            OmsCartItem omsCartItemFromDb = cartService.isCartExists(userId,omsCartItem);

            if (omsCartItemFromDb != null){
                //更新
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity().add(omsCartItem.getQuantity()));
                omsCartItemFromDb.setTotalPrice(omsCartItemFromDb.getPrice().multiply(omsCartItemFromDb.getPrice()));
                cartService.updateCart(omsCartItemFromDb);
            }else {
                cartService.addCart(omsCartItem);
            }

        }else {

            //用户未登录，操作cookie
            //从cookie拿值
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //判断是否能返回值
            if(StringUtils.isNotBlank(cartListCookie)){

                //返回有值
                //判断是否重复
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
                boolean b = is_new_cart(omsCartItems,omsCartItem);

                if(b){
                    omsCartItems.add(omsCartItem);
                }else {
                    for (OmsCartItem cartItem : omsCartItems) {

                        if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                            cartItem.setTotalPrice(omsCartItem.getPrice().multiply(omsCartItem.getQuantity()));
                        }
                    }
                }

            }else {

                //直接添加
                omsCartItems.add(omsCartItem);
            }
            //覆盖cookie
            CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(omsCartItems),1000*60*60*24,true);


        }

        return "redirect:/success.html";
    }

    private boolean is_new_cart(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {

        boolean b= true;
        for (OmsCartItem cartItem : omsCartItems) {
            if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                b=false;
            }
        }
        return b;
    }

    //购物车列表
    @RequestMapping("cartList")
    public String cartList(HttpServletRequest request, HttpServletResponse response, OmsCartItem omsCartItem, ModelMap modelMap){

        String userId = "1";
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        if(StringUtils.isNotBlank(userId)){
            //查缓存
            omsCartItems = cartService.cartList(userId);

        }else {
            //查cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if (StringUtils.isNotBlank(cartListCookie)){
                omsCartItems = JSON.parseArray(cartListCookie, OmsCartItem.class);
            }

        }
        modelMap.put("cartList",omsCartItem);
        if (omsCartItems !=null && omsCartItems.size()> 0){

            modelMap.put("totalAmount", PriceUtil.getTotalAmount(omsCartItems));
        }

        return "cartList";
    }

}
