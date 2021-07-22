package com.prime.auth.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.prime.auth.entity.User;
import com.prime.auth.projection.AuthUserProjection;
import com.prime.auth.projection.LoginProjection;
import com.prime.auth.projection.UserFailedAttemtProjetction;

public interface UserRepository extends JpaRepository<User, String> {

	@Query(value = "select id, login_id as loginId, password from app_user where upper(login_id) = upper(:loginId) and deleted = false", nativeQuery = true)
	Optional<LoginProjection> findByLoginId(String loginId);

	@Query(value = "select id, login_id as loginId, password,currency,device_id as deviceId,decimal_scale as decimalScale,time_zone as TimeZone,last_password_changed_date as LastPasswordChangedDate,locked,deleted,status,tenant_type as TenantType, tenant_id as TenantId,lang_code as LangCode,failed_attempts as failedAttempts from app_user where upper(login_id) = upper(:loginId) and deleted = false", nativeQuery = true)
	Optional<AuthUserProjection> getAuthUserByLoginId(String loginId);

	@Query(value = "select id, failed_attempts as failedAttempts from app_user where upper(login_id) = upper(:loginId) and deleted = false", nativeQuery = true)
	UserFailedAttemtProjetction getUserForFailedLogin(String loginId);

	@Modifying
	@Query(value = "update app_user set failed_attempts =:failed where id=:id", nativeQuery = true)
	void updateFailedAttemptes(String id, int failed);

	@Modifying
	@Query(value = "update app_user set locked =true where id=:id", nativeQuery = true)
	void lockUser(String id);

	@Modifying
	@Query(value = "update app_user set last_login_time =:lastLoginTime,login_ip_address=:loginIpAddress, device_id =:deviceID,latitude=:latitude,longitude=:longitude  where id=:id", nativeQuery = true)
	void updateUserLastLoginDetails(Date lastLoginTime, String loginIpAddress, String id, String deviceID,
			String latitude, String longitude);
}
