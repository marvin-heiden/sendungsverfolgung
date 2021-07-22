import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Person {
    private String name;
    private String street;
    private String houseNumber;
    private String city;
    private String postCode;
    private String cityDistrict;
    private String postBox;
    private String country;
    private String email;
    private String phone;
    private String fax;

    public Person(String name, String street, String houseNumber, String city, String cityDistrict, String postCode, String postBox, String country, String email, String phone, String fax) {
        this.name = name;
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.cityDistrict = cityDistrict;
        this.postCode = postCode;
        this.postBox = postBox;
        this.country = country;
        this.email = email;
        this.phone = phone;
        this.fax = fax;
    }

    public ObjectNode toJson() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode personNode = mapper.createObjectNode();

        if (!name.isEmpty()) personNode.put("Name", name);
        if (!street.isEmpty()) personNode.put("Street", street);
        if (!houseNumber.isEmpty()) personNode.put("HouseNumber", houseNumber);
        if (!city.isEmpty()) personNode.put("City", city);
        if (!postCode.isEmpty()) personNode.put("PostCode", postCode);
        if (!cityDistrict.isEmpty()) personNode.put("CityDistrict", cityDistrict);
        if (!postBox.isEmpty()) personNode.put("PostBox", postBox);
        if (!country.isEmpty()) personNode.put("Country", country);
        if (!email.isEmpty()) personNode.put("Email", email);
        if (!phone.isEmpty()) personNode.put("Phone", phone);
        if (!fax.isEmpty()) personNode.put("Fax", fax);

        return personNode;
    }

    public String getName() {
        return name;
    }

    public String getStreet() {
        return street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getCityDistrict() {
        return cityDistrict;
    }

    public String getPostBox() {
        return postBox;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }
}
