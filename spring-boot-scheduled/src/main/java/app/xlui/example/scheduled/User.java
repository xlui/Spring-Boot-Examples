package app.xlui.example.scheduled;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "t_user")
public class User {
	@Id
	@GeneratedValue
	private int id;
	private LocalDate expire;

	public User() {
	}

	public User(LocalDate expire) {
		this.expire = expire;
	}

	@Override
	public String toString() {
		return "User[id = " + id + ", expire = " + expire + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getExpire() {
		return expire;
	}

	public void setExpire(LocalDate expire) {
		this.expire = expire;
	}
}
