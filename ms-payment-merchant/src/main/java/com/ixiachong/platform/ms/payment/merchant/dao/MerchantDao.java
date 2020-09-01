/*
 * Project: Accounts
 * Document: MerchantDao
 * Date: 2020/8/6 19:15
 * Author: fengzl
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.merchant.dao;

import com.ixiachong.platform.ms.payment.merchant.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @Author fengzl
 * @Date 2020/8/6
 */
public interface MerchantDao extends JpaRepository<Merchant, String> {
    boolean existsByAccountNo(String accountNo);

    Optional<Merchant> findByNo(String no);

    @Query("select o.id from Merchant o where o.no = ?1")
    String getIdByNo(String no);
}
