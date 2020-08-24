package com.crossyf.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crossyf.entity.UserAddress;
import com.crossyf.entity.bo.AddressBO;
import com.crossyf.service.UserAddressService;
import com.crossyf.utils.JsonResponse;
import com.crossyf.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Created by YangFan.
 * @date 2020/8/24ø
 * 功能: 用户地址管理控制器
 */

@RestController
@RequestMapping("address")
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@Slf4j
public class AddressController {

    @Autowired
    private UserAddressService usersAddressService;


    /**
     * 查询用户下的所有地址
     *
     * @param userId 用户id
     * @return 结果
     */
    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JsonResponse list(@RequestParam String userId) {
        if (StrUtil.isBlank(userId)) {
            return JsonResponse.errorMsg("");
        }
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserAddress> list = usersAddressService.list(queryWrapper);
        return JsonResponse.ok(list);
    }

    /**
     * 新增用户地址
     *
     * @param addressBO 地址bo
     * @return 结果
     */
    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResponse add(@RequestBody AddressBO addressBO) {
        JsonResponse checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        usersAddressService.addNewUserAddress(addressBO);
        return JsonResponse.ok();
    }


    /**
     * 修改用户地址信息
     *
     * @param addressBO 地址bo
     * @return 结果
     */
    @ApiOperation(value = "用户修改地址", notes = "用户修改地址", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResponse update(@RequestBody AddressBO addressBO) {

        if (StrUtil.isBlank(addressBO.getAddressId())) {
            return JsonResponse.errorMsg("修改地址错误：addressId不能为空");
        }
        JsonResponse checkRes = checkAddress(addressBO);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        usersAddressService.updateUserAddress(addressBO);
        return JsonResponse.ok();
    }

    /**
     * 删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return 结果
     */
    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResponse delete(
            @RequestParam String userId,
            @RequestParam String addressId) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(addressId)) {
            return JsonResponse.errorMsg("");
        }
        usersAddressService.deleteUserAddress(userId, addressId);
        return JsonResponse.ok();
    }

    /**
     * 设置用户默认地址
     *
     * @param userId    用户地址
     * @param addressId 地址id
     * @return 结果
     */
    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefault")
    public JsonResponse setDefault(
            @RequestParam String userId,
            @RequestParam String addressId) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(addressId)) {
            return JsonResponse.errorMsg("");
        }
        usersAddressService.updateUserAddressToBeDefault(userId, addressId);
        return JsonResponse.ok();
    }

    /**
     * 验证地址是否有效
     *
     * @param addressBO 地址bo
     * @return 结果
     */
    private JsonResponse checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StrUtil.isBlank(receiver)) {
            return JsonResponse.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JsonResponse.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            return JsonResponse.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JsonResponse.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JsonResponse.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StrUtil.isBlank(province) ||
                StrUtil.isBlank(city) ||
                StrUtil.isBlank(district) ||
                StrUtil.isBlank(detail)) {
            return JsonResponse.errorMsg("收货地址信息不能为空");
        }

        return JsonResponse.ok();
    }
}
