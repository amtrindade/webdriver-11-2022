package com.targettrust.test;

import static com.targettrust.core.DriverFactory.getDriver;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.targettrust.core.BaseTest;

public class WebElementsTest extends BaseTest{		

	@Before
	public void setUp() throws Exception {		
		getDriver().get("http://antoniotrindade.com.br/treinoautomacao/elementsweb.html");
	}
	
	@Test
	public void testValidateTitle() {
		// Valida o título da página		
		String title = getDriver().getTitle();	
		assertEquals("WebElements Test Page Lab", title);
	}
	
	@Test
	public void testWriteTextFieldName() throws InterruptedException {
		//Localiza elemento
		WebElement tfName = getDriver().findElement(By.name("txtbox1"));
	
		//Interage com elemento
		tfName.sendKeys("Antônio");	
		
		//opcional sleep
		Thread.sleep(3000);		
		
		String valueExpected = tfName.getAttribute("value"); 
		
		//Valida o resultado		
		assertEquals("O nome deveria ser igual", "Antônio", valueExpected);		
		//assertEquals("O nome deveria ser igual", "Antônio", tfName.getAttribute("value"));
	}
	
	@Test
	public void testValidateTextFieldEnable() throws InterruptedException {
		//Localizar os elementos
		WebElement tfEnable = getDriver().findElement(By.name("txtbox1"));
		WebElement tfDisable = getDriver().findElement(By.name("txtbox2"));
		
		//Interaçao com os elementos 
		//***NÃO PRECISA, SO A NÍVEL DE CURIOSIDADE
		if (tfEnable.isEnabled()) {
			tfEnable.sendKeys("habilitado");
		}
		
		if (tfDisable.isEnabled()) {
			tfDisable.sendKeys("Outro texto qualquer");
		}	
		
		Thread.sleep(3000);
		
		//Valida o resultado
		assertTrue(tfEnable.isEnabled());		
		assertFalse(tfDisable.isEnabled());					
	}
	
	@Test
	public void testRadioButton() throws InterruptedException {
		List<WebElement> listRadio = getDriver().findElements(By.name("radioGroup1"));
		
		assertEquals(4, listRadio.size());
		
		//listRadio.get(2).click();
		
		for (WebElement element : listRadio) {					
			if ("Radio 3".equals(element.getAttribute("value"))) {
				element.click();
			}
		}		
				
		assertTrue(listRadio.get(2).isSelected());		
		assertFalse(listRadio.get(0).isSelected());
		assertFalse(listRadio.get(1).isSelected());
		assertFalse(listRadio.get(3).isSelected());		
	}
	
	@Test
	public void testCheckBoxLooping() throws InterruptedException {
		List<WebElement> checkList = 
				getDriver().findElements(By.xpath("//div[@id='box']//input[@type='checkbox']"));
		
		assertEquals(4, checkList.size());
		
		//Forma com looping
		for (WebElement el : checkList) {
			//if com a condição OU
			if ((el.getAttribute("value").equals("Check 3")) || 
					(el.getAttribute("value").equals("Check 4"))) {
				el.click();
			}						
		}
	
		assertFalse(checkList.get(0).isSelected());
		assertFalse(checkList.get(1).isSelected());
		assertTrue(checkList.get(2).isSelected());
		assertTrue(checkList.get(3).isSelected());
	}
	
	@Test
	public void testCheckBoxSimple() throws InterruptedException {
		List<WebElement> checkList = getDriver().findElements(By.name("chkbox"));
		
		assertEquals(4, checkList.size());

		//Forma simples
		checkList.get(2).click();
		checkList.get(3).click();
		
		assertFalse(checkList.get(0).isSelected());
		assertFalse(checkList.get(1).isSelected());
		assertTrue(checkList.get(2).isSelected());
		assertTrue(checkList.get(3).isSelected());
	}
	
	@Test
	public void testDropDownListSingle() {
		WebElement dpSingle = getDriver().findElement(By.name("dropdownlist"));		
		Select selSingle = new Select(dpSingle);
		
		//selSingle.selectByIndex(6);
		//selSingle.selectByVisibleText("Item 7");
		selSingle.selectByValue("item1");
		
		selSingle.selectByValue("item7");
 		
		assertEquals("Item 7", selSingle.getFirstSelectedOption().getText());		
		assertEquals("item7", selSingle.getFirstSelectedOption().getAttribute("value"));
	}
	
	@Test
	public void testDropDownMultiSelect() throws InterruptedException {
		WebElement dpMulti = getDriver().findElement(By.name("multiselectdropdown"));
		Select selMulti = new Select(dpMulti);
		
		selMulti.selectByVisibleText("Item 5");		
		selMulti.selectByVisibleText("Item 8");
		selMulti.selectByVisibleText("Item 9");
					
		List<WebElement> listSelect = selMulti.getAllSelectedOptions();
		assertEquals(3, listSelect.size());
		
		assertEquals("Item 5", listSelect.get(0).getText());
		assertEquals("Item 8", listSelect.get(1).getText());
		assertEquals("Item 9", listSelect.get(2).getText());		
	}
	
	@Test
	public void testAlert() {
		WebElement btnAlert = getDriver().findElement(By.name("alertbtn"));
		btnAlert.click();
		
		Alert myAlert = getDriver().switchTo().alert();
		assertEquals("Eu sou um alerta!", myAlert.getText());
		
		myAlert.dismiss();
		Boolean enable = btnAlert.isEnabled();
		
		assertTrue(enable);		
	}
	
	@Test
	public void testConfirm() {
		WebElement btnConfirm = getDriver().findElement(By.name("confirmbtn"));
		btnConfirm.click();
		
		Alert confirmAlert = getDriver().switchTo().alert();
		assertEquals("Pressione um botão!", confirmAlert.getText());
		
		confirmAlert.accept();		
	}
	
	@Test
	public void testiFrame() throws InterruptedException {				
		//Entra no iframe
		getDriver().switchTo().frame(0);
		
		WebElement tfiFrame = getDriver().findElement(By.id("tfiframe"));
		tfiFrame.sendKeys("Automação com WebDriver");		
		
		assertEquals("Automação com WebDriver", tfiFrame.getAttribute("value"));
		
		//Volta o driver para origem
		getDriver().switchTo().defaultContent();
		
	}
}
