package com.base.mapper;

import com.base.domain.repair.entity.Repair;
import com.base.domain.repair.vo.EndRepairVo;
import com.base.domain.repair.vo.IngRepairVo;
import com.base.domain.repair.vo.NotreatRepairVo;
import com.base.domain.repair.vo.RepairDetailsVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RepairMapper extends Mapper<Repair> {

    List<NotreatRepairVo> notreatList(@Param("userguid") String userguid);

    List<IngRepairVo> ingList(@Param("userguid") String userguid, @Param("cuserguid") String cuserguid);

    List<EndRepairVo> endList(@Param("userguid") String userguid, @Param("success") Integer success);

    RepairDetailsVo repairDetails(String rowguid);
}
