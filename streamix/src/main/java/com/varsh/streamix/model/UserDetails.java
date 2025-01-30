package com.varsh.streamix.model;

import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity 
@Table(name="users")
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name="user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
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
