package triphub.entity.util;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public java.util.Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
    
    
}
