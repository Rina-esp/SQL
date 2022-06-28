package test;

import data.DbInteraction;
import data.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;
import page.VerificationPage;

import java.util.ArrayList;
import java.util.Random;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestVerification {
  public static ArrayList<User> users = new ArrayList<>();

  @BeforeAll
  static void generateDb() {
    DbInteraction.clearDb();
    for (int i = 0; i < 10; i++) {
      users.add(DbInteraction.addNewUserToDb());
    }
  }

  @AfterAll
  static void clear() {
    DbInteraction.clearDb();
  }

  @BeforeEach
  void login() {
    open("http://localhost:9999");
  }

  @Test
  void shouldLoginCorrectly() {
    User user = users.get(new Random().nextInt(users.size() - 1));
    var loginPage = new LoginPage();
    VerificationPage verificationPage = loginPage.validLogin(user);
    DashboardPage dashboardPage = verificationPage.validVerify(DbInteraction.getVerificationCode());
    assertEquals("Личный кабинет", dashboardPage.getHeading());
  }

}
