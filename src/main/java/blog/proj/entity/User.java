package blog.proj.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "User")
@NoArgsConstructor
public class User extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(unique = true,nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String passWord;

    @Column
    private String role;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Board> board = new ArrayList<>();

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> folder=new ArrayList<>();

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> question=new ArrayList<>();

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment=new ArrayList<>();


    @Builder
    public User( String email,String nickName ,String passWord,String role) {
        this.email = email;
        this.nickName=nickName;
        this.passWord = passWord;
        this.role = role;
    }
    public void update(String passWord, String email ,String nickName) {
        this.passWord = passWord;
        this.email=email;
        this.nickName=nickName;
    }

    //spring security (UserDetails을 사용하기 위한 필수 메서드들)=================================
    @Override //권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    //사용자의 id 반환 (고유값)
    @Override
    public String getUsername() {
        return email;
    }

    //사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return passWord;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true; // true -> 만료되지 않았음
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; //true -> 잠금되지 않았음
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; //true -> 만료되지 않았음
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; //true -> 사용 가능
    }
}
