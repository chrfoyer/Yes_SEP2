package Model;

import mediator.PasswordEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("ALL")
class UserListTest
{

    private final UserList userList = new UserList();

    private final LocalDate bday1 = LocalDate.of(1980, 1, 1);
    private final LocalDate bday2 = LocalDate.of(1995, 2, 2);
    private final LocalDate bday11 = LocalDate.of(1996, 2, 2);
    private final LocalDate bday12 = LocalDate.of(1997, 3, 2);
    private final LocalDate bday13 = LocalDate.of(1998, 4, 2);
    private String salt = PasswordEncryptor.getNewSalt();

    private final User user1 = new User("test1", "1234567", "adsf@", "asfsadf", "test1", bday1, salt);
    private final User user2 = new User("test2", "1234567", "adsf@", "asfsadf", "test2", bday2, salt);
    private final User user3 = new User("test11", "1234567", "adsf@", "asfsadf", "test11", bday11, salt);
    private final User user4 = new User("test12", "1234567", "adsf@", "asfsadf", "test12", bday12, salt);
    private final User user5 = new User("test13", "1234567", "adsf@", "asfsadf", "test13", bday13, salt);

    @BeforeEach
    void setUp() throws SQLException
    {
        userList.addUser(user1);
        userList.addUser(user2);
        userList.addUser(user3);
        userList.addUser(user4);
        userList.addUser(user5);
        salt = PasswordEncryptor.getNewSalt();
    }

    @Test
    void addUser_Z()
    {
        User user = null;
        assertThrows(IllegalArgumentException.class, () -> userList.addUser(user));
    }

    @Test
    void addUser_O()
    {
        LocalDate bday3 = LocalDate.of(1997, 3, 3);
        assertDoesNotThrow(() -> userList.addUser(new User("test3", "1234567", "asdf@", "asdfasfd", "test3", bday3, salt)));
    }

    @Test
    void addUser_M1()
    {
        LocalDate bday4 = LocalDate.of(1980, 1, 1);
        LocalDate bday5 = LocalDate.of(1985, 1, 1);
        LocalDate bday6 = LocalDate.of(1990, 1, 1);
        LocalDate bday7 = LocalDate.of(1993, 1, 1);
        LocalDate bday8 = LocalDate.of(2000, 1, 1);
        LocalDate bday9 = LocalDate.of(2002, 1, 1);
        userList.addUser(new User("test4", "1234567", "adsf@", "asfsadf", "test4", bday4, salt));
        userList.addUser(new User("test5", "1234567", "adsf@", "asfsadf", "test5", bday5, salt));
        userList.addUser(new User("test6", "1234567", "adsf@", "asfsadf", "test6", bday6, salt));
        userList.addUser(new User("test7", "1234567", "adsf@", "asfsadf", "test7", bday7, salt));
        userList.addUser(new User("test8", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));

        assertDoesNotThrow(() -> userList.addUser(new User("test9", "1234567", "adsf@", "asfsadf", "test9", bday9, salt)));
    }

    @Test
    void addUser_M2()
    {
        LocalDate bday4 = LocalDate.of(1980, 1, 1);
        LocalDate bday5 = LocalDate.of(1985, 1, 1);
        LocalDate bday6 = LocalDate.of(1990, 1, 1);
        LocalDate bday7 = LocalDate.of(1993, 1, 1);
        LocalDate bday8 = LocalDate.of(2000, 1, 1);
        assertDoesNotThrow(() ->
        {
            userList.addUser(new User("test4", "1234567", "adsf@", "asfsadf", "test4", bday4, salt));
            userList.addUser(new User("test5", "1234567", "adsf@", "asfsadf", "test5", bday5, salt));
            userList.addUser(new User("test6", "1234567", "adsf@", "asfsadf", "test6", bday6, salt));
            userList.addUser(new User("test7", "1234567", "adsf@", "asfsadf", "test7", bday7, salt));
            userList.addUser(new User("test8", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test14", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test15", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test16", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test17", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test18", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test19", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test20", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test21", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test22", "1234567", "adsf@", "asfsadf", "test8", bday8, salt));
            userList.addUser(new User("test9", "1234567", "adsf@", "asfsadf", "test9", bday5, salt));
        });
    }

    @Test
    void addUser_B1() {
        userList.addUser(new User("test20", "1234567", "adsf@", "asfsadf", "test8", bday1, salt));
        userList.addUser(new User("test21", "1234567", "adsf@", "asfsadf", "test8", bday1, salt));
        assertDoesNotThrow(() -> userList.addUser(new User("test22", "1234567", "adsf@", "asfsadf", "test8", bday1, salt)));
    }

    @Test
    void addUser_B2() {
        userList.addUser(new User("test19", "1234567", "adsf@", "asfsadf", "test8", bday1, salt));
        userList.addUser(new User("test20", "1234567", "adsf@", "asfsadf", "test8", bday1, salt));
        userList.addUser(new User("test21", "1234567", "adsf@", "asfsadf", "test8", bday2, salt));
        System.out.println(userList.get(0).getUsername());
        assertThrows(IllegalArgumentException.class, () -> userList.addUser(user1));
    }

    @Test
    void addUser_E()
    {
        assertThrows(NullPointerException.class, () -> userList.addUser(new User(null, null)));
    }

    @Test
    void removeUser_Z()
    {
        assertThrows(NullPointerException.class, () -> userList.removeUser(null));
    }

    @Test
    void removeUser_O()
    {
        assertDoesNotThrow(() -> userList.removeUser(user2));
    }

    @Test
    void removeUser_M()
    {
        userList.removeUser(user1);
        userList.removeUser(user2);
        userList.removeUser(user3);
        assertDoesNotThrow(() -> userList.removeUser(user4));
    }

    @Test
    void removeUser_B1()
    {
        assertDoesNotThrow(() ->
                {
                    userList.removeUser(user1);
                    userList.removeUser(user2);
                    userList.addUser(user1);
                    userList.addUser(user2);
                    userList.removeUser(user1);
                    userList.removeUser(user2);
                }
        );
    }

    @Test
    void removeUser_B2() {
        userList.removeUser(user1);
        userList.removeUser(user2);
        userList.addUser(user1);
        userList.addUser(user2);
        userList.removeUser(user1);
        userList.removeUser(user2);
        assertThrows(IllegalArgumentException.class, () -> userList.removeUser(user1));
    }

    @Test
    void removeUser_E()
    {
        assertThrows(NullPointerException.class, () -> userList.removeUser(null));
    }

    @Test
    void get_Z()
    {
        assertDoesNotThrow(() -> userList.get(0));
    }

    @Test
    void get_O()
    {
        assertDoesNotThrow(() -> userList.get(4));
    }

    @Test
    void get_M()
    {
        userList.get(0);
        userList.get(1);
        userList.get(2);
        userList.get(3);
        assertDoesNotThrow(() -> userList.get(4));
    }

    @Test
    void get_B1()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> userList.get(-1));
    }

    @Test
    void get_B2()
    {
        assertThrows(IndexOutOfBoundsException.class, () -> userList.get(5));
    }

    @Test
    void get_E()
    {
        UserList userList2 = new UserList();
        assertThrows(IndexOutOfBoundsException.class, () -> userList2.get(0));
    }

}