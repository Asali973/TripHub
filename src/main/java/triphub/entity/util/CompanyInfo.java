package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.UserViewModel;

@Entity
public class CompanyInfo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture logo;

    @OneToOne(cascade = CascadeType.ALL)
    private Picture picture;
    
    public static CompanyInfo createCompanyInfoFromViewModel(UserViewModel form) {
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setName(form.getCompanyName());

        Picture logo = new Picture();
        logo.setLink(form.getCompanyLogoLink());
        companyInfo.setLogo(logo);

        Picture picture = new Picture();
        picture.setLink(form.getCompanyPictureLink());
        companyInfo.setPicture(picture);

        return companyInfo;
    }
    
    public void updateCompanyInfoFromViewModel(UserViewModel form) {
        this.setName(form.getCompanyName());

        Picture logo = new Picture();
        logo.setLink(form.getCompanyLogoLink());
        this.setLogo(logo);

        Picture picture = new Picture();
        picture.setLink(form.getCompanyPictureLink());
        this.setPicture(picture);
    }

    public void initCompanyInfoViewModel(UserViewModel userViewModel) {
        userViewModel.setCompanyName(this.getName());

        if (this.getLogo() != null) {
            userViewModel.setCompanyLogoLink(this.getLogo().getLink());
        }

        if (this.getPicture() != null) {
            userViewModel.setCompanyPictureLink(this.getPicture().getLink());
        }
    }



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Picture getLogo() {
		return logo;
	}

	public void setLogo(Picture logo) {
		this.logo = logo;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
    
    
    
}
