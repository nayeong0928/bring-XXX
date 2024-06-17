package com.bring.back.member;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Table(name="MEMBER")
@NoArgsConstructor(force = true)
public class Member {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String pwd;

    public Member(String id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public String getName(){
        return name;
    }

    public boolean matchPwd(String pwd){
        if(this.pwd.equals(pwd)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("Member [");
        sb.append(this.id);
        sb.append(", ");
        sb.append(this.name);
        sb.append("]");
        return sb.toString();
    }
}
