package api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ReqresTest {
    public static final String URL = "https://reqres.in/";

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOk200());

        List<UserData> users = given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        // Проверка, что Avatar имеет в названии такое же Id
        users.forEach(x-> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        // Проверкаа, что адресс почты заканчивается на @reqres.in
        Assert.assertTrue(users.stream().allMatch(x->x.getEmail().endsWith("@reqres.in")));

//        // Проверка, что Avatar имеет в названии такое же Id (повтор первого метода)
//        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
//        List<String> ids = users.stream().map(x->x.getId().toString()).collect(Collectors.toList());
//        for (int i = 0; i < avatars.size(); i++) {
//            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
//        }
    }
}
