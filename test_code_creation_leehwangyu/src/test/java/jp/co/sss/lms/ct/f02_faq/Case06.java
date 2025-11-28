package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
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

		//トップページURLにアクセス
		webDriver.get("http://localhost:8080/lms");

		//タイトル「ログイン | LMS」になるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.titleIs("ログイン | LMS"));

		//タイトルチェック
		assertEquals("ログイン | LMS", webDriver.getTitle());

		//Case05_test01エビデンスを取得
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

		//Case05_test02エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// 「機能」メニューをクリックしてドロップダウンを開く
		webDriver.findElement(By.linkText("機能")).click();

		// 「ヘルプ」リンクが表示されるまで待機
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("ヘルプ")));

		// 「ヘルプ」をクリック
		webDriver.findElement(By.linkText("ヘルプ")).click();

		// ヘルプ画面のタイトルになるまで待機
		wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));

		// タイトルチェック
		assertEquals("ヘルプ | LMS", webDriver.getTitle());

		//Case05_test03エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {

		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// 現在のタブを保持
		String currentTab = webDriver.getWindowHandle();

		//「よくある質問」リンク押下（別タブ）
		webDriver.findElement(By.linkText("よくある質問")).click();

		// タブ切り替え
		Set<String> allTabs = webDriver.getWindowHandles();
		for (String tab : allTabs) {
			if (!tab.equals(currentTab)) {
				webDriver.switchTo().window(tab);
				break;
			}
		}

		//タイトル「よくある質問 | LMS」になるまで待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));

		//タイトルチェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		//Case05_test04エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// 「研修関係」カテゴリをクリック
		webDriver.findElement(By.linkText("【研修関係】")).click();

		// 検索結果行が表示される部分へスクロール
		WebElement resultArea = webDriver.findElement(By.cssSelector("table"));
		((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", resultArea);

		// タイトル「よくある質問 | LMS」になるまで待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));

		// タイトルチェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		// Case06_test05エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問クリックで回答表示")
	void test06() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// 1件目の質問をクリック
		WebElement question = webDriver.findElement(By.cssSelector("table tbody tr dl dt"));
		question.click();

		// 回答要素を取得し、表示状態を確認
		WebElement answer = webDriver.findElement(By.cssSelector("table tbody tr dl dd"));
		assertEquals("fs18", answer.getAttribute("class"));

		// タイトル「よくある質問 | LMS」になるまで待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));

		// タイトルチェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		// Case06_test06エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
