package entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "guide")

@NamedQuery(name = "Guide.deleteAllRows", query = "DELETE from Guide")

public class Guide implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "guide_name", length = 100, unique = true)
    private String guideName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "birth_year")
    private int birthYear;

    @Column(name = "profile")
    private String profile;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "guide", orphanRemoval = true)
    private List<Trip> trips = new ArrayList<>();

    public Guide() {
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void addTrip(Trip trip) {
        trips.add(trip);
        trip.setGuide(this);
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Guide{" +
                "guideName='" + guideName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthYear='" + birthYear + '\'' +
                ", profile='" + profile + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", trips=" + trips +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guide guide = (Guide) o;
        return getGuideName() != null && Objects.equals(getGuideName(), guide.getGuideName());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


