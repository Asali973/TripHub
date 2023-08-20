package triphub.entity.subscription;

import javax.persistence.*;

@Entity
public class Customization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String primaryColor;
    private String secondaryColor;
    private String primaryFont;
    private String secondaryFont;
    private String logoUrl;
    private String backgroundUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "layout_id")
    private Layout layout;
    
    

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(String primaryColor) {
		this.primaryColor = primaryColor;
	}

	public String getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(String secondaryColor) {
		this.secondaryColor = secondaryColor;
	}

	public String getPrimaryFont() {
		return primaryFont;
	}

	public void setPrimaryFont(String primaryFont) {
		this.primaryFont = primaryFont;
	}

	public String getSecondaryFont() {
		return secondaryFont;
	}

	public void setSecondaryFont(String secondaryFont) {
		this.secondaryFont = secondaryFont;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

    
    
}
