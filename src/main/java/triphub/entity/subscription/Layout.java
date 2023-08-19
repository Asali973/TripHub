package triphub.entity.subscription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Layout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String xhtmlFile;
    
    @OneToMany(mappedBy = "layout", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Customization> customizations = new ArrayList<>();
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Layout layout = (Layout) obj;
        return id != null && id.equals(layout.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getXhtmlFile() {
		return xhtmlFile;
	}
	public void setXhtmlFile(String xhtmlFile) {
		this.xhtmlFile = xhtmlFile;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Customization> getCustomizations() {
		return customizations;
	}
	public void setCustomizations(List<Customization> customizations) {
		this.customizations = customizations;
	}

    
}
