package by.itacademy.matveenko.jd2.dao.connectionpool;

import java.util.ResourceBundle;
import by.itacademy.matveenko.jd2.dao.connectionpool.DBResourceManager;

public final class DBResourceManager {
    
	private final static DBResourceManager instance = new DBResourceManager();
    
    private DBResourceManager(){}
    
    private ResourceBundle bundle = ResourceBundle.getBundle("db");

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}