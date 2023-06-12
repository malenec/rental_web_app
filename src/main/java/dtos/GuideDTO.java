package dtos;

import entities.Guide;
import entities.Trip;

import java.util.List;
import java.util.stream.Collectors;

public class GuideDTO {

    private String guideName;
    private String gender;
    private int birthYear;
    private String profile;
    private String imageUrl;

    private List<String> trips;

    public GuideDTO(String guideName, String gender, int birthYear, String profile, String imageUrl) {
        this.guideName = guideName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.profile = profile;
        this.imageUrl = imageUrl;
    }

    public GuideDTO(Guide guide){
        this.guideName = guide.getGuideName();
        this.gender = guide.getGender();
        this.birthYear = guide.getBirthYear();
        this.profile = guide.getProfile();
        this.imageUrl = guide.getImageUrl();
        if(guide.getTrips() != null)
            this.trips = guide.getTrips().stream().map(t -> t.getTripName()).collect(Collectors.toList());
    }

    public static List<GuideDTO> getDtos(List<Guide> guides){
        return guides.stream().map(g -> new GuideDTO(g)).collect(Collectors.toList());
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

    public List<String> getTrips() {
        return trips;
    }

    public void setTrips(List<String> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "GuideDTO{" +
                "guideName='" + guideName + '\'' +
                ", gender='" + gender + '\'' +
                ", birthYear=" + birthYear +
                ", profile='" + profile + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", trips=" + trips +
                '}';
    }
}
