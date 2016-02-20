package orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import Models.CategoryConver;
import Models.LeftCat;
import Models.RightCat;
import Models.RightCatSub;

public class DAOHelper extends OrmLiteSqliteOpenHelper {
	private static final String DATABASE_NAME = "orm";
	private static final int DATABASE_VERSON = 8;
	private Map<String, Dao> daos = new HashMap<String, Dao>();

	public DAOHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSON);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {

			LogUtil.i("tag", "-------- daohelper oncreate---------");
			Log.i(DAOHelper.class.getName(), "onCreate");


			//   =======分类 start
			TableUtils.createTable(connectionSource, CategoryConver.class);

			TableUtils.createTable(connectionSource, LeftCat.class);
			TableUtils.createTable(connectionSource, RightCat.class);
			TableUtils.createTable(connectionSource, RightCatSub.class);

			//   =======分类 end

		} catch (SQLException e) {
			Log.e(DAOHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			if (newVersion > oldVersion) {

				Log.i(DAOHelper.class.getName(), "onUpgrade");

				TableUtils.dropTable(connectionSource, CategoryConver.class, true);
				TableUtils.dropTable(connectionSource, LeftCat.class, true);
				TableUtils.dropTable(connectionSource, RightCat.class, true);
				TableUtils.dropTable(connectionSource, RightCatSub.class, true);


				onCreate(database, connectionSource);
			}
		} catch (SQLException e) {
			Log.e(DAOHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}



	public boolean deleteDatabase(Context context) {
		LogUtil.i("tag", "------delete database------");

		boolean b = context.deleteDatabase(DATABASE_NAME);

		LogUtil.i("tag", "---------- 00 " + b + " 00 -----------");
		return b;
	}




	public synchronized Dao getDao(Class clazz) throws SQLException
	{
		Dao dao = null;
		String className = clazz.getSimpleName();

		if (daos.containsKey(className))
		{
			dao = daos.get(className);
		}
		if (dao == null)
		{
			dao = super.getDao(clazz);
			daos.put(className, dao);
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close()
	{
		super.close();

		for (String key : daos.keySet())
		{
			Dao dao = daos.get(key);
			dao = null;
		}
		daos.clear();
	}


}
