package xyz.wanghongtao.rebac.object.context;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.wanghongtao.rebac.object.dataObject.RelationDo;

import java.util.List;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-04-26
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RelationComputeContext {
  @NotNull
  String policyId;
  List<RelationDo> relationsToAdd;
  List<RelationDo> relationsToDelete;

  String triple;
}
