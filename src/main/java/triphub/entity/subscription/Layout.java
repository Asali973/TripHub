package triphub.entity.subscription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * Represents the layout of a user interface or page, including its design and
 * associated customizations.
 * <p>
 * Each layout is associated with a specific XHTML file and can have multiple
 * customizations.
 * </p>
 */

@Entity
public class Layout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String xhtmlFile;

	@OneToMany(mappedBy = "layout", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Customization> customizations = new ArrayList<>();

	/**
	 * Determines if the provided object is equal to the current layout. Two layouts
	 * are considered equal if they have the same identifier (ID).
	 * 
	 * @param obj the object to compare with the current layout
	 * @return {@code true} if the provided object is a layout with the same ID,
	 *         {@code false} otherwise
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Layout layout = (Layout) obj;
		return id != null && id.equals(layout.id);
	}

	/**
	 * Generates a hash code based on the layout's ID.
	 * 
	 * @return an integer representing the hash code of the layout
	 */

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
