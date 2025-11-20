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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
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

		//Case02_test01エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		//不正なログインIDとパスワードを入力
		webDriver.findElement(By.id("loginId")).sendKeys("Case02TestId");
		webDriver.findElement(By.id("password")).sendKeys("Case02TestPW");

		//ログインボタン押下
		webDriver.findElement(
				By.cssSelector("input[type='submit'][value='ログイン']")).click();

		//エラーメッセージの表示を待機
		WebElement errorMessage = wait.until(
				ExpectedConditions.presenceOfElementLocated(
						By.cssSelector("span.error")));

		//エラーメッセージが表示されていることを確認
		assertTrue(errorMessage.getText().contains("ログイン"),
				"エラーメッセージが表示されていません");

		//Case02_test02エビデンス取得
		getEvidence(new Object() {
		});
	}
}
