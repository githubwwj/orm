package orm;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import Models.CategoryConver;
import Models.LeftCat;
import Models.RightCat;
import Models.RightCatSub;

public class DAOManager {
	public DAOHelper helper;

	public static DAOManager manager = null;

	public DAOManager(Context context) {
		helper = OpenHelperManager.getHelper(context, DAOHelper.class);
	}

	public static DAOManager getInstance(Context context) {
		if (manager == null) {
			manager = new DAOManager(context);
		}
		return manager;
	}

	public void close() {
		if (helper != null) {
			helper.close();
		}

		OpenHelperManager.releaseHelper();
		helper = null;
	}

	public void deleteDataBase(Context context) {
		helper.deleteDatabase(context);
		close();
	}

	/*************************************** 以下为  CategoryConver  操作 ******************************************/
	public void addCategoryConver(List<CategoryConver> data) throws SQLException {
		Dao dao=helper.getDao(CategoryConver.class);
		if (dao != null && data!=null && data.size()>0) {
			for(int k=0;k<data.size();k++){
				dao.create(data.get(k));
				LogUtil.i("tag","--------addCategoryConver : " + data.get(k).getImg_url() + "-----");
			}
		}
	}

	public int deleteAllCategoryConver() throws SQLException {
		Dao dao=helper.getDao(CategoryConver.class);
		dao.deleteBuilder().delete();
		return 0;
	}

	public List<CategoryConver> queryAllCategoryConver() throws SQLException {
		Dao dao=helper.getDao(CategoryConver.class);
		List<CategoryConver> list = dao.queryForAll();
		LogUtil.i("tag", "--------CategoryConver.size : " + list.size() + "-----");
		return list;
	}


	/***************************************CategoryConver End ******************************************/






	/*************************************** 以下为   Category  操作 ******************************************/
	public void addLeftCat(List<LeftCat> data) throws SQLException {
		Dao dao=helper.getDao(LeftCat.class);
		if (dao != null && data!=null && data.size()>0) {
			for(int k=0;k<data.size();k++){
				dao.create(data.get(k));
				LogUtil.i("tag","--------addLeftCat : " + data.get(k).getName() + "-----");
			}
		}
	}

	public void addRightCat(List<RightCat> data) throws SQLException {
		Dao dao=helper.getDao(RightCat.class);
		if (dao != null && data!=null && data.size()>0) {
			for(int k=0;k<data.size();k++){
				dao.create(data.get(k));
				LogUtil.i("tag","--------addRightCat : " + data.get(k).getName() + "-----");
			}
		}
	}

	public void addRightCat(RightCat data) throws SQLException {
		Dao dao=helper.getDao(RightCat.class);
		if (dao != null && data!=null) {
			dao.create(data);
		}
	}

	public void addRightCatSub(List<RightCatSub> data) throws SQLException {
		Dao dao=helper.getDao(RightCatSub.class);
		if (dao != null && data!=null && data.size()>0) {
			for(int k=0;k<data.size();k++){
				dao.create(data.get(k));
				LogUtil.i("tag","--------addRightCatSub : " + data.get(k).getName() + "-----");
			}
		}
	}

	public int getRightCatSize() throws SQLException {
		Dao dao=helper.getDao(RightCat.class);
		if (dao != null) {
			List<Integer> result=dao.queryRaw("select count(*) from rightcat").getResults();
			int size=result.get(0);
			LogUtil.i("tag","--------getRightCatSize size : " + size + "-----");
			return size;
		}
		return  0;
	}

	public int getRightCatSubSize() throws SQLException {
		Dao dao=helper.getDao(RightCatSub.class);
		if (dao != null) {
			List<Integer> result=dao.queryRaw("select count(*) from rightcatsub").getResults();
			int size=result.get(0);
			LogUtil.i("tag", "--------getRightCatSubSize size : " + size + "-----");
			return size;
		}
		return 0;
	}

	public int deleteLeftCat() throws SQLException {
		Dao leftCatDao=helper.getDao(LeftCat.class);
		leftCatDao.deleteBuilder().delete();
		return 0;
	}

	public int deleteRightCat() throws SQLException {
		Dao rightCatDao=helper.getDao(RightCat.class);
		rightCatDao.deleteBuilder().delete();
		return 0;
	}


	public int deleteRightCatSub() throws SQLException {
		Dao rightCatSubDao=helper.getDao(RightCatSub.class);
		rightCatSubDao.deleteBuilder().delete();
		return 0;
	}

	public List<LeftCat> queryAllLeftCat() throws SQLException {
		Dao dao=helper.getDao(LeftCat.class);
		List<LeftCat> list = dao.queryForAll();
		LogUtil.i("tag", "--------LeftCat.size : " + list.size() + "-----");
		return list;
	}

	public List<RightCat> queryAllRightCat() throws SQLException {
		Dao dao=helper.getDao(RightCat.class);
		List<RightCat> list = dao.queryForAll();
		LogUtil.i("tag", "--------RightCat.size : " + list.size() + "-----");
		return list;
	}

	public List<RightCat> queryRightCatWithLeftID(int leftID) throws SQLException {
		Dao dao=helper.getDao(RightCat.class);
		List<RightCat> list = dao.queryBuilder().where().eq("leftcat_id",leftID).query();
		LogUtil.i("tag", "--------RightCat.size : " + list.size() + "-----");
		return list;
	}

	public List<RightCatSub> queryAllRightCatSub() throws SQLException {
		Dao dao=helper.getDao(RightCatSub.class);
		List<RightCatSub> list = dao.queryForAll();
		LogUtil.i("tag","--------RightCatSub.size : " + list.size() + "-----");
		return list;
	}



//	public int deleteMySelfStature() throws SQLException {
//		Dao dao=helper.getDao(ModelStature.class);
//		DeleteBuilder deleteBuilder=dao.deleteBuilder();
//		deleteBuilder.where().eq("bMySelftStature", true);
//		int deleteResult= dao.delete(deleteBuilder.prepare());
//		LogUtil.i("tag", "--------delete bMySelftStature : " +deleteResult + "-----");
//		return deleteResult;
//	}

}
