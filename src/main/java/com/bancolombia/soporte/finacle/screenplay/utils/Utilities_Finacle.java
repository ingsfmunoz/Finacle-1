package com.bancolombia.soporte.finacle.screenplay.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.serenitybdd.core.pages.PageObject;

public class Utilities_Finacle extends PageObject {

	private String winHandleBefore;
	private String contents;
	private String xmlFilePath = "C:\\Users\\YANIER.MESTRA\\eclipse-workspace\\com.bancolombia.soporte.finacle\\src\\test\\resources\\com\\bancolombia\\soporte\\finacle\\FilesXml\\CDT_Fisicos.xml";

	public void selecFrame(WebDriver hisBrowser, String frame) {

		if (frame == "loginFrame") {

			hisBrowser.switchTo().frame(hisBrowser.findElement(By.name("loginFrame")));

		} else if (frame == "CoreServer") {

			hisBrowser.switchTo().frame(hisBrowser.findElement(By.name("CoreServer")));
			hisBrowser.switchTo().frame(hisBrowser.findElement(By.name("FINW")));
		}

	}
	public void GoToMenu(WebDriver hisBrowser, String menu) throws InterruptedException {

		hisBrowser.findElement(By.id("menuName")).sendKeys(menu);
		hisBrowser.findElement(By.id("gotomenu")).click();
		hisBrowser.switchTo().alert().accept();
	}

	public void selectFinacleCore(WebDriver hisBrowser, String option) throws InterruptedException {

		Select element = new Select(hisBrowser.findElement(By.name("appSelect")));
		element.selectByVisibleText(option);
		hisBrowser.switchTo().alert().accept();

	}

	public void enterKey() throws AWTException, InterruptedException {

		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
	}

	public void loadXMLToString() throws IOException {

		contents = FileUtils.readFileToString(new File(xmlFilePath), "UTF-8");
	}

	public void copyPaste() throws AWTException {

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection stringSelection = new StringSelection(contents);
		clipboard.setContents(stringSelection, stringSelection);
		Robot robot = new Robot();
		robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
		robot.keyPress(java.awt.event.KeyEvent.VK_V);
		robot.keyRelease(java.awt.event.KeyEvent.VK_V);
		robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
	}

	public void swithWindows(WebDriver hisBrowser) {

		winHandleBefore = hisBrowser.getWindowHandle();

		for (String winHandle : hisBrowser.getWindowHandles()) {
			hisBrowser.switchTo().window(winHandle);
		}

	}

	public String getWinHandleBefore() {
		return winHandleBefore;
	}

	public String readXML(String xml, String parentNode, String sonNode)
			throws SAXException, IOException, ParserConfigurationException {

		String dataXmlResult;
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));

		NodeList transactionNodes = doc.getElementsByTagName(parentNode);

		if (transactionNodes.getLength() > 0) {

			Element node = (Element) transactionNodes.item(0);
			dataXmlResult = node.getElementsByTagName(sonNode).item(0).getTextContent();

		} else {

			dataXmlResult = "No Response Gender";
		}

		return dataXmlResult;

	}

	public void updateXml(Map<String, String> map)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFilePath);

		Set<String> keys = map.keySet();
		String keysStr = String.join(",", keys);
		String[] strKey = keysStr.split(",");

		NodeList NodePartne = document.getElementsByTagName("FIXML");
		Element NodeSon = (Element) NodePartne.item(0);

		for (int i = 0; i < strKey.length; i++) {

			NodeList tag_1 = NodeSon.getElementsByTagName(strKey[i]);
			Element value = (Element) tag_1.item(0);
			value.setTextContent(map.get(strKey[i]));
		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource domSource = new DOMSource(document);
		StreamResult streamResult = new StreamResult(new File(xmlFilePath));
		transformer.transform(domSource, streamResult);

	}

}