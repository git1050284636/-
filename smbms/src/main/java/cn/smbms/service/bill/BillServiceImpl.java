package cn.smbms.service.bill;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	private BillMapper billMapper;
	@Autowired
	private ProviderMapper providerMapper;

	@Override
	public Bill selectBillCodeExist(String billCode) {

		Bill bill = null;
		try {
			bill = billMapper.getLoginBill(billCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bill;
	}
	@Override
	@Transactional
	public boolean add(Bill bill) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			int add = billMapper.add(bill);
			if (add > 0) {
				flag = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			//在service层进行connection连接的关闭
		}
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill) {
		// TODO Auto-generated method stub

		List<Bill> billList = null;
		System.out.println("query productName ---- > " + bill.getProductName());
		System.out.println("query providerId ---- > " + bill.getProviderId());
		System.out.println("query isPayment ---- > " + bill.getIsPayment());
		try {
			billList = billMapper.getBillList(bill);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return billList;
	}

	@Override
	@Transactional
	public boolean deleteBillById(String delId) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			int count = billMapper.deleteBillById(delId);
			if (count > 0) {
				flag = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return flag;
	}

	@Override
	public Bill getBillById(String id) {
		// TODO Auto-generated method stub
		Bill bill = null;
		try {
			bill = billMapper.getBillById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;

		}
		return bill;
	}

	@Override
	@Transactional
	public boolean modify(Bill bill) {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			int modify = billMapper.modify(bill);
			if (modify > 0) {
				flag = true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;

		}
		return flag;
	}

}
