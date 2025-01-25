package com.varsh.streamix.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {
	private String id;

	@NonNull
	private String userName;

	@NonNull
	private String userEmailId;

	@NonNull
	private String userPassword;

	@NonNull
	private Long userPhoneNo;
}
