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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト05 キーワード検索で該当キーワードのみ表示")
	void test05() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// キーワード入力
		webDriver.findElement(By.id("form")).sendKeys("申し込み");

		// 検索押下
		webDriver.findElement(By.cssSelector("input[value='検索']")).click();

		// 検索結果が画面に見えるように、クリアボタンの位置までスクロール
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0, 400);");

		// タイトル「よくある質問 | LMS」になるまで待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));

		// タイトルチェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());
		// Case05_test05エビデンスを取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));

		// クリアボタン押下
		webDriver.findElement(By.cssSelector("input[value='クリア']")).click();

		// 入力欄の値が空か確認
		String value = webDriver.findElement(By.id("form")).getAttribute("value");
		assertEquals("", value);

		// タイトル「よくある質問 | LMS」になるまで待機
		wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));

		// タイトルチェック
		assertEquals("よくある質問 | LMS", webDriver.getTitle());

		// Case05_test06エビデンスを取得
		getEvidence(new Object() {
		});
	}

}
