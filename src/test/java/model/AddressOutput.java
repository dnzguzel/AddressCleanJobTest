package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class AddressOutput implements Serializable {

    public static final long serialVersionUID = 1L;

    private long districtId;
    private String district;
    private long districtXdockId;
    private String districtXdockName;
    private long districtAltId;
    private String districtAlt;
    private long districtAltXdockId;
    private String districtAltXdockName;
    private long townId;
    private String town;
    private long townXdockId;
    private String townXdockName;
    private long cityId;
    private String city;
    private String cleanText;
    private String cleanTextAlt;
    private BigDecimal cleanProcessScore;
    private List<String> allKeywordValues;

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getDistrictXdockId() {
        return districtXdockId;
    }

    public void setDistrictXdockId(long districtXdockId) {
        this.districtXdockId = districtXdockId;
    }

    public String getDistrictXdockName() {
        return districtXdockName;
    }

    public void setDistrictXdockName(String districtXdockName) {
        this.districtXdockName = districtXdockName;
    }

    public long getDistrictAltId() {
        return districtAltId;
    }

    public void setDistrictAltId(long districtAltId) {
        this.districtAltId = districtAltId;
    }

    public String getDistrictAlt() {
        return districtAlt;
    }

    public void setDistrictAlt(String districtAlt) {
        this.districtAlt = districtAlt;
    }

    public long getDistrictAltXdockId() {
        return districtAltXdockId;
    }

    public void setDistrictAltXdockId(long districtAltXdockId) {
        this.districtAltXdockId = districtAltXdockId;
    }

    public String getDistrictAltXdockName() {
        return districtAltXdockName;
    }

    public void setDistrictAltXdockName(String districtAltXdockName) {
        this.districtAltXdockName = districtAltXdockName;
    }

    public long getTownId() {
        return townId;
    }

    public void setTownId(long townId) {
        this.townId = townId;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public long getTownXdockId() {
        return townXdockId;
    }

    public void setTownXdockId(long townXdockId) {
        this.townXdockId = townXdockId;
    }

    public String getTownXdockName() {
        return townXdockName;
    }

    public void setTownXdockName(String townXdockName) {
        this.townXdockName = townXdockName;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCleanText() {
        return cleanText;
    }

    public void setCleanText(String cleanText) {
        this.cleanText = cleanText;
    }

    public String getCleanTextAlt() {
        return cleanTextAlt;
    }

    public void setCleanTextAlt(String cleanTextAlt) {
        this.cleanTextAlt = cleanTextAlt;
    }

    public BigDecimal getCleanProcessScore() {
        return cleanProcessScore;
    }

    public void setCleanProcessScore(BigDecimal cleanProcessScore) {
        this.cleanProcessScore = cleanProcessScore;
    }

    public List<String> getAllKeywordValues() {
        return allKeywordValues;
    }

    public void setAllKeywordValues(List<String> allKeywordValues) {
        this.allKeywordValues = allKeywordValues;
    }
}
