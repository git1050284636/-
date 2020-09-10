package cn.smbms.tools;

import cn.smbms.dao.BaseDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static ConfigManager configManager;
    private static Properties properties;

    private ConfigManager() {
        //加载属性文件
        properties = new Properties();
        String configFile = "database.properties";
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*懒汉模式*/
   /* public static synchronized ConfigManager getInstance(){
        if(configManager == null){
            configManager = new ConfigManager() ;
        }
        return configManager;
    }*/
    public static class InitInstance {
        private static ConfigManager instance = new ConfigManager();
    }

    public static ConfigManager getInstance() {
        configManager = InitInstance.instance;
        return configManager;
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }


}
