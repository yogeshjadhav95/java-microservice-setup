package com.prime.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.prime.common.library.LoginUserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "app_user", indexes = { @Index(columnList = "id") })
public class User extends LoginUserDetails {

	@Id
	@Column(length = 64)
	private String id;

}
