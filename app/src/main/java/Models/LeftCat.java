package Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "leftcat")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeftCat implements Serializable {   //左边菜单菜单栏

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField
	private int key;

	@DatabaseField
	private String name;

	@DatabaseField
	private String type;

	@ForeignCollectionField
	private Collection<RightCat> rightcat;


	private boolean isCheck=false;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<RightCat> getRightcat() {
		return rightcat;
	}

	public void setRightcat(Collection<RightCat> rightcat) {
		this.rightcat = rightcat;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setIsCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
}
