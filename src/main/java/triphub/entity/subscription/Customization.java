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

    @Enumerated(EnumType.STRING)
    private LayoutType layoutType;

    private boolean useHeader;
    private boolean useFooter;
    private boolean showSidebar;
    private boolean stickySidebar;
    private boolean useDarkTheme;

    //... Getters, setters, etc.

    public enum LayoutType {
        STANDARD, GRID, LIST
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

	public LayoutType getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(LayoutType layoutType) {
		this.layoutType = layoutType;
	}

	public boolean isUseHeader() {
		return useHeader;
	}

	public void setUseHeader(boolean useHeader) {
		this.useHeader = useHeader;
	}

	public boolean isUseFooter() {
		return useFooter;
	}

	public void setUseFooter(boolean useFooter) {
		this.useFooter = useFooter;
	}

	public boolean isShowSidebar() {
		return showSidebar;
	}

	public void setShowSidebar(boolean showSidebar) {
		this.showSidebar = showSidebar;
	}

	public boolean isStickySidebar() {
		return stickySidebar;
	}

	public void setStickySidebar(boolean stickySidebar) {
		this.stickySidebar = stickySidebar;
	}

	public boolean isUseDarkTheme() {
		return useDarkTheme;
	}

	public void setUseDarkTheme(boolean useDarkTheme) {
		this.useDarkTheme = useDarkTheme;
	}
    
    
}
