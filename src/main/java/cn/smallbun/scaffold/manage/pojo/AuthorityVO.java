package cn.smallbun.scaffold.manage.pojo;

import cn.smallbun.scaffold.manage.entity.SysAuthorityTypeEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 系统角色VO
 * @author SanLi
 * Created by qinggang.zuo@gmail.com / 2689170096@qq.com on  2019/5/27
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统权限参数", description = "系统权限VO")
public class AuthorityVO extends SysAuthorityTypeEntity {
}
