/*
 * Project: Payment
 * Document: IdentityKey
 * Date: 2020/7/23 7:47 下午
 * Author: wangbz
 *
 * Copyright © 2020 www.ixiachong.com Inc. All rights reserved.
 * 注意：本内容仅限于深圳瞎充集团有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.ixiachong.platform.ms.payment.crypto.model;

import com.ixiachong.commons.jpa.AbstractEditedEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "identity_key")
public class IdentityKey extends AbstractEditedEntity {
    @Column(name = "scene", length = 50)
    private String scene;
    @Column(name = "identity")
    private String identity;
    @Column(name = "key_type", length = 20)
    private String keyType;
    @Column(name = "key_value")
    private String keyValue;
}
