package com.daming.bartersystem.DTO;

import com.daming.bartersystem.entitys.BarterOrder;
import lombok.Data;

import java.util.List;

@Data
public class GetSelfOrderResult {
    private List<BarterOrder> barterOrderList;
}
