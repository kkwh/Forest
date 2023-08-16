package com.example.forest.model;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.forest.dto.user.UserFindPasswordDto;
import com.example.forest.dto.user.UserInfoUpdateDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "USERS") //선아 개인 DB테이블 
@SequenceGenerator(name = "USERS_SEQ_GEN", sequenceName = "USERS_SEQ", allocationSize = 1)
public class User extends BaseTimeEntity implements UserDetails {
	
	@Id //프라이 머리키
	@GeneratedValue(generator = "USERS_SEQ_GEN", strategy = GenerationType.SEQUENCE)
	private long id;
	
	// 로그인 시 사용할 아이디
	@Column(nullable = false, unique = true)
	private String loginId;
	
	// 사이트 내 회원 닉네임
    @Column(nullable = true)
	private String nickname;
	
	// 회원의 이메일 주소
    @Column(nullable = true)
	private String email;

	// 비밀번호
    @Column(nullable = true)
	private String password;
	
	// 사용자 권한
    @Column(nullable = true)
	private Role role;
	
	// 프로필 이미지 파일
	@OneToOne
	@JoinColumn(name = "image_file_id")
	private ImageFile imageFile;

    @Builder 
    private User(String loginId, String password, String nickname, String email, ImageFile imageFile) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.imageFile = imageFile;
        this.role = Role.USER; //권한 바꾸는 곳, 관리자 = ADMIN 
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(role.getKey()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;// 계정(account)이 non-expired(만료되지 않음).
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; // 계정이 non-lock(잠기지 않음).
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;// 비밀번호가 non-expired.
	}

	@Override
	public boolean isEnabled() {
		return true;// 사용자 상세정보(UserDetails)가 활성화(enable)
	}

	@Override
	public String getUsername() {
		return this.loginId;
	}
    public User update(UserInfoUpdateDto dto) {
        this.id = dto.getId();
        this.nickname = dto.getNickname();
        
        return this;
      
        
    }
    public void updatepw(UserFindPasswordDto dto) {
       this.id = dto.getId(); 
    }

    
    
 

}
