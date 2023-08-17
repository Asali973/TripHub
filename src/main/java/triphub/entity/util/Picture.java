package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.TourPackageFormViewModel;
import triphub.viewModel.UserViewModel;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int size;
    private int weight;
    private String link;
    
    @Enumerated(EnumType.STRING)
    private PictureType type;
    
    private String altText;
    
    
    public void updatePictureFromViewModel(UserViewModel form) {
    	if (form != null) {
        this.setLink(form.getProfilePicture());
    	}
    }
    
    public void initPictureViewModel(UserViewModel userViewModel) {
        if (userViewModel != null) {
            userViewModel.setProfilePicture(this.getLink());
        }
    }

    public void updatePicFromViewModel(TourPackageFormViewModel form) {
        this.setLink(form.getProfilePicture());
    }
    
    public void initPicViewModel(TourPackageFormViewModel userViewModel) {
        userViewModel.setProfilePicture(this.getLink());
    }

   
    // getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public PictureType getType() {
        return type;
    }

    public void setType(PictureType type) {
        this.type = type;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }
}

