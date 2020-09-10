package cn.smbms.dao.bill;
import cn.smbms.pojo.Bill;

import java.util.List;

public interface BillMapper {
    /**
     * 增加订单
     * @param bill
     */
    public int add(Bill bill);

    /**
     * 通过查询条件获取供应商列表-模糊查询-getBillList
     * @param bill
     */
    public List<Bill> getBillList(Bill bill);

    /**
     * 通过delId删除Bill
     * @param delId
     */
    public int deleteBillById(String delId);

    /**
     * 通过billId获取Bill
     * @param id
     */
    public Bill getBillById(String id);

    /**
     * 修改订单信息
     * @param bill
     */
    public int modify(Bill bill);
    /**
     * 根据供应商ID查询订单数量
     * @param providerId
     */
    public int getBillCountByProviderId(String providerId);
    public Bill getLoginBill(String billCode);

}
