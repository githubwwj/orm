package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "rightcatsub")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RightCatSub implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int ids;

	@DatabaseField
	private int id;

	@DatabaseField
	private String name;

	@DatabaseField
	private String group;

	@DatabaseField
	private String img_url ;

	@DatabaseField(canBeNull = false, foreign = true,columnName = "rightCat_id",foreignAutoRefresh = true)
	private RightCat rightCat;

	public RightCatSub() {
		super();
	}

	public RightCatSub(int id, String name, String group, String img_url) {
		super();
		this.id = id;
		this.name = name;
		this.group = group;
		this.img_url = img_url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public RightCat getRightCat() {
		return rightCat;
	}

	public void setRightCat(RightCat rightCat) {
		this.rightCat = rightCat;
	}

	public int getIds() {
		return ids;
	}

	public void setIds(int ids) {
		this.ids = ids;
	}

	@Override
	public String toString() {
		return "RightCatSub{" +
				"ids=" + ids +
				", id=" + id +
				", name='" + name + '\'' +
				", rightCat=" + rightCat +
				'}';
	}
}
