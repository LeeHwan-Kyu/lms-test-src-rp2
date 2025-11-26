package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース03
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

	@BeforeAll
	static void before() {
		createDriver();
	}

	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		//トップページURLにアクセス
		webDriver.get("http://localhost:8080/lms");

		//タイトル「ログイン | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleIs("ログイン | LMS"));

		//タイトルチェック
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//Case03_test01エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		//環境変数からID/PW取得
		String id = System.getenv("LMS_ID");
		String pw = System.getenv("LMS_PW");


		//ID/PW入力
		webDriver.findElement(By.id("loginId")).sendKeys(id);
		webDriver.findElement(By.id("password")).sendKeys(pw);

		//ログインボタン押下
		webDriver.findElement(
				By.cssSelector("input[type='submit'][value='ログイン']")).click();

		//遷移先が /course/detail になるまで待機
		wait.until(ExpectedConditions.urlContains("/course/detail"));

		// タイトルチェック
		assertEquals("コース詳細 | LMS", webDriver.getTitle());

		//Case03test02エビデンスを取得
		getEvidence(new Object() {
		});
	}
}
