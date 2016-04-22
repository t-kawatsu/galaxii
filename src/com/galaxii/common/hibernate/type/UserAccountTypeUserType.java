package com.galaxii.common.hibernate.type;

import com.galaxii.common.entity.UserAccountType;

public class UserAccountTypeUserType extends BitEnumSetUserType<UserAccountType> {
	public UserAccountTypeUserType() {
		super(UserAccountType.class);
	}
}
