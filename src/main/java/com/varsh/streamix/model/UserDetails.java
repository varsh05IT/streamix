package com.varsh.streamix.model;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity 
@Table(name="users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name="user_seq", sequenceName = "USER_SEQ", allocationSize = 1)
	private Long id;

	@NonNull
	private String userName;

	@NonNull
	@Column(unique=true)
	private String userEmailId;

	@NonNull
	private String userPassword;

	@NonNull
	private Long userPhoneNo;
	
	@Enumerated(EnumType.STRING)
    private Role role; 
	
	
}
