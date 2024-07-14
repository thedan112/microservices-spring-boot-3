package auth.enums;

public enum Messages {

	// Base error message
		SUCCESS("000000","SUCCESS"), 
		BAD_REQUEST("400", "BAD REQUEST"),
		ACCESS_DENIED_SERVER_ERROR("403", "ACCESS DENINED"),
		WHITELIST_DENIED_ERROR("403", "FORBIDEN"),
		NOT_FOUND("404", "NOT FOUND"), 
		UNAUTHORIZED("401", "UNAUTHORIZED"),
		INTERNAL_SERVER_ERROR("500", "INTERNAL SERVER ERROR"),
		
		/* user */
		USER_LOCKED("10000", "Người dùng đang bị khóa"), 
		USER_EXIST("100001", "Người dùng đã tồn tại."),
		USER_INVALID_OR_STATUS_INACTIVE("100002", "Người dùng chưa kích hoạt."),
		PASSWORD_EXPIRED("100003", "Mật khẩu hết hạn."), 
		PASSWORD_FAIL("100004", "Mật khẩu không đúng."), 
		USER_NOT_FOUND("100005", "Người dùng không tồn tại."),
		USER_BAD_CREDENTIAL("100006", "Người dùng hoặc mật khẩu không đúng."),
		USER_LEAK_INFO("100007", "Thông tin không hợp lệ."), 
		PASSWORD_INVALID("100007", "Mật khẩu không hợp lệ."), 
		PASSWORD_NOTMATCH("100008", "Xác nhận mật khẩu không đúng."),
		PASSWORD_NOTFOUND("100009", "Mật khẩu cũ không hợp lệ."), 
		;

		private final String code;
		private final String description;

		private Messages(String code, String description) {
			this.code = code;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getCode() {
			return code;
		}

		@Override
		public String toString() {
			return description;
		}
}
