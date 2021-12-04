package ua.goit;

import lombok.SneakyThrows;

import java.util.List;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        int idUser = 8;

        User createdUser = HttpUtil.postRequest(newUser());
        System.out.println("creating a new user :\n" + createdUser);
        System.out.println("---------------------------------------------");

        User user = HttpUtil.getRequest(idUser);
        user.setName("Brandon");
        user.setUserName("Fly");
        user = HttpUtil.putRequest(user.getId(), user);
        System.out.println("---------------------------------------------");


        int statusResponse = HttpUtil.deleteRequest(idUser);
        System.out.println("status response is: " + statusResponse);
        System.out.println("---------------------------------------------");


        List<User> users = HttpUtil.getAllUsers();
        users.forEach(System.out::println);
        System.out.println("---------------------------------------------");


        User getUserById = HttpUtil.getRequestByUserId(idUser);
        System.out.println("user info by id :" + idUser + "\n" + getUserById);
        System.out.println("---------------------------------------------");


        users = HttpUtil.getRequestUserByName(getUserById.getUserName());
        System.out.println("user info by Name :" + getUserById.getUserName() + "\n" + users);
        System.out.println("---------------------------------------------");


        String  allCommentToLastPostOfUser = HttpUtil.getRequestCommentsOfLastPost(user);
        System.out.println(allCommentToLastPostOfUser);
        System.out.println("---------------------------------------------");

        List<Task> allOpenedTaskOfUser = HttpUtil.getRequestByTasks(user);
        allOpenedTaskOfUser.forEach(System.out::println);
    }

    public static User newUser() {
        return new User.UserBuilder()
                .id(13)
                .name("Bob Jackson")
                .userName("smartest-boy")
                .email("bob@boby.com")
                .address(new Address.AddressBuilder()
                        .street("Somestreet")
                        .suite("13")
                        .city("Neverland")
                        .zipcode("93799")
                        .geo(new Geo.GeoBuilder()
                                .lat(33.84)
                                .lng(-78.58)
                                .build())
                        .build())
                .phone("+1-21-445-613")
                .website("boby.com")
                .company(new Company.CompanyBuilder()
                        .name("Boby-heaven")
                        .catchPhrase("What is the catchPhrase?")
                        .bs("I don't know")
                        .build())
                .build();
    }
}
