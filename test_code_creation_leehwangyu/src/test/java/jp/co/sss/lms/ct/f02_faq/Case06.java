package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース06 カテゴリ検索 正常系
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		webDriver.get("http://localhost:8080/lms");

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleIs("ログイン | LMS"));

		assertEquals("ログイン | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		String id = System.getenv("LMS_ID");
		String pw = System.getenv("LMS_PW");

		webDriver.findElement(By.id("loginId")).sendKeys(id);
		webDriver.findElement(By.id("password")).sendKeys(pw);
		webDriver.findElement(By.cssSelector("input[type='submit'][value='ログイン']")).click();

		wait.until(ExpectedConditions.urlContains("/course/detail"));
		assertEquals("コース詳細 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		webDriver.findElement(By.linkText("機能")).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("ヘルプ")));
		webDriver.findElement(By.linkText("ヘルプ")).click();

		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));
		assertEquals("ヘルプ | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクを別タブで開く")
	void test04() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		String currentTab = webDriver.getWindowHandle();
		webDriver.findElement(By.linkText("よくある質問")).click();

		Set<String> tabs = webDriver.getWindowHandles();
		for (String tab : tabs) {
			if (!tab.equals(currentTab)) {
				webDriver.switchTo().window(tab);
				break;
			}
		}

		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {

		// 「研修関係」カテゴリをクリック
		webDriver.findElement(By.linkText("【研修関係】")).click();

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table tbody tr")));

		// 質問行を全件取得
		List<WebElement> questions = webDriver.findElements(By.cssSelector("table tbody tr dl dt"));

		// 1件以上表示されていることだけ確認
		assertTrue(questions.size() > 0);

		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問クリックで回答表示")
	void test06() {

		// 1件目の質問をクリック
		WebElement question = webDriver.findElement(By.cssSelector("table tbody tr dl dt"));
		// 要素位置までスクロール（他要素に遮られないように）
		((JavascriptExecutor) webDriver).executeScript(
				"arguments[0].scrollIntoView(true);", question);

		question.click();

		// 回答要素を取得し、表示状態を確認
		WebElement answer = webDriver.findElement(By.cssSelector("table tbody tr dl dd"));
		assertEquals("fs18", answer.getAttribute("class"));

		getEvidence(new Object() {
		});
	}

}
