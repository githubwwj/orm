package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "rightcat")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RightCat implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int ids;

	@DatabaseField
	private int id;

	@DatabaseField
	private String name;

	@DatabaseField
	private String group;

	@ForeignCollectionField
	private Collection<RightCatSub> sub;

	@DatabaseField(canBeNull = false, foreign = true,columnName = "leftcat_id",foreignAutoRefresh = true)
	private LeftCat leftCat;

	@DatabaseField
	private String img_url ;

	public RightCat(int id, String name, String group, String img_url) {
		super();
		this.id = id;
		this.name = name;
		this.group = group;
		this.img_url = img_url;
	}

	public RightCat() {
		super();
	}

	public int getIds() {
		return ids;
	}

	public void setIds(int ids) {
		this.ids = ids;
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

	public Collection<RightCatSub> getSub() {
		return sub;
	}

	public void setSub(Collection<RightCatSub> sub) {
		this.sub = sub;
	}

	public LeftCat getLeftCat() {
		return leftCat;
	}

	public void setLeftCat(LeftCat leftCat) {
		this.leftCat = leftCat;
	}

	public String getImg_url() {
		return img_url;
	}

	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}

	@Override
	public String toString() {
		return "RightCat{" +
				"ids=" + ids +
				", id=" + id +
				", name='" + name + '\'' +
				", sub=" + sub +
				", leftCat=" + leftCat +
				'}';
	}
}
