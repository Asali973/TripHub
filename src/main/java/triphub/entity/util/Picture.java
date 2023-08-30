package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.TourPackageFormViewModel;
import triphub.viewModel.UserViewModel;
/**
 * Represents a picture with various attributes like link, size, and type.
 * <p>
 * This class provides methods to initialize and update its properties from different view models.
 * </p>
 */
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
    
    /**
     * Updates the {@code link} of the picture from a given {@code UserViewModel}.
     *
     * @param form the {@code UserViewModel} containing picture details.
     */
    public void updatePictureFromViewModel(UserViewModel form) {
    	if (form != null) {
        this.setLink(form.getProfilePicture());
    	}
    }
    
    /**
     * Initializes the given {@link UserViewModel} with the {@code link} of the picture.
     * <p>
     * This method sets the profile picture link of the provided user view model using the link of this picture.
     * If the given view model is null, this method does nothing.
     * </p>
     *
     * @param userViewModel the {@link UserViewModel} to be initialized with the picture link.
     */
    public void initPictureViewModel(UserViewModel userViewModel) {
        if (userViewModel != null) {
            userViewModel.setProfilePicture(this.getLink());
        }
    }

    /**
     * Updates the picture's {@code link} using the provided {@link TourPackageFormViewModel}.
     * <p>
     * This method retrieves the profile picture link from the tour package form view model and sets it as the link
     * for this picture.
     * </p>
     *
     * @param form the {@link TourPackageFormViewModel} containing the new picture link.
     */
    public void updatePicFromViewModel(TourPackageFormViewModel form) {
        this.setLink(form.getProfilePicture());
    }
    
    /**
     * Initializes the provided {@link TourPackageFormViewModel} with the {@code link} of the picture.
     * <p>
     * This method sets the profile picture link of the provided tour package form view model using the link of this picture.
     * </p>
     *
     * @param userViewModel the {@link TourPackageFormViewModel} to be initialized with the picture link.
     */
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

