package cn.smbms.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;
@Data
public class User {
	private Integer id; //id
	@NotEmpty(message = "用户编码不为空")
	private String userCode; //用户编码
	private String userName; //用户名称
	@Length(min = 6,max = 20 ,message = "密码长度在6-20")
	private String userPassword; //用户密码
	private Integer gender;  //性别
	@Past(message = "日期必须是过去时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;  //出生日期
	private String phone;   //电话
	private String address; //地址
	private Integer userRole;    //用户角色
	private Integer createdBy;   //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy;     //更新者
	private Date modifyDate;   //更新时间
	private Integer age;//年龄
	
	private String userRoleName;    //用户角色名称
	private String idPicPath;

	public User(){
		System.out.println("创建了User===================");
	}
	
	
	public String getUserRoleName() {
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	public Integer getAge() {
		/*long time = System.currentTimeMillis()-birthday.getTime();
		Integer age = Long.valueOf(time/365/24/60/60/1000).IntegerValue();*/
		Date date = new Date();
		Integer age = date.getYear()-birthday.getYear();
		return age;
	}
 
}
