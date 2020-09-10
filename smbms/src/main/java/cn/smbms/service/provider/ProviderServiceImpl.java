package cn.smbms.service.provider;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    @Autowired
    private ProviderMapper providerMapper;
    @Autowired
    private BillMapper billMapper;

    @Override
    public boolean add(Provider provider) {
        // TODO Auto-generated method stub
        boolean flag = false;
        try {
            int add = providerMapper.add(provider);

            if (add > 0) {
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
    public List<Provider> getProviderList(String proName, String proCode) {
        // TODO Auto-generated method stub

        List<Provider> providerList = null;
        System.out.println("query proName ---- > " + proName);
        System.out.println("query proCode ---- > " + proCode);
        try {
            providerList = providerMapper.getProviderList(proName, proCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        }
        return providerList;
    }

    /**
     * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
     * 若订单表中无该供应商的订单数据，则可以删除
     * 若有该供应商的订单数据，则不可以删除
     * 返回值billCount
     * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
     * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
     * <p>
     * ---判断
     * 如果billCount = -1 失败
     * 若billCount >= 0 成功
     */
    @Override
    public int deleteProviderById(String delId) {
        // TODO Auto-generated method stub
        int billCount = -1;
        Provider provider = providerMapper.getProviderById(delId.toString());
        if (provider.getIdPicPath() != null) {
            billCount = -1;
        } else {
            try {
                billCount = billMapper.getBillCountByProviderId(delId);
                if (billCount == 0) {
                    providerMapper.deleteProviderById(delId);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                throw e;

            }
        }
        return billCount;
    }

    @Override
    public Provider getProviderById(String id) {
        // TODO Auto-generated method stub
        Provider provider = null;
        try {
            provider = providerMapper.getProviderById(id);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            throw e;
        }
        return provider;
    }

    @Override
    public boolean modify(Provider provider) {
        // TODO Auto-generated method stub

        boolean flag = false;
        try {
            int modify = providerMapper.modify(provider);
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

    @Override
    public Provider selectProviderCodeExist(String proCode) {
	/*	// TODO Auto-generated method stub
		Connection connection = null;*/
        Provider provider = null;
        try {
            /*	connection = BaseDao.getConnection();*/
            provider = providerMapper.getLoginProvider(proCode);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return provider;
    }

}
