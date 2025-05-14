package pl.kamil_dywan.external.allegro.own.order;

import java.util.List;

public record OrderMoneyStats(

    List<OrderItemMoneyStats> orderItemsMoneyStats,
    List<OrderTaxSummary> orderTaxesSummaries,
    OrderTotalMoneyStats orderTotalMoneyStats
){}
