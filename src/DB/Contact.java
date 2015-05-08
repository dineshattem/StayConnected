package DB;

public class Contact {

	String name;
	String type;
	String contacted;
	String interval_type;
	String interval_time;
	int id;
	String email;
	String contact_number;
	String group_time;

	public Contact() {

	}

	public Contact(String type, String interval_time, int id) {
		super();
		this.type = type;
		this.interval_time = interval_time;
		this.id = id;
	}

	public Contact(int id, String type) {
		super();
		this.type = type;
		this.id = id;
	}

	public Contact(String interval_time, int id) {
		super();
		this.interval_time = interval_time;
		this.id = id;
	}

	public Contact(int id) {
		super();
		this.id = id;
	}

	public String getGroup_time() {
		return group_time;
	}

	public void setGroup_time(String group_time) {
		this.group_time = group_time;
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

	public String getContacted() {
		return contacted;
	}

	public void setContacted(String contacted) {
		this.contacted = contacted;
	}

	public String getInterval_type() {
		return interval_type;
	}

	public void setInterval_type(String interval_type) {
		this.interval_type = interval_type;
	}

	public String getInterval_time() {
		return interval_time;
	}

	public void setInterval_time(String interval_time) {
		this.interval_time = interval_time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact_number() {
		return contact_number;
	}

	public void setContact_number(String contact_number) {
		this.contact_number = contact_number;
	}

	public Contact(String type, int id, String group_time) {
		super();
		this.type = type;
		this.id = id;
		this.group_time = group_time;
	}

	public Contact(String type, String group_time) {
		super();
		this.type = type;
		this.group_time = group_time;
	}

	public Contact(String name, String type, String contacted,
			String contact_number, String interval_time) {
		super();
		this.name = name;
		this.type = type;
		this.contacted = contacted;
		this.contact_number = contact_number;
		this.interval_time = interval_time;
	}

	public Contact(String name, String type, String contact_number) {
		super();
		this.name = name;
		this.type = type;
		this.contact_number = contact_number;
	}

}
